package edu.wpi.cs3733.g.controllers;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Teammate;
import edu.wpi.cs3733.g.requests.RemoveTeammateRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RemoveTeammateControllerTest extends BaseControllerTest {
    @Test
    void test() throws Exception {
        DatabaseAccess.createProject(new Project("P1"));
        DatabaseAccess.createProject(new Project("P2"));

        RemoveTeammateRequest t1 = new RemoveTeammateRequest("A", "P1");
        RemoveTeammateRequest t2 = new RemoveTeammateRequest("B", "P1");
        RemoveTeammateRequest t3 = new RemoveTeammateRequest("A", "P2");

        DatabaseAccess.createTeammate(new Teammate(t1.getName(), t1.getProjectName()));
        DatabaseAccess.createTeammate(new Teammate(t2.getName(), t2.getProjectName()));
        DatabaseAccess.createTeammate(new Teammate(t3.getName(), t3.getProjectName()));

        RemoveTeammateController controller = new RemoveTeammateController();

        Assertions.assertNotNull(controller.handleRequest(t1, null));
        Assertions.assertNotNull(controller.handleRequest(t2, null));
        Assertions.assertNotNull(controller.handleRequest(t3, null));
    }
}
