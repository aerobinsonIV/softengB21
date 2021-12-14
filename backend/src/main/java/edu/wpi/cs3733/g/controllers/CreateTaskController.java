package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.requests.CreateTaskRequest;

public class CreateTaskController implements RequestHandler<CreateTaskRequest, Task> {
    @Override
    public Task handleRequest(CreateTaskRequest req, Context context) {
        System.out.println(req);
        try {
            if (DatabaseAccess.getProject(req.getProjectName()).getIsArchived()) {
                return null;
            }

            return DatabaseAccess.createTask(new Project(req.getProjectName()), new Task(req.getTaskName()));
        } catch (Exception e) {
            System.out.println("Failure");
            e.printStackTrace();
            return null;
        }
    }
}
