package edu.wpi.cs3733.g.controllers;

import edu.wpi.cs3733.g.requests.CreateProjectRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateProjectControllerTest extends BaseControllerTest {
    @Test
    void test() throws Exception {
        CreateProjectRequest r1 = new CreateProjectRequest("P1");
        CreateProjectRequest r2 = new CreateProjectRequest("P2");

        CreateProjectController c1 = new CreateProjectController();

        CreateProjectResponse p1 = c1.handleRequest(r1, null);
        CreateProjectResponse p2 = c1.handleRequest(r2, null);

        Assertions.assertNotNull(p1.getProject());
        Assertions.assertNotNull(p2.getProject());
    }
}
