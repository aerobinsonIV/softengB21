package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Teammate;

public class RemoveTeammateController implements RequestHandler<Teammate, Project> {
    @Override
    public Project handleRequest(Teammate input, Context context) {
        try {
            Project project = new Project(input.getProject());
            if (DatabaseAccess.removeTeammate(input)) {
                return DatabaseAccess.getProject(project);
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
