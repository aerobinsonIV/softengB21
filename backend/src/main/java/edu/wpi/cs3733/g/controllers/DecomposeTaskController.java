package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.entities.TaskMarkValue;
import edu.wpi.cs3733.g.requests.DecomposeTaskRequest;
import edu.wpi.cs3733.g.responses.GenericResponse;

public class DecomposeTaskController implements RequestHandler<DecomposeTaskRequest, GenericResponse> {

    @Override
    public GenericResponse handleRequest(DecomposeTaskRequest input, Context context) {
        try {
            Project project = DatabaseAccess.findProjectWithTask(input.getParentID());
            if (project.getIsArchived()) 
                return new GenericResponse(400, "Project is archived");

            Task child = DatabaseAccess.createTask(project, new Task(input.getChildName()));
            DatabaseAccess.setTaskParent(input.getParentID(), child.getId());
            DatabaseAccess.markTask(input.getParentID(), TaskMarkValue.IN_PROGRESS); // in progress because child is in progress
            DatabaseAccess.reassignTask(input.getParentID(), child.getId()); // move all teammates from parent to child

            return new GenericResponse(200, "Task successfully decomposed");
        } catch (Exception e) {
            e.printStackTrace();
            return new GenericResponse(400, "Exception when trying to decompose task: " + e.getMessage());
        }
    }
}
