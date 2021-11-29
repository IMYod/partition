package Evaluation;

import DataSheet.DiversityRequest;
import DataSheet.DiversityRequestsPool;
import DataSheet.FriendRequest;
import DataSheet.Student;
import Placing.*;

import java.util.List;

//Given a partition or part of it, the quality of the solution could be evaluated
//Depending on the weight of the requests that have been fulfilled or not fulfilled.
public class EvalRequests {

    /* Friend requests */

    //Where the request met, add the weight.
    public static int evalFriendRequests(StudentsPool friends, FriendRequest request) {
        return friends.contains(request.getOther()) ? request.getWeight() : 0;
    }

    public static int evalFriendRequests(StudentsPool friends, List<FriendRequest> requests) {
        return requests.stream().map(request -> evalFriendRequests(friends, request)).reduce(0, Integer::sum);
    }

    public static int evalFriendRequests(Student student, StudentsPool friends) {
        return evalFriendRequests(friends, student.getFrom())
                + evalFriendRequests(friends, student.getTo());
    }


    /* Diversity requests */
    public static int evalDiversities(StudentsPool students, DiversityRequest request) {
        int countFeature = students.countWithFeature(request.getFeature());
        return - request.distanceToRange(countFeature) * request.getWeight();
    }

    //Eval many diversity request
    public static int evalDiversitiesOfGroup(StudentsPool students, DiversityRequestsPool requestsPool) {
        return requestsPool.getRequests().stream()
                .map(request -> evalDiversities(students, request)).reduce(0, Integer::sum);
    }


    /* Eval any type of requests */

    //For some student in group
    public static int evalRequestsAndDiversities(Student student, StudentsPool friends, DiversityRequestsPool requestsPool) {
        StudentsPool friendsWithStudent = new StudentsPool(friends);
        friendsWithStudent.add(student);
        return evalFriendRequests(student, friendsWithStudent) + evalDiversitiesOfGroup(friendsWithStudent, requestsPool);
    }

    //For one group
    public static int evalGroup(StudentsPool students, DiversityRequestsPool requestsPool) {
        int evalFromRequests = students.stream().map(s -> evalFriendRequests(students, s.getFrom())).reduce(0, Integer::sum);
        int evalDiversity = evalDiversitiesOfGroup(students, requestsPool);
        return  evalFromRequests + evalDiversity;
    }

    //For all the groups
    public static int evalSolution(GroupsPool groupsPool, DiversityRequestsPool requestsPool) {
        return groupsPool.getPlacements().stream().map(placement -> evalGroup(placement, requestsPool)).reduce(0, Integer::sum);
    }
    public static int evalSolution(Instance instance) {
        return evalSolution(instance.solution, instance.input.getDiversityRequests());
    }
}