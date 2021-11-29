package DataSheet;

import java.util.ArrayList;

//A Boolean list that determines which feature a student holds
public class FeaturesList extends ArrayList<Boolean> {
    private ArrayList<Boolean> list;

    public FeaturesList() {
        list = new ArrayList<>(Settings.numOfFeatures);
        for (int i=0; i<Settings.numOfFeatures; ++i)
            add(false);
    }

    public void set(Feature feature, boolean hasFeature) {
        set(feature.getFeatureID(), hasFeature);
    }

    public boolean hasFeature(Feature feature) {
        return get(feature.getFeatureID());
    }
}
