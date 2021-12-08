package edu.wpi.cs3733.g.controllers;

import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.requests.CreateTeammateRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateTeammateControllerTest extends BaseControllerTest {
    @Test
    void test() throws Exception {
        DatabaseAccess.createProject(new Project("P1"));
        DatabaseAccess.createProject(new Project("P2"));

        CreateTeammateRequest t1 = new CreateTeammateRequest("A", "P1");
        CreateTeammateRequest t2 = new CreateTeammateRequest("B", "P1");
        CreateTeammateRequest t3 = new CreateTeammateRequest("A", "P2");

        CreateTeammateController controller = new CreateTeammateController();

        Assertions.assertNotNull(controller.handleRequest(t1, null));
        Assertions.assertNotNull(controller.handleRequest(t2, null));
        Assertions.assertNotNull(controller.handleRequest(t3, null));
    }
}
