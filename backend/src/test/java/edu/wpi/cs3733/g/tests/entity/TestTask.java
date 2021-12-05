package edu.wpi.cs3733.g.tests.entity;

import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.entities.TaskMarkValue;
import edu.wpi.cs3733.g.entities.Teammate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestTask {
    @Test
    public void testName() {
        Task testTask = new Task("Write unit tests", 42);
        assertTrue(testTask.getName().equals("Write unit tests"));

    }

    @Test
    public void testNameNull() {
        Task testTask = new Task(null, 42);
        assertFalse(testTask.getName() != null);

    }

    @Test
    public void testNameEmpty() {
        Task testTask = new Task("", 42);
        assertTrue(testTask.getName().equals(""));

    }

    @Test
    public void testId() {
        Task testTask = new Task("", 42);
        assertEquals(42, testTask.getId());
    }

    @Test
    public void testMark() {
        Task testTask = new Task("", 42);
        assertEquals(TaskMarkValue.IN_PROGRESS, testTask.getMarkStatus());

        testTask.setMarkStatus(TaskMarkValue.COMPLETE);
        assertEquals(TaskMarkValue.COMPLETE, testTask.getMarkStatus());

        testTask.setMarkStatus(TaskMarkValue.NOT_MARKABLE);
        assertEquals(TaskMarkValue.NOT_MARKABLE, testTask.getMarkStatus());
    }

    @Test
    public void testMarkNull() {
        Task testTask = new Task("", 42);

        testTask.setMarkStatus(null);
        assertEquals(null, testTask.getMarkStatus());
    }

    @Test
    public void testAssignedTeammatesStartsEmpty() {
        Task testTask = new Task("", 42);

        assertEquals(0, testTask.getTeammates().size());
    }

    @Test
    public void testAssignOneTeammate() {
        Task testTask = new Task("", 42);

        Teammate peebo = new Teammate("Peebo", "P");
        testTask.assignTeammate(peebo);

        assertEquals(1, testTask.getTeammates().size());
        assertTrue(testTask.getTeammates().contains(peebo));
    }

    @Test
    public void testAssignMultipleTeammates() {
        Task testTask = new Task("", 42);

        Teammate peebo = new Teammate("Peebo", "P");
        testTask.assignTeammate(peebo);

        Teammate iv = new Teammate("Iv", "P");
        testTask.assignTeammate(iv);

        assertEquals(2, testTask.getTeammates().size());
        assertTrue(testTask.getTeammates().contains(peebo));
        assertTrue(testTask.getTeammates().contains(iv));
    }

    @Test
    public void testRemoveOneTeammateByReference() {
        Task testTask = new Task("", 42);

        Teammate peebo = new Teammate("Peebo", "P");
        testTask.assignTeammate(peebo);
        Teammate iv = new Teammate("Iv", "P");
        testTask.assignTeammate(iv);

        testTask.removeTeammate(peebo);

        assertEquals(1, testTask.getTeammates().size());
        assertFalse(testTask.getTeammates().contains(peebo));
        assertTrue(testTask.getTeammates().contains(iv));
    }

    @Test
    public void testRemoveTwoTeammatesByReference() {
        Task testTask = new Task("", 42);

        Teammate peebo = new Teammate("Peebo", "P");
        testTask.assignTeammate(peebo);
        Teammate iv = new Teammate("Iv", "P");
        testTask.assignTeammate(iv);

        testTask.removeTeammate(peebo);
        testTask.removeTeammate(iv);

        assertEquals(0, testTask.getTeammates().size());
        assertFalse(testTask.getTeammates().contains(peebo));
        assertFalse(testTask.getTeammates().contains(iv));
    }

    @Test
    public void testRemoveOneTeammateByName() {
        Task testTask = new Task("", 42);

        Teammate peebo = new Teammate("Peebo", "P");
        testTask.assignTeammate(peebo);
        Teammate iv = new Teammate("Iv", "P");
        testTask.assignTeammate(iv);

        testTask.removeTeammate("Peebo");

        assertEquals(1, testTask.getTeammates().size());
        assertFalse(testTask.getTeammates().contains(peebo));
        assertTrue(testTask.getTeammates().contains(iv));
    }

    @Test
    public void testRemoveTwoTeammatesByName() {
        Task testTask = new Task("", 42);

        Teammate peebo = new Teammate("Peebo", "P");
        testTask.assignTeammate(peebo);
        Teammate iv = new Teammate("Iv", "P");
        testTask.assignTeammate(iv);

        testTask.removeTeammate("Peebo");
        testTask.removeTeammate("Iv");

        assertEquals(0, testTask.getTeammates().size());
        assertFalse(testTask.getTeammates().contains(peebo));
        assertFalse(testTask.getTeammates().contains(iv));
    }

    //TODO: test removing teammates that don't exist and make sure nothing happens

    @Test
    public void testIsLeafTask() {
        Task testTask = new Task("", 42);
        Task testSubtask = new Task("", 1337);

        assertTrue(testTask.isLeafTask());
        testTask.addSubtask(testSubtask);
        assertFalse(testTask.isLeafTask());
    }

    @Test
    public void testSubtasks() {
        Task testTask = new Task("", 42);
        Task testSubtask1 = new Task("", 1337);
        Task testSubtask2 = new Task("", 5);

        assertEquals(0, testTask.getSubtasks().size());
        testTask.addSubtask(testSubtask1);
        testTask.addSubtask(testSubtask2);

        ArrayList<Task> subtasks = testTask.getSubtasks();

        assertEquals(2, subtasks.size());

        assertTrue(subtasks.contains(testSubtask1));
        assertTrue(subtasks.contains(testSubtask2));
    }
}
