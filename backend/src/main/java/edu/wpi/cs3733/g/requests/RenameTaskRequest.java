package edu.wpi.cs3733.g.requests;

public class RenameTaskRequest {
    String projectName;
    int id;
    String name;

    public String getProjectName(){
        return projectName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RenameTaskRequest(String projectName, int id, String name) {
        this.projectName = projectName;
        this.id = id;
        this.name = name;
    }

    public RenameTaskRequest() {}

    @Override
    public String toString() {
        return "RenameTaskRequest{" +
                "id='" + id + '\'' +
                "name='" + name + "\''" + '}';
    }
}