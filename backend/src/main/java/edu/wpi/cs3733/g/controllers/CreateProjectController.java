package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;

public class CreateProjectController implements RequestHandler<CreateProjectRequest, CreateProjectResponse> {
    @Override
    public CreateProjectResponse handleRequest(CreateProjectRequest req, Context context) {
        Project project = new Project(req.getName());

        try {
            System.out.println("Creating project: " + project.getName());
            boolean success = DatabaseAccess.createProject(project);

            if(success) {
                System.out.println("Create project success");
                return new CreateProjectResponse(project);
            }
        } catch (Exception e) {
            System.out.println("Failure");
            e.printStackTrace();
            // TODO: Swap to some sort of GenericErrorResponse
            return new CreateProjectResponse(null);
        }

        return new CreateProjectResponse(null);
    }
}
