package edu.wpi.cs3733.g.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.entities.TaskMarkValue;

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
}
