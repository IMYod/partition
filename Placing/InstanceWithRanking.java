package Placing;

import Algorithm2.Select;
import DataSheet.FriendRequest;
import DataSheet.Student;
import Evaluation.EvalRequests;

import java.util.List;


public abstract class InstanceWithRanking extends Instance {
    public Student peekStudentByRanking () {
        GroupPlacement groupPlacement = solution.peekUniformGroup();
        return Select.rankingProportional(groupPlacement, (s1, s2) -> {
            return EvalRequests.evalFriendRequests(groupPlacement, getRequestsToCompare(s2))
                    - EvalRequests.evalFriendRequests(groupPlacement, getRequestsToCompare(s2));
        });
    }

    protected abstract List<FriendRequest> getRequestsToCompare(Student student);
}
