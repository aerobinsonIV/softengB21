package edu.wpi.cs3733.g.responses;

public class CreateTeammateResponse {
    String name;

    public CreateTeammateResponse() {}

    public CreateTeammateResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
