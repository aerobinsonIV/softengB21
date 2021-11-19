package edu.wpi.cs3733.g.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.entities.TaskMarkValue;
import edu.wpi.cs3733.g.entities.Teammate;

public class TestTask {
    @Test
    public void testName(){
        Task testTask = new Task("Write unit tests", 42);
        assertTrue(testTask.getName().equals("Write unit tests"));

    }

    @Test
    public void testNameNull(){
        Task testTask = new Task(null, 42);
        assertFalse(testTask.getName() != null);

    }

    @Test
    public void testNameEmpty(){
        Task testTask = new Task("", 42);
        assertTrue(testTask.getName().equals(""));

    }

    @Test
    public void testId(){
        Task testTask = new Task("", 42);
        assertEquals(42, testTask.getId());
    }

    @Test
    public void testMark(){
        Task testTask = new Task("", 42);
        assertEquals(TaskMarkValue.IN_PROGRESS, testTask.getMark());
        
        testTask.setMark(TaskMarkValue.COMPLETE);
        assertEquals(TaskMarkValue.COMPLETE, testTask.getMark());

        testTask.setMark(TaskMarkValue.NOT_MARKABLE);
        assertEquals(TaskMarkValue.NOT_MARKABLE, testTask.getMark());
    }

    @Test
    public void testMarkNull(){
        Task testTask = new Task("", 42);

        testTask.setMark(null);
        assertEquals(null, testTask.getMark());
    }

    @Test
    public void testAssignedTeammatesStartsEmpty(){
        Task testTask = new Task("", 42);
        
        assertEquals(0, testTask.getAssignedTeammates().size());
    }

    @Test
    public void testAssignOneTeammate(){
        Task testTask = new Task("", 42);
        
        Teammate peebo = new Teammate("Peebo");
        testTask.assignTeammate(peebo);

        assertEquals(1, testTask.getAssignedTeammates().size());
        assertTrue(testTask.getAssignedTeammates().contains(peebo));
    }

    @Test
    public void testAssignMultipleTeammates(){
        Task testTask = new Task("", 42);
        
        Teammate peebo = new Teammate("Peebo");
        testTask.assignTeammate(peebo);

        Teammate iv = new Teammate("Iv");
        testTask.assignTeammate(iv);

        assertEquals(2, testTask.getAssignedTeammates().size());
        assertTrue(testTask.getAssignedTeammates().contains(peebo));
        assertTrue(testTask.getAssignedTeammates().contains(iv));
    }

    @Test
    public void testRemoveOneTeammateByReference(){
        Task testTask = new Task("", 42);
        
        Teammate peebo = new Teammate("Peebo");
        testTask.assignTeammate(peebo);
        Teammate iv = new Teammate("Iv");
        testTask.assignTeammate(iv);

        testTask.removeTeammate(peebo);

        assertEquals(1, testTask.getAssignedTeammates().size());
        assertFalse(testTask.getAssignedTeammates().contains(peebo));
        assertTrue(testTask.getAssignedTeammates().contains(iv));
    }

    @Test
    public void testRemoveTwoTeammatesByReference(){
        Task testTask = new Task("", 42);
        
        Teammate peebo = new Teammate("Peebo");
        testTask.assignTeammate(peebo);
        Teammate iv = new Teammate("Iv");
        testTask.assignTeammate(iv);

        testTask.removeTeammate(peebo);
        testTask.removeTeammate(iv);

        assertEquals(0, testTask.getAssignedTeammates().size());
        assertFalse(testTask.getAssignedTeammates().contains(peebo));
        assertFalse(testTask.getAssignedTeammates().contains(iv));
    }
}
