package edu.wpi.cs3733.g.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.requests.RenameTaskRequest;
import edu.wpi.cs3733.g.responses.RenameTaskResponse;

public class RenameTaskControllerTest extends BaseControllerTest {
    String testProjectName = "RenameTaskControllerTestProject";

    @Test
    void testSuccessfulRename() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("oldName");
        task = DatabaseAccess.createTask(project, task);

        assertEquals(task.getName(), "oldName");
        System.out.println("assigned taskID: " + task.getId());

        RenameTaskResponse res = new RenameTaskController().handleRequest(new RenameTaskRequest(task.getId(), "newName"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(res.getMessage(), "Task successfully renamed");
        assertEquals(res.getStatusCode(), 200);

        assertEquals(task.getName(), "newName");

    }

    @Test
    void testUnsuccessfulRename() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        DatabaseAccess.createTask(project, new Task("oldName"));
        Task task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(task.getName(), "oldName");

        RenameTaskResponse res = new RenameTaskController().handleRequest(new RenameTaskRequest(task.getId() + 100, "newName"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(task.getName(), "oldName");

        assertEquals(res.getStatusCode(), 400);
    }

    // public static void main(String[] args) {
    //     try {
    //         DatabaseAccess.forceReconnectForTesting();
    //         DatabaseAccess.initSchemaForTesting();
            
    //         testSuccessfulRename();
    //         // testUnsuccessfulRename();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}
