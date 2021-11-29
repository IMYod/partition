package Main;

import Algorithm2.*;
import DataSheet.*;
import Evaluation.EvalRequests;
import Input.InputData;
import Placing.GroupsPool;
import Placing.StudentsPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args){
        //Set settings
        Settings.numOfGroups=5;
        Settings.minSize=22;
        Settings.maxSize=28;
        Settings.numOfFeatures=4;
        int numberOfStudents = 120;
        int maxWeight = 10;

        //Create students and features
        ArrayList<Student> students = new ArrayList<>(numberOfStudents);
        for (int i=0; i<numberOfStudents; ++i) {
            Student s = new Student(String.valueOf(i));
            for (int f=0; f<Settings.numOfFeatures; ++f)
                if (ThreadLocalRandom.current().nextBoolean())
                    s.getFeatures().set(new Feature(f), true);
            students.add(s);
        }

        //add friend requests
        for (int i=0; i<numberOfStudents; ++i) {
            for (int j=0; j<numberOfStudents; ++j) {
                if (i==j)
                    break;
                if (ThreadLocalRandom.current().nextFloat()<0.3)
                    students.get(i).createFriendRequest(students.get(j), ThreadLocalRandom.current().nextInt(-maxWeight, maxWeight));
            }
        }

        //add diversity requests
        ArrayList<DiversityRequest> requests = new ArrayList<>(Settings.numOfFeatures);
        for (int f=0; f<Settings.numOfFeatures; ++f) {
            DiversityRequest dr = new DiversityRequest(ThreadLocalRandom.current().nextInt(-maxWeight, maxWeight), new Feature(f));
            int mean = (Settings.maxSize + Settings.minSize) / Settings.numOfGroups;
            dr.setRange((int)(mean/1.2), (int)(mean*1.2));
        }
        DiversityRequestsPool requestsPool = new DiversityRequestsPool(requests);

        InputData data = new InputData(new StudentsPool(students), requestsPool);

        //Create algorithms
        ArrayList<SearchSBSAlgorithm> algorithms = new ArrayList<SearchSBSAlgorithm>(Arrays.asList
                (new RandomWalk(new ImproveBottom(data)), new HillClimbing(data), new ImproveBottom(data), new TabuSearch(data), new RandomRestart(new HillClimbing(data))));

        //Find partition!
        for (SearchSBSAlgorithm algo : algorithms) {
            GroupsPool solution = algo.search();
            System.out.println(algo.getClass().getName());
            System.out.println(solution);
            if (solution != null) {
                System.out.println(EvalRequests.evalSolution(solution, requestsPool));
                for (Student student : data.getStudents()) {
                    System.out.print(EvalRequests.evalFriendRequests(solution.getPlacement(solution.getGroupID(student)), student.getFrom()));
                    System.out.print(',');
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
