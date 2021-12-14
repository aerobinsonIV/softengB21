package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.entities.Teammate;
import edu.wpi.cs3733.g.requests.CreateTeammateRequest;
import edu.wpi.cs3733.g.db.DatabaseAccess;

public class CreateTeammateController implements RequestHandler<CreateTeammateRequest, Teammate> {
    @Override
    public Teammate handleRequest(CreateTeammateRequest input, Context context) {
        try {
            if (DatabaseAccess.getProject(input.getProjectName()).getIsArchived()) {
                return null;
            }

            if (DatabaseAccess.createTeammate(new Teammate(input.getName(), input.getProjectName()))) {
                return new Teammate(input.getName(), input.getProjectName());
            }
        } catch (Exception ignored) {}
        return null;
    }
}
