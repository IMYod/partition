package Placing;

import Algorithm2.Select;
import DataSheet.Feature;
import DataSheet.FriendRequest;
import DataSheet.Student;
import Evaluation.EvalRequests;

import java.util.*;

//Set of students
public class StudentsPool extends HashSet<Student> {

    public StudentsPool() {
        super();
    }
    public StudentsPool(Collection<Student> students) {
        super(students);
    }

    //How many students in this pool has a feature
    public int countWithFeature(Feature feature) {
        return (int) stream().filter(s -> s.getFeatures().hasFeature(feature)).count();
    }

    //Peek students
    public Student peek(){
        return iterator().next();
    }
    public Student peekUniform(){
        return Select.selectUniform(this);
    }
    public Student peekByRanking () {
        return Select.rankingProportional(this, (s2, s1) -> {
            return EvalRequests.evalFriendRequests(this, s1.getFrom())
                    - EvalRequests.evalFriendRequests(this, s2.getFrom());
        });
    }
}
