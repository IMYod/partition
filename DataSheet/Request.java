package DataSheet;

public abstract class Request {
    //The weight of a request used in tha evaluation part
    private int weight;

    public Request(int weight) {
        this.weight = weight;
    }
    public int getWeight() {
        return weight;
    }
}
