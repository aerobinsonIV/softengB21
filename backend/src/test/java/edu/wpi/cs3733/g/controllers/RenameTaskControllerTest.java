package edu.wpi.cs3733.g.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.requests.RenameTaskRequest;
import edu.wpi.cs3733.g.responses.GenericResponse;

public class RenameTaskControllerTest extends BaseControllerTest {
    String testProjectName = "RenameTaskControllerTestProject";

    @Test
    void testSuccessfulRename() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("oldName");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        assertEquals(task.getName(), "oldName");

        GenericResponse res = new RenameTaskController().handleRequest(new RenameTaskRequest(task.getId(), "newName"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(res.getStatusCode(), 200);
        assertEquals(res.getMessage(), "Task successfully renamed");

        assertEquals(task.getName(), "newName");
    }

    @Test
    void testUnsuccessfulRename() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("oldName");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        assertEquals(task.getName(), "oldName");

        GenericResponse res = new RenameTaskController().handleRequest(new RenameTaskRequest(task.getId() + 100, "newName"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(task.getName(), "oldName");

        assertEquals(res.getStatusCode(), 400);
    }

    @Test
    void testArchive() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("oldName");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        assertEquals(task.getName(), "oldName");

        DatabaseAccess.updateProjectArchived(project, true);

        GenericResponse res = new RenameTaskController().handleRequest(new RenameTaskRequest(task.getId(), "newName"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(task.getName(), "oldName");

        assertEquals(res.getStatusCode(), 400);
        assertEquals(res.getMessage(), "Project is archived");

        DatabaseAccess.updateProjectArchived(project, false);
        GenericResponse res1 = new RenameTaskController().handleRequest(new RenameTaskRequest(task.getId(), "newName"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(task.getName(), "newName");

        assertEquals(res1.getStatusCode(), 200);
    }
}
