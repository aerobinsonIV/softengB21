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

        try {
            Project projectToDelete = DatabaseAccess.getProject(input.getName());
            
            if (DatabaseAccess.deleteProject(new Project(input.name))) {
                return projectToDelete;
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
