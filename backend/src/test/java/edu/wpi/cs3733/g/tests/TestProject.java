package edu.wpi.cs3733.g.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import edu.wpi.cs3733.g.entities.Project;

public class TestProject {

    @Test
    public void TestName(){
        Project testProject = new Project("Starship");
        assertTrue(testProject.getName().equals("Starship"));

    }
}
