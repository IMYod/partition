package Algorithm2;

import DataSheet.Student;
import Evaluation.EvalOperations;
import Input.InputData;
import Placing.GroupPlacement;

import java.util.concurrent.ThreadLocalRandom;

//Performs operations that improve partition
public class HillClimbing extends SearchSBSAlgorithm {
    //Probability for swap operation
    protected double swapProbability;
    protected EvalOperations evaluator;

    protected HillClimbing(InputData input, long wait_time, double swapProbability) {
        super(input, wait_time);
        this.swapProbability = swapProbability;
        this.evaluator = new EvalOperations(input);
    }

    public HillClimbing(InputData input) {
        this(input,SearchSBSAlgorithm.default_wait_time ,0.5);
    }

    //Choose random operation
    //Apply it only if it is improves
    protected void takeAStep() {
        if (ThreadLocalRandom.current().nextFloat() < swapProbability)
            swapClimbing(input.getStudents().peekUniform(), input.getStudents().peekUniform());
        else
            moveClimbing(input.getStudents().peekUniform(), currentSolution.peekUniformGroup());
    }

    //Swap only if it is improves
    public void swapClimbing(Student student1, Student student2) {
        if (evaluator.isSwapImprove(student1, student2, currentSolution))
            swap(student1, student2);
    }
    //Move only if it is improves
    protected void moveClimbing(Student student, GroupPlacement groupTo) {
        GroupPlacement groupFrom = currentSolution.getPlacement(student);
        //The student already in this group
        if (! EvalOperations.isMovePossible(groupFrom, groupTo))
            return;

        if (evaluator.isMoveImprove(student, groupFrom, groupTo))
            move(student, groupTo);
    }
}