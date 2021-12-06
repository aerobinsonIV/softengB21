package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Teammate;
import edu.wpi.cs3733.g.requests.RemoveTeammateRequest;

public class RemoveTeammateController implements RequestHandler<RemoveTeammateRequest, Project> {
    @Override
    public Project handleRequest(RemoveTeammateRequest input, Context context) {
        try {
            Project project = new Project(input.getProjectName());
            if (DatabaseAccess.removeTeammate(new Teammate(input.getName(), input.getProjectName()))) {
                return DatabaseAccess.getProject(project);
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
