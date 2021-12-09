package edu.wpi.cs3733.g.requests;

public class MarkTaskRequest {
    int id;
    String newStatus;

    public int getID() {
        return id;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public MarkTaskRequest(int id, String newStatus) {
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
