package Algorithm2;

import DataSheet.Student;
import Evaluation.EvalRequests;
import Input.InputData;
import Placing.GroupPlacement;
import Placing.GroupsPool;

public abstract class SearchSBSAlgorithm {
    GroupsPool bestSolution, currentSolution;
    int evalBestSolution;
    long wait_time;
    final InputData input;
    final static long default_wait_time = 5000L;
    boolean breakFlag = true;

    protected SearchSBSAlgorithm(InputData input, long wait_time) {
        this.input = input;
        this.wait_time = wait_time;
    }

    protected SearchSBSAlgorithm(InputData input) {
        this(input, default_wait_time);
    }

    protected int evalSolution(GroupsPool solution) {
        return EvalRequests.evalSolution(solution, input.getDiversityRequests());
    }

    protected GroupsPool getRandomSolution() {
        RandomAssignment ra = new RandomAssignment(input);
        return ra.search();
    }

    public void currentSolutionUpdated() {
        int evalNewSolution = evalSolution(currentSolution);
        if (bestSolution == null || evalNewSolution > evalBestSolution) {
            bestSolution = new GroupsPool(currentSolution);
            evalBestSolution = evalNewSolution;
        }
    }

    //Update the current solution
    protected abstract void takeAStep();

    public GroupsPool search() {
        currentSolution = getRandomSolution();
        if (currentSolution == null)
            return null;

        long start_time = System.currentTimeMillis();
        long end_time = start_time + wait_time;
        while (breakFlag && System.currentTimeMillis() < end_time) {
            takeAStep();
            currentSolutionUpdated();
        }
        return bestSolution;
    }

    //Basic operations

    public final void move(Student student, GroupPlacement groupTo) {
        currentSolution.put(groupTo.getGroupID(), student);
    }
    public final void swap(Student student1, Student student2) {
        currentSolution.swap(student1, student2);
    }
}
