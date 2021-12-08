package edu.wpi.cs3733.g.controllers;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.requests.ArchiveProjectRequest;
import edu.wpi.cs3733.g.requests.CreateTaskRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArchiveProjectControllerTest extends BaseControllerTest {
    @Test
    void test() throws Exception {
        Project p1 = new Project("P1");

        DatabaseAccess.createProject(p1);

        ArchiveProjectRequest r1 = new ArchiveProjectRequest(p1.getName());

        ArchiveProjectController c1 = new ArchiveProjectController();

        Project res1 = c1.handleRequest(r1, null);

        Assertions.assertNotNull(res1.getName());
        Assertions.assertTrue(res1.getIsArchived());
    }
}
