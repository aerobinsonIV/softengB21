package edu.wpi.cs3733.g.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import edu.wpi.cs3733.g.entities.Project;

public class TestProject {

    @Test
    public void testName(){
        Project testProject = new Project("Starship");
        assertTrue(testProject.getName().equals("Starship"));

    }

    @Test
    public void testNameNull(){
        Project testProject = new Project(null);
        assertFalse(testProject.getName() != null);

    }

    @Test
    public void testNameEmpty(){
        Project testProject = new Project("");
        assertTrue(testProject.getName().equals(""));

    }

    @Test
    public void testArchive(){
        Project testProject = new Project("Starship");
        assertFalse(testProject.isArchived());
        testProject.archive();
        assertTrue(testProject.isArchived());

        //Archive shouldn't be a toggle
        testProject.archive();  
        assertTrue(testProject.isArchived());
    }
}
