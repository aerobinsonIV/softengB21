package edu.wpi.cs3733.g.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.entities.Teammate;
import edu.wpi.cs3733.g.requests.DecomposeTaskRequest;
import edu.wpi.cs3733.g.responses.GenericResponse;

public class DecomposeTaskControllerTest extends BaseControllerTest {
    String testProjectName = "DecomposeTaskControllerTestProject";

    @Test
    void testSuccessfulDecompose() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("task");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        GenericResponse res = new DecomposeTaskController().handleRequest(new DecomposeTaskRequest(task.getId(), "childTask"), null);
        List<Task> tasks = DatabaseAccess.getProject(testProjectName).getTasks();

        assertEquals(tasks.size(), 2);

        assertEquals(res.getStatusCode(), 200);
        assertEquals(res.getMessage(), "Task successfully decomposed");

        assertEquals(DatabaseAccess.getParent(task.getId() + 1), task.getId());
    }

    @Test
    void testNestedDecompose() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("task");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        GenericResponse res = new DecomposeTaskController().handleRequest(new DecomposeTaskRequest(task.getId(), "childTask"), null);
        GenericResponse res1 = new DecomposeTaskController().handleRequest(new DecomposeTaskRequest(task.getId() + 1, "grandChildTask"), null);
        List<Task> tasks = DatabaseAccess.getProject(testProjectName).getTasks();

        assertEquals(tasks.size(), 3);

        assertEquals(res.getStatusCode(), 200);
        assertEquals(res.getMessage(), "Task successfully decomposed");

        assertEquals(res1.getStatusCode(), 200);
        assertEquals(res1.getMessage(), "Task successfully decomposed");

        assertEquals(DatabaseAccess.getParent(task.getId() + 1), task.getId());
        assertEquals(DatabaseAccess.getParent(task.getId() + 2), task.getId() + 1);
    }

    @Test
    void testReassignOnDecompose() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("task");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        DatabaseAccess.createTeammate(new Teammate("Alice", testProjectName));
        DatabaseAccess.createTeammate(new Teammate("Bob", testProjectName));
        DatabaseAccess.createTeammate(new Teammate("Ciera", testProjectName));

        DatabaseAccess.createTaskAssignment(testProjectName, task.getId(), "Alice");
        DatabaseAccess.createTaskAssignment(testProjectName, task.getId(), "Bob");
        DatabaseAccess.createTaskAssignment(testProjectName, task.getId(), "Ciera");
        project = DatabaseAccess.getProject(testProjectName);

        assertEquals(project.getTeam().size(), 3);
        assertEquals(project.getTask(task.getId()).getTeammates().size(), 3);

        GenericResponse res = new DecomposeTaskController().handleRequest(new DecomposeTaskRequest(task.getId(), "childTask"), null);
        project = DatabaseAccess.getProject(testProjectName);

        assertEquals(res.getStatusCode(), 200);
        assertEquals(res.getMessage(), "Task successfully decomposed");

        assertEquals(project.getTasks().size(), 2);
        assertEquals(project.getTeam().size(), 3);
        assertEquals(project.getTask(task.getId()).getTeammates().size(), 0);
        assertEquals(project.getTask(task.getId() + 1).getTeammates().size(), 3);

        GenericResponse res1 = new DecomposeTaskController().handleRequest(new DecomposeTaskRequest(task.getId() + 1, "grandChildTask"), null);
        project = DatabaseAccess.getProject(testProjectName);

        assertEquals(res1.getStatusCode(), 200);
        assertEquals(res1.getMessage(), "Task successfully decomposed");

        assertEquals(DatabaseAccess.getParent(task.getId() + 1), task.getId());
        assertEquals(DatabaseAccess.getParent(task.getId() + 2), task.getId() + 1);

        assertEquals(project.getTasks().size(), 3);
        assertEquals(project.getTeam().size(), 3);
        assertEquals(project.getTask(task.getId()).getTeammates().size(), 0);
        assertEquals(project.getTask(task.getId() + 1).getTeammates().size(), 0);
        assertEquals(project.getTask(task.getId() + 2).getTeammates().size(), 3);
    }

    @Test
    void testUnsuccessfulDecompose() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("task");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        GenericResponse res = new DecomposeTaskController().handleRequest(new DecomposeTaskRequest(task.getId() + 100, "childTask"), null);
        List<Task> tasks = DatabaseAccess.getProject(testProjectName).getTasks();

        assertEquals(tasks.size(), 1);

        assertEquals(res.getStatusCode(), 400);
    }

    @Test
    void testArchive() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("task");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        DatabaseAccess.updateProjectArchived(project, true);

        GenericResponse res = new DecomposeTaskController().handleRequest(new DecomposeTaskRequest(task.getId(), "childTask"), null);
        List<Task> tasks = DatabaseAccess.getProject(testProjectName).getTasks();

        assertEquals(tasks.size(), 1);

        assertEquals(res.getStatusCode(), 400);
    }
}
