package DataSheet;

//A feature that students can hold.
//Identified by ID
public class Feature {

    private final int featureID;

    public Feature(int featureID) {
        assert (featureID >= 0);
        assert (featureID < Settings.numOfFeatures);
        this.featureID = featureID;
    }

    public int getFeatureID() {
        return featureID;
    }
}
