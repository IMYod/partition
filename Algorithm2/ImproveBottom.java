package Algorithm2;

import Algorithm2.HillClimbing;
import DataSheet.FriendRequest;
import DataSheet.Student;
import Input.InputData;
import Placing.InstanceWithRanking;

import java.util.concurrent.ThreadLocalRandom;

//The operations are performed on students who suffer the most from the partition
//An operation performs only if it improves
public class ImproveBottom extends HillClimbing {

    public ImproveBottom(InputData input, long wait_time, double swapProbability) {
        super(input, wait_time, swapProbability);
    }

    public ImproveBottom(InputData input) {
        super(input);
    }

    @Override
    protected void takeAStep() {
        if (ThreadLocalRandom.current().nextFloat() < swapProbability)
            swapClimbing(currentSolution.peekUniformGroup().peekByRanking(), input.getStudents().peekUniform());
        else
            moveClimbing(currentSolution.peekUniformGroup().peekByRanking(), currentSolution.peekUniformGroup());
    }
}
