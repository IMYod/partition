package Placing;

import DataSheet.Student;

import java.util.Iterator;
import java.util.Objects;

//Match students pool to specific groupId
public class GroupPlacement extends StudentsPool {
    private GroupID groupID;

    public GroupPlacement(GroupID groupID) {
        this.groupID = groupID;
    }
    public GroupID getGroupID() {
        return groupID;
    }
    public GroupPlacement(GroupPlacement other) {
        super(other);
        this.groupID = other.groupID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GroupPlacement placement = (GroupPlacement) o;
        return Objects.equals(groupID, placement.groupID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), groupID);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Student> itr = iterator();
        while (itr.hasNext()) {
            sb.append(itr.next());
            sb.append(',');
        }
        sb.append('}');
        return sb.toString();
    }
}
