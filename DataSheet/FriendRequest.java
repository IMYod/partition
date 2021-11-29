package DataSheet;

public class FriendRequest extends Request {
    //Can be interpreted as either from or to friend request

    //From request: Who are you asked to be with
    //To request: Who asked to be with you
    private Student other;

    public FriendRequest(int weight, Student other) {
        super(weight);
        this.other = other;
    }

    public Student getOther() {
        return other;
    }
}
