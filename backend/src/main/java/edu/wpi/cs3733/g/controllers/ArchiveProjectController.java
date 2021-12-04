package edu.wpi.cs3733.g.controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.requests.ArchiveProjectRequest;

public class ArchiveProjectController implements RequestHandler<ArchiveProjectRequest, Project> {

    @Override
    public Project handleRequest(ArchiveProjectRequest req, Context context) {

        Project project = new Project(req.getProjectName());

        try {
            boolean success = DatabaseAccess.updateProjectArchived(project, true);

            if(success) {
                project.archive();

                return project;
            } else {
                return new Project("Failed");
            }
        } catch (Exception e) {
            return new Project("Failed");
        }
    }
}
