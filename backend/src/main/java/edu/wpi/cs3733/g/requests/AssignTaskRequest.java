package edu.wpi.cs3733.g.requests;

public class AssignTaskRequest {
    String projectName;
    int taskId;
    String teammateName;
    boolean assign; // false for unassign

    public void setProjectName(String name){
        projectName = name;
    }

    public String getProjectName(){
        return projectName; 
    }

    public void setTaskId(int id){
        taskId = id;
    }

    public int getTaskId(){
        return taskId;
    }

    public void setTeammateName(String name){
        teammateName = name;
    }

    public String getTeammateName(){
        return teammateName;   
    }

    public void setAssign(boolean assign) {
        this.assign = assign;
    }

    public boolean getAssign() {
        return assign;
    }

    public AssignTaskRequest(String projectName, int taskId, String teammateName, boolean assign){
        this.projectName = projectName;
        this.taskId = taskId;
        this.teammateName = teammateName;
        this.assign = assign;
    }

    public AssignTaskRequest(){
        
    }
    
}
