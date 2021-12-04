package edu.wpi.cs3733.g.requests;

public class CreateProjectRequest {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CreateProjectRequest(String project_name) {
        this.name = project_name;
    }

    public CreateProjectRequest() {}

    @Override
    public String toString() {
        return "CreateProjectRequest{" +
                "project_name='" + name + '\'' +
                '}';
    }
}