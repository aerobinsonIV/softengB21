package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.requests.GetProjectRequest;

// Note: Return type (second type in angle brackets) is a placeholder
public class GetProjectController implements RequestHandler<GetProjectRequest, GetProjectRequest> {

    @Override
    public GetProjectRequest handleRequest(GetProjectRequest req, Context context) {

        // TODO: This will involve calling whatever method retrieves a project from the database
        Project project = new Project(req.getName());

        System.out.println("In lambda for GetProjectRequest, project name is " + project.getName());

        return null;
    }
}
