package Algorithm2;

import DataSheet.DiversityRequestsPool;
import Evaluation.EvalRequests;
import Input.InputData;
import Placing.GroupsPool;

//Holds another search algorithm.
//Runs the algorithm several times, the time allotted for each run is shorter.
//Choose the best result
public class RandomRestart extends SearchSBSAlgorithm {

    public RandomRestart(long wait_time, int restartIterations, SearchSBSAlgorithm algo) {
        super(algo.input, wait_time);
        this.restartIterations = restartIterations;
        this.algo = algo;
        algo.wait_time /= restartIterations;
    }

    public RandomRestart(SearchSBSAlgorithm algo) {
        this(SearchSBSAlgorithm.default_wait_time, 50, algo);
    }

    private int restartIterations;
    SearchSBSAlgorithm algo;

    @Override
    protected void takeAStep() {
        currentSolution = algo.search();
    }
}
