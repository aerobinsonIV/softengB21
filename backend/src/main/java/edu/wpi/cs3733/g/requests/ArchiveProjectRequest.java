package edu.wpi.cs3733.g.requests;

public class ArchiveProjectRequest {
    String projectName;
    boolean archive; // false for unarchive

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String name) {
        this.projectName = name;
    }

    public boolean getArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public ArchiveProjectRequest(String projectName, boolean archive) {
        this.projectName = projectName;
        this.archive = archive;
    }

    public ArchiveProjectRequest() {}
}
