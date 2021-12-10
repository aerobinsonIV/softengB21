package edu.wpi.cs3733.g.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.entities.TaskMarkValue;
import edu.wpi.cs3733.g.requests.MarkTaskRequest;
import edu.wpi.cs3733.g.responses.GenericResponse;

public class MarkTaskControllerTest extends BaseControllerTest {
    String testProjectName = "MarkTaskControllerTestProject";

    @Test
    void testSuccessfulRename() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("task");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);

        GenericResponse res = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, task.getId(), "complete"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(res.getStatusCode(), 200);
        assertEquals(res.getMessage(), "Task successfully marked");

        assertEquals(task.getMarkStatus(), TaskMarkValue.COMPLETE);
    }

    @Test
    void testUnsuccessfulMark() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("task");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);

        GenericResponse res = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, task.getId() + 100, "complete"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(res.getStatusCode(), 400);

        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);

        res = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, task.getId(), "not_markable"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(res.getStatusCode(), 400);

        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);
    }

    @Test
    void testArchive() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("task");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);

        DatabaseAccess.updateProjectArchived(project, true);

        GenericResponse res = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, task.getId(), "complete"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(res.getStatusCode(), 400);

        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);
    }
}
