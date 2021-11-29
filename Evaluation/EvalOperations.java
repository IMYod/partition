package Evaluation;

import DataSheet.DiversityRequestsPool;
import DataSheet.Student;
import Input.InputData;
import Placing.*;

//The effect of swap and move operations on meeting the settings and the quality of the solution
//The evaluation depends on some placement, usually an intermediate solution
public class EvalOperations
{
    DiversityRequestsPool requestsPool;

    public EvalOperations(DiversityRequestsPool requestsPool) {
        this.requestsPool = requestsPool;
    }

    public EvalOperations(InputData input) {
        this(input.getDiversityRequests());
    }

    //When implementing this function, select the desired measure.
    //The evaluation functions of the adjacent classes can be used
    public int evalStudentInGroup(Student student, StudentsPool studentsPool) {
        StudentsPool withStudent = new StudentsPool(studentsPool);
        withStudent.add(student);
        return EvalRequests.evalGroup(withStudent, requestsPool);
    }

    /* Move operation */

    public static boolean isMovePossible(StudentsPool groupFrom, StudentsPool groupTo) {
        return ! (groupFrom.equals(groupTo)
                || MetSettings.isReachMaxCapacity(groupTo)
                || MetSettings.isReachMinCapacity(groupFrom));
    }

    public int evalMoveGain(Student student, StudentsPool groupFrom, StudentsPool groupTo) {
        int currentEval = evalStudentInGroup(student, groupFrom);
        int moveEval = evalStudentInGroup(student, groupTo);
        return  moveEval - currentEval;
    }

    public boolean isMoveImprove(Student student, StudentsPool groupFrom, StudentsPool groupTo) {
        return evalMoveGain(student, groupFrom, groupTo) > 0;
    }

    /* Swap operation */

    public int evalSwapGain(Student student1, Student student2, GroupsPool solution) {
        GroupID groupID1 = solution.getGroupID(student1);
        GroupID groupID2 = solution.getGroupID(student2);
        GroupPlacement group1 = solution.getPlacement(groupID1);
        GroupPlacement group2 = solution.getPlacement(groupID2);

        int currentEval = evalStudentInGroup(student1, group1) + evalStudentInGroup(student2, group2);

        StudentsPool without1 = new StudentsPool(group1);
        without1.remove(student1);
        StudentsPool without2 = new StudentsPool(group2);
        without2.remove(student2);
        int swapEval = evalStudentInGroup(student1, without2) + evalStudentInGroup(student2, without2);
        return swapEval - currentEval;
    }

    public boolean isSwapImprove(Student student1, Student student2, GroupsPool solution) {
        return evalSwapGain(student1, student2, solution) > 0;
    }

}
