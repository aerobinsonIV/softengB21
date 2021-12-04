package edu.wpi.cs3733.g.responses;

import edu.wpi.cs3733.g.entities.Project;

import java.util.ArrayList;

public class GetAllProjectsResponse {
    ArrayList<Project> projects;

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public GetAllProjectsResponse(ArrayList<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        String output = "";
        for(Project project : projects){
            output += "Name: " + project.getName() + " Archived: " + project.getIsArchived() + "\n";
        }
        return output;
    }
}
