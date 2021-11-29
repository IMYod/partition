package DataSheet;

import com.google.common.collect.Range;

//What is the desired range of students who will hold a feature in one group.
public class DiversityRequest extends Request {
    private final Feature feature;
    private Range<Integer> range;

    public DiversityRequest(int weight, Feature feature) {
        //Weight: The impact of deviation on one student from the desired range.
        super(weight);
        this.feature = feature;
    }

    public Feature getFeature() {
        return feature;
    }
    public Range<Integer> getRange() {
        return range;
    }
    public void setRange(int lower, int upper) {
        this.range = Range.closed(lower, upper);
    }
    public boolean isInRange(int i) {
        return range.contains(i);
    }
    public int distanceToRange(int i) {
        if (isInRange(i))
            return 0;
        if (i < getRange().lowerEndpoint())
            return getRange().lowerEndpoint() - i;
        return i - getRange().upperEndpoint();
    }
}
