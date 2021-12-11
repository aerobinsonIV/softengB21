package edu.wpi.cs3733.g.requests;

public class DecomposeTaskRequest {
    int parentID;
    String childName;

    public int getParentID() {
        return parentID;
    }

    public String getChildName() {
        return childName;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public DecomposeTaskRequest(int parentID, String childName) {
        this.parentID = parentID;
        this.childName = childName;
    }

    public DecomposeTaskRequest() {}

    @Override
    public String toString() {
        return "DecomposeTaskRequest{" +
                "parentID='" + parentID + '\'' +
                "child_name='" + childName + "\''" + '}';
    }
}
