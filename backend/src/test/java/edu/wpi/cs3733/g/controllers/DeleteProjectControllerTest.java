package edu.wpi.cs3733.g.controllers;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeleteProjectControllerTest extends BaseControllerTest {
    @Test
    void test() throws Exception {
        DeleteProjectController controller = new DeleteProjectController();

        Project p1 = new Project("P1");
        Project p2 = new Project("P2");

        DatabaseAccess.createProject(p1);
        DatabaseAccess.createProject(p2);

        // TODO: Fix this

        // Assertions.assertNotNull(controller.handleRequest(new DeleteProjectData(p1.getName()), null));
        // Assertions.assertNotNull(controller.handleRequest(new DeleteProjectData(p2.getName()), null));
    }
}
