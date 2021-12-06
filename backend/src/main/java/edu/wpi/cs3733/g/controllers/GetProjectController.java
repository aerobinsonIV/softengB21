package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.requests.GetProjectRequest;

// Note: Return type (second type in angle brackets) is a placeholder
public class GetProjectController implements RequestHandler<GetProjectRequest, Project> {

    @Override
    public Project handleRequest(GetProjectRequest req, Context context) {

        try {
            Project db_project = DatabaseAccess.getProject(req.getName());

            return db_project;
        } catch (Exception e) {
            return new Project("Failed");
        }
    }
}
