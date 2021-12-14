package edu.wpi.cs3733.g.controllers;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.requests.GetProjectRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetProjectTest extends BaseControllerTest {
    @Test
    void test() throws Exception {
        GetProjectController controller = new GetProjectController();

        Project project = new Project("P1");

        DatabaseAccess.createProject(project);

        GetProjectRequest p1Request = new GetProjectRequest("P1");

        Assertions.assertEquals(project.getName(), controller.handleRequest(p1Request, null).getName());
        
    }
}
