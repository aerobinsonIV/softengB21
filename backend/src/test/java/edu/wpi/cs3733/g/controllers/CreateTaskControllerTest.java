package edu.wpi.cs3733.g.controllers;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.requests.CreateTaskRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateTaskControllerTest extends BaseControllerTest {
    @Test
    void test() throws Exception {
        Project p1 = new Project("P1");

        DatabaseAccess.createProject(p1);

        Task t1 = new Task("T1");

        CreateTaskRequest r1 = new CreateTaskRequest(p1.getName(), t1.getName());

        CreateTaskController c1 = new CreateTaskController();

        Task res1 = c1.handleRequest(r1, null);

        Assertions.assertNotNull(res1.getName());
    }
}
