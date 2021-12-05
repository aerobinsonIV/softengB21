package edu.wpi.cs3733.g.requests;

public class ArchiveProjectRequest {
    String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String name) {
        this.projectName = name;
    }

    public ArchiveProjectRequest(String projectName) {
        this.projectName = projectName;
    }

    public ArchiveProjectRequest() {}
}
