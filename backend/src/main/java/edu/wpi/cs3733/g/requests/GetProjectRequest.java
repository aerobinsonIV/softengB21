package edu.wpi.cs3733.g.requests;

public class GetProjectRequest {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GetProjectRequest(String name) {
        this.name = name;
    }

    public GetProjectRequest() {}

    @Override
    public String toString() {
        return "GetProjectRequest{" +
                "project_name='" + name + '\'' +
                '}';
    }
}
