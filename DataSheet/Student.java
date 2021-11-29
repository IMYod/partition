package DataSheet;

import com.sun.nio.sctp.NotificationHandler;

import java.util.*;

public class Student {

    //unique student ID
    private final String studentID;
    //List of friend requests
    //If student A want to be with student B, than A has "from" friend request, and B has "to" friend request
    private List<FriendRequest> from, to;
    //Which features the student has
    private FeaturesList features;

    public Student(String _studentID) {
        this.studentID = _studentID;
        from = new LinkedList<>();
        to = new LinkedList<>();
        features = new FeaturesList();
    }

    //copy constructor
    public Student(Student other) {
        this.studentID = other.studentID;
        this.from = other.from;
        this.to = other.to;
        this.features = other.features;
    }

    //For each request create from A and to B
    public void createFriendRequest(Student toStudent, int weight) {
        from.add(new FriendRequest(weight, toStudent));
        toStudent.to.add(new FriendRequest(weight, this));
    }

    public String getStudentID() { return studentID; }
    public List<FriendRequest> getFrom() { return from; }
    public List<FriendRequest> getTo() { return to; }
    public FeaturesList getFeatures() { return features; }

    @Override
    public String toString() {
        return "Student{" +
                studentID +
                '}';
    }

    @Override
    public int hashCode() {
        return studentID.hashCode();
    }


}