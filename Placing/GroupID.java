package Placing;

import DataSheet.Settings;

import java.util.Objects;

//Unique group ID
public class GroupID {

    private final int groupID;

    public GroupID(int groupID) {
        assert (groupID >= 0);
        assert (groupID < Settings.numOfGroups);
        this.groupID = groupID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupID groupID = (GroupID) o;
        return this.groupID == groupID.groupID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID);
    }

    @Override
    public String toString() {
        return "GroupID{" +
                groupID +
                '}';
    }
}