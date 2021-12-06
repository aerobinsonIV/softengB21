package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;

class DeleteProjectData {
    String name;

    public DeleteProjectData() {
    }

    public DeleteProjectData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

public class DeleteProjectController implements RequestHandler<DeleteProjectData, Project> {
    @Override
    public Project handleRequest(DeleteProjectData input, Context context) {

        System.out.println("DeleteProjectData handleRequest called with project name " + input.getName());

        try {
            Project projectToDelete = DatabaseAccess.getProject(new Project(input.name));
            System.out.println("project gotten");
            
            if (DatabaseAccess.deleteProject(new Project(input.name))) {
                System.out.println("project deleted");
                return projectToDelete;
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
