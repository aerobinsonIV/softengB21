package edu.wpi.cs3733.g.requests;

public class DeleteProjectData {
    String name;

    public DeleteProjectData() {
    }

    public DeleteProjectData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
