package Algorithm2;

import DataSheet.Student;
import Evaluation.EvalOperations;
import Evaluation.MetSettings;
import Input.InputData;
import Placing.GroupPlacement;
import Placing.StudentsPool;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

//Holds a list of recently moved/swapped students, on whom the next operation will not be performed.
//From the rest a student randomly selects and randomly selects an operation,
//Looks for how to run this operation on him, in the most beneficial way.
public class TabuSearch extends SearchSBSAlgorithm {

    double swapProbability;
    private LinkedList<Student> tabuList;
    private StudentsPool notTabuList;
    private double tabuCapacityRatio;
    private int maxTabuCapacity;
    EvalOperations evaluator;

    public TabuSearch(InputData input, long wait_time, double swapProbability, double tabuCapacityRatio) {
        super(input, wait_time);
        this.swapProbability = swapProbability;
        this.tabuList = new LinkedList<>();
        this.tabuCapacityRatio = tabuCapacityRatio;
        this.maxTabuCapacity = (int) (input.getStudents().size() * tabuCapacityRatio);
        this.notTabuList = new StudentsPool(input.getStudents());
        this.evaluator = new EvalOperations(input);
    }

    public TabuSearch(InputData input) {
        this(input, SearchSBSAlgorithm.default_wait_time ,0.75, 0.3);
    }

    @Override
    public void takeAStep() {
        //Student student = notTabuList.peekUniform();
        Student student = notTabuList.peekByRanking();
        if (ThreadLocalRandom.current().nextFloat() < swapProbability)
            swap(student);
        else
            move(student);
        addStudentToTabu(student);
    }

    private void move(Student student) {
        GroupPlacement studentPlacement = currentSolution.getPlacement(student);
        GroupPlacement bestGroupMoveTo = currentSolution.getPlacements().stream()
                .filter(placement -> !MetSettings.isReachMaxCapacity(placement))
                .max(Comparator.comparingInt(value -> evaluator.evalMoveGain(student, studentPlacement, value)))
                .orElse(studentPlacement);
        move(student, bestGroupMoveTo);
    }

    private void swap(Student student1) {
        Student bestStudentToSwap = notTabuList.stream()
                .max(Comparator.comparingInt(value -> evaluator.evalSwapGain(student1, value, currentSolution)))
                .orElse(student1);
        swap(student1, bestStudentToSwap);
    }

    private void addStudentToTabu(Student student) {
        tabuList.addLast(student);
        if (tabuList.size() > maxTabuCapacity) {
            Student removed = tabuList.removeFirst();
            notTabuList.add(removed);
        }

    }
}
