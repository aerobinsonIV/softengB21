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
            boolean success = DatabaseAccess.updateProjectArchived(project, req.getArchive());

            if(success) {
                project.setArchived(req.getArchive());

                return project;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
