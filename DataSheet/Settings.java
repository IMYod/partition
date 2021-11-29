package DataSheet;

import com.google.common.collect.Range;

public class Settings {
    //How many groups, how many features, range of group size
    public static int numOfGroups, numOfFeatures, minSize, maxSize;

    public static Range<Integer> getSizeRange() {
        return Range.closed(minSize, maxSize);
    }
}
