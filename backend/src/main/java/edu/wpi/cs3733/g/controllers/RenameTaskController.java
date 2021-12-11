package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.requests.RenameTaskRequest;
import edu.wpi.cs3733.g.responses.GenericResponse;

public class RenameTaskController implements RequestHandler<RenameTaskRequest, GenericResponse> {
    @Override
    public GenericResponse handleRequest(RenameTaskRequest input, Context context) {
        try {
            if (DatabaseAccess.findProjectWithTask(input.getId()).getIsArchived()) 
                return new GenericResponse(400, "Project is archived");

            if (DatabaseAccess.renameTask(input.getId(), input.getName()))
                return new GenericResponse(200, "Task successfully renamed");

            return new GenericResponse(400, "Task not found and/or renamed");
        } catch (Exception e) {
            e.printStackTrace();
            return new GenericResponse(400, "Exception when trying to rename task: " + e.getMessage());
        }
    }
}
