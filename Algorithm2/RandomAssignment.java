package Algorithm2;

import DataSheet.Settings;
import DataSheet.Student;
import Evaluation.MetSettings;
import Input.InputData;
import Placing.GroupID;
import Placing.GroupsPool;
import Placing.StudentsPool;

//Produces a random partition that meets the settings.
public class RandomAssignment extends SearchSBSAlgorithm {

    protected RandomAssignment(InputData input, long wait_time) {
        super(input, wait_time);
    }

    protected RandomAssignment(InputData input) {
        super(input);
    }

    protected void takeAStep() {
        GroupsPool groupsPool = new GroupsPool();
        StudentsPool waitingForPlacement = new StudentsPool(input.getStudents());
        int groupID = 0;

        while (!waitingForPlacement.isEmpty()) {
            Student nextStudent = waitingForPlacement.peekUniform();
            groupsPool.put(new GroupID(groupID), nextStudent);
            waitingForPlacement.remove(nextStudent);
            groupID = (groupID + 1) % Settings.numOfGroups;
        }
        if (MetSettings.isMetSizeSettings(groupsPool))
            currentSolution = groupsPool;
    }

    @Override
    public GroupsPool search() {
        long start_time = System.currentTimeMillis();
        long end_time = start_time + wait_time;
        while (System.currentTimeMillis() < end_time) {
            takeAStep();
            if (currentSolution != null)
                return currentSolution;
        }
        return null;
    }
}
