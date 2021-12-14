package edu.wpi.cs3733.g.controllers;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.requests.GetAllProjectsRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetAllProjectsTest extends BaseControllerTest {
    @Test
    void test() throws Exception {
        GetAllProjectsController controller = new GetAllProjectsController();

        Project project = new Project("P1");

        DatabaseAccess.createProject(project);

        GetAllProjectsRequest request = new GetAllProjectsRequest();

        Assertions.assertEquals(project.getName(), controller.handleRequest(request, null).getProjects().get(0).getName());
        
    }
}