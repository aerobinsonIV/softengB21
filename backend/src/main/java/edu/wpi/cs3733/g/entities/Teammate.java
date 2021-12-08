package edu.wpi.cs3733.g.entities;

public class Teammate {

    private String name;
    private String projectName;

    public Teammate() {}

    public Teammate(String name, String project) {
        this.name = name;
        this.projectName = project;
    }

    public String getName() {
        return name;
    }

    public String getProjectName() {
        return projectName;
    }
}

