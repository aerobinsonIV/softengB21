package edu.wpi.cs3733.g.tests.response;

import edu.wpi.cs3733.g.controllers.CreateProjectResponse;
import edu.wpi.cs3733.g.entities.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestResponses {
    @Test
    public void testCreateProjectResponse() {
        Project testProject = new Project("Starship");
        CreateProjectResponse testResponse = new CreateProjectResponse(testProject);
        assertEquals(testResponse.getProject(), testProject);
    }

    @Test
    public void testCreateProjectResponseError() {
        Project testProject = new Project("Starship");
        CreateProjectResponse testResponse = new CreateProjectResponse(null);
        assertEquals(testResponse.getError(), "Failed to create project.");
    }
}
