package edu.wpi.cs3733.g.requests;

public class CreateTaskRequest {
    String projectName;
    String taskName;

    public String getProjectName() {
        return projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public CreateTaskRequest(String pname, String tname) {
        this.projectName = pname;
        this.taskName = tname;
    }

    public CreateTaskRequest() {}

    @Override
    public String toString() {
        return "CreateTaskRequest{" +
                "project_name='" + projectName + '\'' +
                "task_name='" + taskName + "\''" + '}';
    }
}
