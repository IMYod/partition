package Placing;

import Algorithm2.Select;
import DataSheet.Settings;
import DataSheet.Student;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Pool of groups
public class GroupsPool {

    //Double mapping: group to students, and student to group
    private Map<GroupID, GroupPlacement> groupsMap = new HashMap<>();
    private Map<Student, GroupID> studentsMap = new HashMap<>();

    //Assuming groupID's are in [0, numOfGroups)
    public GroupsPool() {
        for (int i = 0; i< Settings.numOfGroups; ++i) {
            GroupID gid = new GroupID(i);
            groupsMap.put(gid, new GroupPlacement(gid));
        }
    }

    public GroupsPool(GroupsPool solution) {
        //Deep copy constructor
        this.groupsMap = new HashMap<>();
        for (Map.Entry<GroupID, GroupPlacement> entry : solution.groupsMap.entrySet()) {
            groupsMap.put(entry.getKey(), new GroupPlacement(entry.getValue()));
        }
        this.studentsMap = new HashMap<>(solution.studentsMap);
    }

    //add student to group
    public void put(GroupID groupID, Student student) {
        GroupID prevGroupID = studentsMap.get(student);

        //If the student already has an assignment
        if (groupsMap.get(prevGroupID) != null) {
            if (prevGroupID.equals(groupID)) return;
            getPlacement(prevGroupID).remove(student);
        }
        studentsMap.put(student, groupID);
        getPlacement(groupID).add(student);
    }

    //Remove assignment of student
    public void remove(Student student) {
        if (!studentsMap.containsKey(student))
            return;
        GroupID groupID = getGroupID(student);
        studentsMap.remove(student);
        getPlacement(groupID).remove(student);
    }

    public GroupID getGroupID(Student student) {
        return studentsMap.get(student);
    }
    public GroupPlacement getPlacement(GroupID groupID) {
        return groupsMap.get(groupID);
    }
    public GroupPlacement getPlacement(Student student) {
        return getPlacement(getGroupID(student));
    }
    public StudentsPool getStudents() {return new StudentsPool(studentsMap.keySet());}
    public Collection<GroupID> getGroups() {
        return groupsMap.keySet();
    }
    public Collection<GroupPlacement> getPlacements() {
        return groupsMap.values();
    }

    @Override
    public String toString() {
        return "GroupsPool{" +
                groupsMap +
                '}';
    }

    //Swap groups of two students
    public void swap(Student student1, Student student2) {
        GroupID gid1 = getGroupID(student1);
        GroupID gid2 = getGroupID(student2);
        if (gid1.equals(gid2))
            return;
        put(gid2, student1);
        put(gid1, student2);
    }

    //Peek a group:
    public GroupPlacement peekGroup(){
        return getPlacement(groupsMap.keySet().iterator().next());
    }
    public GroupPlacement peekUniformGroup(){
        GroupID groupID = Select.selectUniform(groupsMap.keySet());
        return getPlacement(groupID);
    }
}