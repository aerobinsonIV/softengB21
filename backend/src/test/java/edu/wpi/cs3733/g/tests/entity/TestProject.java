package edu.wpi.cs3733.g.tests.entity;

import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.entities.Teammate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {

    @Test
    public void testName() {
        Project testProject = new Project("Starship");
        assertTrue(testProject.getName().equals("Starship"));

    }

    @Test
    public void testNameNull() {
        Project testProject = new Project(null);
        assertFalse(testProject.getName() != null);

    }

    @Test
    public void testNameEmpty() {
        Project testProject = new Project("");
        assertTrue(testProject.getName().equals(""));

    }

    @Test
    public void testArchive() {
        Project testProject = new Project("Starship");
        assertFalse(testProject.isArchived());
        testProject.archive();
        assertTrue(testProject.isArchived());

        //Archive shouldn't be a toggle
        testProject.archive();
        assertTrue(testProject.isArchived());
    }

    @Test
    public void testAddOneTask() {
        Project testProject = new Project("Starship");

        assertEquals(0, testProject.getAllTasks().size());

        Task testTask = new Task("Build raptor engine", 1);
        testProject.addTask(testTask);

        ArrayList<Task> allTasks = testProject.getAllTasks();

        assertEquals(1, allTasks.size());
        assertTrue(allTasks.contains(testTask));

    }

    @Test
    public void testAddTwoTasks() {
        Project testProject = new Project("Starship");

        assertEquals(0, testProject.getAllTasks().size());

        Task testProject1 = new Task("Build raptor engine", 1);
        Task testProject2 = new Task("Write flight control software", 2);
        testProject.addTask(testProject1);
        testProject.addTask(testProject2);

        ArrayList<Task> allTasks = testProject.getAllTasks();

        assertEquals(2, allTasks.size());
        assertTrue(allTasks.contains(testProject1));
        assertTrue(allTasks.contains(testProject2));

    }

    @Test
    public void testRemoveOneTaskByReference() {
        Project testProject = new Project("Starship");

        assertEquals(0, testProject.getAllTasks().size());

        Task testTask = new Task("Build raptor engine", 1);
        testProject.addTask(testTask);

        testProject.removeTask(testTask);

        assertEquals(0, testProject.getAllTasks().size());

    }

    @Test
    public void testRemoveOneTaskByID() {
        Project testProject = new Project("Starship");

        assertEquals(0, testProject.getAllTasks().size());

        Task testTask = new Task("Build raptor engine", 1);
        testProject.addTask(testTask);

        testProject.removeTask(1);

        assertEquals(0, testProject.getAllTasks().size());

    }

    @Test
    public void testGetTask() {
        Project testProject = new Project("Starship");

        assertEquals(0, testProject.getAllTasks().size());

        Task testTask = new Task("Build raptor engine", 1);
        testProject.addTask(testTask);

        assertEquals(testTask, testProject.getTask(1));

    }

    @Test
    public void testTeamStartsEmpty() {
        Project testProject = new Project("Starship");

        assertEquals(0, testProject.getTeam().size());
    }

    @Test
    public void testAssignOneTeammate() {
        Project testProject = new Project("Starship");

        Teammate peebo = new Teammate("Peebo", "P");
        testProject.addTeammate(peebo);

        assertEquals(1, testProject.getTeam().size());
        assertTrue(testProject.getTeam().contains(peebo));
    }

    @Test
    public void testAssignMultipleTeammates() {
        Project testProject = new Project("Starship");

        Teammate peebo = new Teammate("Peebo", "P");
        testProject.addTeammate(peebo);

        Teammate iv = new Teammate("Iv", "P");
        testProject.addTeammate(iv);

        assertEquals(2, testProject.getTeam().size());
        assertTrue(testProject.getTeam().contains(peebo));
        assertTrue(testProject.getTeam().contains(iv));
    }

    @Test
    public void testRemoveOneTeammateByReference() {
        Project testProject = new Project("Starship");

        Teammate peebo = new Teammate("Peebo", "P");
        testProject.addTeammate(peebo);
        Teammate iv = new Teammate("Iv", "P");
        testProject.addTeammate(iv);

        testProject.removeTeammate(peebo);

        assertEquals(1, testProject.getTeam().size());
        assertFalse(testProject.getTeam().contains(peebo));
        assertTrue(testProject.getTeam().contains(iv));
    }

    @Test
    public void testRemoveTwoTeammatesByReference() {
        Project testProject = new Project("Starship");

        Teammate peebo = new Teammate("Peebo", "P");
        testProject.addTeammate(peebo);
        Teammate iv = new Teammate("Iv", "P");
        testProject.addTeammate(iv);

        testProject.removeTeammate(peebo);
        testProject.removeTeammate(iv);

        assertEquals(0, testProject.getTeam().size());
        assertFalse(testProject.getTeam().contains(peebo));
        assertFalse(testProject.getTeam().contains(iv));
    }

    @Test
    public void testRemoveOneTeammateByName() {
        Project testProject = new Project("Starship");

        Teammate peebo = new Teammate("Peebo", "P");
        testProject.addTeammate(peebo);
        Teammate iv = new Teammate("Iv", "P");
        testProject.addTeammate(iv);

        testProject.removeTeammate("Peebo");

        assertEquals(1, testProject.getTeam().size());
        assertFalse(testProject.getTeam().contains(peebo));
        assertTrue(testProject.getTeam().contains(iv));
    }

    @Test
    public void testRemoveTwoTeammatesByName() {
        Project testProject = new Project("Starship");

        Teammate peebo = new Teammate("Peebo", "P");
        testProject.addTeammate(peebo);
        Teammate iv = new Teammate("Iv", "P");
        testProject.addTeammate(iv);

        testProject.removeTeammate("Peebo");
        testProject.removeTeammate("Iv");

        assertEquals(0, testProject.getTeam().size());
        assertFalse(testProject.getTeam().contains(peebo));
        assertFalse(testProject.getTeam().contains(iv));
    }
}
