package edu.wpi.cs3733.g.controllers;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Teammate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RemoveTeammateControllerTest extends BaseControllerTest {
    @Test
    void test() throws Exception {
        DatabaseAccess.createProject(new Project("P1"));
        DatabaseAccess.createProject(new Project("P2"));

        Teammate t1 = new Teammate("A", "P1");
        Teammate t2 = new Teammate("B", "P1");
        Teammate t3 = new Teammate("A", "P2");

        DatabaseAccess.createTeammate(t1);
        DatabaseAccess.createTeammate(t2);
        DatabaseAccess.createTeammate(t3);

        RemoveTeammateController controller = new RemoveTeammateController();

        Assertions.assertNotNull(controller.handleRequest(t1, null));
        Assertions.assertNotNull(controller.handleRequest(t2, null));
        Assertions.assertNotNull(controller.handleRequest(t3, null));
    }
}
