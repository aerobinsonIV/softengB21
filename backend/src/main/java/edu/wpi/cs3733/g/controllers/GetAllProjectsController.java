package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.requests.CreateProjectRequest;
import edu.wpi.cs3733.g.requests.GetAllProjectsRequest;

// Note: Return type (second type in angle brackets) is a placeholder.
// Also, we probably don't need the empty GetAllProjectsRequest class.
public class GetAllProjectsController implements RequestHandler<GetAllProjectsRequest, GetAllProjectsRequest> {

    @Override
    public GetAllProjectsRequest handleRequest(GetAllProjectsRequest req, Context context) {
        System.out.println("In lambda for GetAllProjectsController");


        // TODO: Return array of all projects
        return null;
    }
}
