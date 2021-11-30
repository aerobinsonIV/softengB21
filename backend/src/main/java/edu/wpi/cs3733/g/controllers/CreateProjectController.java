package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;

public class CreateProjectController implements RequestHandler<CreateProjectRequest, Project> {
    @Override
    public Project handleRequest(CreateProjectRequest req, Context context) {
        Project project = new Project(req.getName());

        try {
            System.out.println("Creating project");
            boolean success = DatabaseAccess.createProject(project);

            if(success) {
                System.out.println("Create project success");
                return project;
            }
        } catch (Exception e) {
            System.out.println("Failure");
            e.printStackTrace();
            // TODO: Swap to some sort of GenericErrorResponse
            return new Project("Failed");
        }

        return new Project("Should not reach here");
    }
}
