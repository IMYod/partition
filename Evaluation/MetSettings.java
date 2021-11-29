package Evaluation;

import DataSheet.*;
//import Placing.Mapping;
import Placing.GroupsPool;
import Placing.StudentsPool;

//Functions to check whether the placement meets the settings
public class MetSettings {

    public static boolean isMetSizeSettings(StudentsPool students) {
        return Settings.getSizeRange().contains(students.size());
    }

    public static boolean isMetSizeSettings(GroupsPool groupsPool) {
        return groupsPool.getPlacements().stream().allMatch(MetSettings::isMetSizeSettings)
                && groupsPool.getGroups().size() == Settings.numOfGroups;
    }

    public static boolean isReachMaxCapacity(StudentsPool students) {
        return students.size() >= Settings.maxSize;
    }
    public static boolean isReachMinCapacity(StudentsPool students) {
        return students.size() <= Settings.minSize;
    }
}
