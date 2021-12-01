package edu.wpi.cs3733.g.controllers;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.requests.CreateProjectRequest;
import edu.wpi.cs3733.g.requests.GetAllProjectsRequest;
import edu.wpi.cs3733.g.responses.GetAllProjectsResponse;

// Note: Return type (second type in angle brackets) is a placeholder.
// Also, we probably don't need the empty GetAllProjectsRequest class.
public class GetAllProjectsController implements RequestHandler<GetAllProjectsRequest, GetAllProjectsResponse> {

    @Override
    public GetAllProjectsResponse handleRequest(GetAllProjectsRequest req, Context context) {
        System.out.println("In lambda for GetAllProjectsController");
        try {
            ArrayList<Project> allProjects = DatabaseAccess.getAllProjects();

            return new GetAllProjectsResponse(allProjects);

        } catch (Exception e) {
            return null;
        }
    }
}
