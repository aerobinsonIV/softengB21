package edu.wpi.cs3733.g.requests;

public class RenameTaskRequest {
    int id;
    String newName;

    public int getID() {
        return id;
    }

    public String getNewName() {
        return newName;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public RenameTaskRequest(int id, String newName) {
        this.id = id;
        this.newName = newName;
    }

    public RenameTaskRequest() {}

    @Override
    public String toString() {
        return "RenameTaskRequest{" +
                "id='" + id + '\'' +
                "new_name='" + newName + "\''" + '}';
    }
}
