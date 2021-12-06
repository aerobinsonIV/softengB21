package edu.wpi.cs3733.g.requests;

public class CreateTeammateRequest {
    String name;
    String projectName;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    public String getProjectName(){
        return projectName;
    }

    public CreateTeammateRequest(String name, String projectName){
        this.name = name;
        this.projectName = projectName;
    }

    public CreateTeammateRequest(){}
}
