package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.TaskMarkValue;
import edu.wpi.cs3733.g.requests.MarkTaskRequest;
import edu.wpi.cs3733.g.responses.GenericResponse;

public class MarkTaskController implements RequestHandler<MarkTaskRequest, GenericResponse>{
    @Override
    public GenericResponse handleRequest(MarkTaskRequest input, Context context) {
        try {
            String newStatus = input.getNewStatus();
            if (!(newStatus.equals("in_progress") || newStatus.equals("complete")))
                return new GenericResponse(400, "Invalid Mark Status");

            Project project = DatabaseAccess.findProjectWithTask(input.getID());
            if (project.getIsArchived()) 
                return new GenericResponse(400, "Project is archived");

            if (!project.getTask(input.getID()).isLeafTask())
                return new GenericResponse(400, "Cannot change mark status of task with subtasks");

            System.out.println(newStatus.toUpperCase());
            if (DatabaseAccess.markTask(input.getID(), TaskMarkValue.valueOf(newStatus.toUpperCase())))
                return new GenericResponse(200, "Task successfully marked");

            return new GenericResponse(400, "Task not found and/or marked");
        } catch (Exception e) {
            e.printStackTrace();
            return new GenericResponse(400, "Exception when trying to mark task: " + e.getMessage());
        }
    }
}
