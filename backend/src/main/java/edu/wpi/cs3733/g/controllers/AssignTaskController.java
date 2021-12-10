package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.requests.AssignTaskRequest;

public class AssignTaskController implements RequestHandler<AssignTaskRequest, Task> {
    @Override
    public Task handleRequest(AssignTaskRequest req, Context context) {
        return null;
    }
}
