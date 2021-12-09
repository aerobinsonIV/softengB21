package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.requests.RenameTaskRequest;
import edu.wpi.cs3733.g.responses.RenameTaskResponse;

public class RenameTaskController implements RequestHandler<RenameTaskRequest, RenameTaskResponse> {
    @Override
    public RenameTaskResponse handleRequest(RenameTaskRequest input, Context context) {
        try {
            if (DatabaseAccess.findProjectWithTask(input.getID()).getIsArchived()) 
                return new RenameTaskResponse(400, "Project is archived");

            if (DatabaseAccess.renameTask(input.getID(), input.getNewName()))
                return new RenameTaskResponse(200, "Task successfully renamed");

            return new RenameTaskResponse(400, "Task not found or renamed");
        } catch (Exception e) {
            e.printStackTrace();
            return new RenameTaskResponse(400, "Exception when trying to rename task: " + e.getMessage());
        }
    }
}
