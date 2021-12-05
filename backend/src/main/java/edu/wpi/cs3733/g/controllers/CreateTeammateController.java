package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.entities.Teammate;
import edu.wpi.cs3733.g.db.DatabaseAccess;

class CreateTeammateResponse {
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

public class CreateTeammateController implements RequestHandler<Teammate, CreateTeammateResponse> {
    @Override
    public CreateTeammateResponse handleRequest(Teammate input, Context context) {
        try {
            if (DatabaseAccess.createTeammate(input)) {
                return new CreateTeammateResponse(input.getName());
            }
        } catch (Exception ignored) {}
        return null;
    }
}
