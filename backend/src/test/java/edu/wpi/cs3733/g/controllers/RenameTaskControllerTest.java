package edu.wpi.cs3733.g.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.requests.RenameTaskRequest;
import edu.wpi.cs3733.g.responses.RenameTaskResponse;

public class RenameTaskControllerTest extends BaseControllerTest {
    @Test
    void testSuccessfulRename() throws Exception {
        Project project = new Project("P1");

        DatabaseAccess.createProject(project);
        DatabaseAccess.createTask(project, new Task("oldName"));
        Task task = DatabaseAccess.getProject("P1").getTasks().get(0);

        assertEquals(task.getName(), "oldName");

        RenameTaskResponse res = new RenameTaskController().handleRequest(new RenameTaskRequest(task.getId(), "newName"), null);
        task = DatabaseAccess.getProject("P1").getTasks().get(0);

        assertEquals(task.getName(), "newName");

        assertEquals(res.getStatusCode(), 200);
    }

    @Test
    void testUnsuccessfulRename() throws Exception {
        Project project = new Project("P1");

        DatabaseAccess.createProject(project);
        DatabaseAccess.createTask(project, new Task("oldName"));
        Task task = DatabaseAccess.getProject("P1").getTasks().get(0);

        assertEquals(task.getName(), "oldName");

        RenameTaskResponse res = new RenameTaskController().handleRequest(new RenameTaskRequest(task.getId() + 100, "newName"), null);
        task = DatabaseAccess.getProject("P1").getTasks().get(0);

        assertEquals(task.getName(), "oldName");

        assertEquals(res.getStatusCode(), 400);

        // test archived project now

        Project project1 = new Project("P2");

        DatabaseAccess.createProject(project1);
        DatabaseAccess.createTask(project1, new Task("oldName"));
        Task task1 = DatabaseAccess.getProject("P2").getTasks().get(0);

        assertEquals(task1.getName(), "oldName");

        DatabaseAccess.updateProjectArchived(project1, true);

        RenameTaskResponse res1 = new RenameTaskController().handleRequest(new RenameTaskRequest(task1.getId(), "newName"), null);
        task1 = DatabaseAccess.getProject("P1").getTasks().get(0);

        assertEquals(task1.getName(), "oldName");

        assertEquals(res1.getStatusCode(), 400);
    }
}
