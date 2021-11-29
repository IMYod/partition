package Algorithm2;

import DataSheet.Student;
import Evaluation.EvalOperations;
import Input.InputData;
import Placing.GroupPlacement;
import Placing.Instance;

import java.util.concurrent.ThreadLocalRandom;

//Holds some "step by step" algorithm.
//With a constant probability it is chosen whether to perform a random operation, or to proceed according to the determined algorithm.
public class RandomWalk extends SearchSBSAlgorithm {

    SearchSBSAlgorithm algo;
    double randomWalkProbability = 0.1;
    double swapProbability = 0.8;

    public RandomWalk(long wait_time, SearchSBSAlgorithm algo, double randomWalkProbability, double swapProbability) {
        super(algo.input, wait_time);
        this.algo = algo;
        this.randomWalkProbability = randomWalkProbability;
        this.swapProbability = swapProbability;
    }

    public RandomWalk(SearchSBSAlgorithm algo) {
        super(algo.input);
        this.algo = algo;
        this.randomWalkProbability = 0.05;
        this.swapProbability = 0.8;
    }

    @Override
    protected void takeAStep() {
        if (ThreadLocalRandom.current().nextFloat() < randomWalkProbability) {
            if (ThreadLocalRandom.current().nextFloat() < swapProbability)
                randomSwap();
            else
                randomMove();
        }

        else {
            algo.currentSolution = currentSolution;
            algo.takeAStep();
            currentSolution = algo.currentSolution;
        }
    }

    //Swap students uniformly at random
    private void randomSwap() {
        Student student1 = input.getStudents().peekUniform();
        Student student2 = input.getStudents().peekUniform();
        swap(student1, student2);
    }
    //Move random student to a random group
    private void randomMove() {
        Student student1 = input.getStudents().peekUniform();
        GroupPlacement groupFrom = currentSolution.getPlacement(student1);
        GroupPlacement groupTo = currentSolution.peekUniformGroup();
        if (EvalOperations.isMovePossible(groupFrom, groupTo))
            move(student1, groupTo);
    }
}
