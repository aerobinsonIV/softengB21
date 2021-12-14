package edu.wpi.cs3733.g.controllers;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.entities.Teammate;
import edu.wpi.cs3733.g.requests.AssignTaskRequest;
import edu.wpi.cs3733.g.requests.GetProjectRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssignTaskControllerTest extends BaseControllerTest {
    @Test
    void test() throws Exception {
        GetProjectController getProjectController = new GetProjectController();
        AssignTaskController assignTaskController = new AssignTaskController();

        Project project = new Project("P1");
    
        Task task = new Task("write unit tests");

        DatabaseAccess.createProject(project);
        Task taskWithId = DatabaseAccess.createTask(project, task);

        Teammate bob = new Teammate("Bob", "P1");
        DatabaseAccess.createTeammate(bob);

        AssignTaskRequest request = new AssignTaskRequest("P1", taskWithId.getId(), "Bob");

        assignTaskController.handleRequest(request, null);

        GetProjectRequest p1Request = new GetProjectRequest("P1");

        Assertions.assertEquals("Bob", getProjectController.handleRequest(p1Request, null).getTasks().get(0).getTeammates().get(0).getName());
        
    }
}
