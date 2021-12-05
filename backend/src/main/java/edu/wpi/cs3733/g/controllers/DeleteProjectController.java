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

public class DeleteProjectController implements RequestHandler<DeleteProjectData, DeleteProjectData> {
    @Override
    public DeleteProjectData handleRequest(DeleteProjectData input, Context context) {
        try {
            if (DatabaseAccess.deleteProject(new Project(input.name))) {
                return input;
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
