package edu.wpi.cs3733.g.controllers;

import edu.wpi.cs3733.g.entities.Project;

public class CreateProjectResponse {
    Project project;
    String error;

    public CreateProjectResponse(Project project){
        if(project != null){
            this.project = project;
            this.error = "";
        }else{
            this.project = null;
            this.error = "Failed to create project.";
        }
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
