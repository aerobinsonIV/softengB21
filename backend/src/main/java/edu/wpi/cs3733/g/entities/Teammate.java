package edu.wpi.cs3733.g.entities;

public class Teammate {

    private String name;
    private String project;

    public Teammate() {}

    public Teammate(String name, String project) {
        this.name = name;
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public String getProject() {
        return project;
    }
}

