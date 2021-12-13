package edu.wpi.cs3733.g.controllers;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.requests.AssignTaskRequest;

public class AssignTaskController implements RequestHandler<AssignTaskRequest, Task> {
    @Override
    public Task handleRequest(AssignTaskRequest req, Context context) {
        try {
            if(!DatabaseAccess.checkProjectExists(req.getProjectName())) {
                System.out.println("Project does not exist");
                return null;
            }

            if (DatabaseAccess.getProject(req.getProjectName()).getIsArchived()) {
                System.out.println("Project is archived");
                return null;
            }

            if (DatabaseAccess.hasChild(req.getTaskId())) {
                System.out.println("Cannot assign teammate to task with subtasks");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Failed to access database to check if project exists");
            e.printStackTrace();
            return null;
        }

        try {
            DatabaseAccess.createTaskAssignment(req.getProjectName(), req.getTaskId(), req.getTeammateName());

        } catch (Exception e) {
            System.out.println("Failed create task assignment");
            e.printStackTrace();
            return null;
        }

        try{
            //Get project that contains this task, then isolate the task and return it
            Project modifiedProject = DatabaseAccess.getProject(req.getProjectName());

            ArrayList<Task> tasks = modifiedProject.getTasks();

            for(Task task : tasks) {
                if(task.getId() == req.getTaskId()) {
                    return task;
                }
            }

            System.out.println("Couldn't find modified task!");
            return null;
        } catch (Exception e) {
            System.out.println("Failed to retrieve modified task");
            e.printStackTrace();
            return null;
        }
    }
}
