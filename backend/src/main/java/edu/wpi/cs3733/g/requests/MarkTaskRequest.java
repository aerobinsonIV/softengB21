package edu.wpi.cs3733.g.requests;

public class MarkTaskRequest {
    String projectName;
    int id;
    String newStatus;

    public String getProjectName(){
        return projectName;
    }

    public int getId() {
        return id;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public MarkTaskRequest(String projectName, int id, String newStatus) {
        this.projectName = projectName;
        this.id = id;
        this.newStatus = newStatus;
    }

    public MarkTaskRequest() {}

    @Override
    public String toString() {
        return "MarkTaskRequest{" +
                "id='" + id + '\'' +
                "new_status='" + newStatus + "\''" + '}';
    }
}
