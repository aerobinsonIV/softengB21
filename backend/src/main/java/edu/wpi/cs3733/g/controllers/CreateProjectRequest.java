package edu.wpi.cs3733.g.controllers;

public class CreateProjectRequest {
    String project_name;

    public String getName() {
        return project_name;
    }

    public void setName(String name) {
        this.project_name = name;
    }

    public CreateProjectRequest(String project_name) {
        this.project_name = project_name;
    }

    public CreateProjectRequest() {}

    @Override
    public String toString() {
        return "CreateProjectRequest{" +
                "project_name='" + project_name + '\'' +
                '}';
    }
}