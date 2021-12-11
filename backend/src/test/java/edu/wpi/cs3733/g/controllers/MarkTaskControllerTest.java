package edu.wpi.cs3733.g.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.entities.TaskMarkValue;
import edu.wpi.cs3733.g.requests.DecomposeTaskRequest;
import edu.wpi.cs3733.g.requests.MarkTaskRequest;
import edu.wpi.cs3733.g.responses.GenericResponse;

public class MarkTaskControllerTest extends BaseControllerTest {
    String testProjectName = "MarkTaskControllerTestProject";

    @Test
    void testSuccessfulMark() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("task");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);

        GenericResponse res = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, task.getId(), "complete"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(res.getStatusCode(), 200);
        assertEquals(res.getMessage(), "Task successfully marked");

        assertEquals(task.getMarkStatus(), TaskMarkValue.COMPLETE);
    }

    @Test
    void testMarkUpdateParent() throws Exception {
        // create example project with subtasks
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("task");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        int parentID = task.getId();
        int childID = parentID + 1;
        int grandchild1ID = childID + 1;
        int grandchild2ID = grandchild1ID + 1;

        GenericResponse createChild = new DecomposeTaskController().handleRequest(new DecomposeTaskRequest(parentID, "childTask"), null);
        GenericResponse createGrandChild1 = new DecomposeTaskController().handleRequest(new DecomposeTaskRequest(childID, "grandChild1Task"), null);
        GenericResponse createGrandChild2 = new DecomposeTaskController().handleRequest(new DecomposeTaskRequest(childID, "grandChild2Task"), null);

        assertEquals(createChild.getStatusCode(), 200);
        assertEquals(createGrandChild1.getStatusCode(), 200);
        assertEquals(createGrandChild2.getStatusCode(), 200);

        // begin mark testing
        // marking parent task should be impossible
        GenericResponse markParent = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, parentID, "complete"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);
        assertEquals(task.getId(), parentID);

        assertEquals(markParent.getStatusCode(), 400);
        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);

        // marking child task should be impossible
        GenericResponse markChild = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, childID, "complete"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(1);
        assertEquals(task.getId(), childID);

        assertEquals(markChild.getStatusCode(), 400);
        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);

        // marking first grandchild should be possible and not update child or parent (not all children of child are complete)
        GenericResponse markGrandChild1 = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, grandchild1ID, "complete"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(2);
        assertEquals(task.getId(), grandchild1ID);

        assertEquals(markGrandChild1.getStatusCode(), 200);
        assertEquals(markGrandChild1.getMessage(), "Task successfully marked");
        assertEquals(task.getMarkStatus(), TaskMarkValue.COMPLETE);
        assertEquals(DatabaseAccess.getProject(testProjectName).getTasks().get(0).getMarkStatus(), TaskMarkValue.IN_PROGRESS);
        assertEquals(DatabaseAccess.getProject(testProjectName).getTasks().get(1).getMarkStatus(), TaskMarkValue.IN_PROGRESS);

        // marking second grandchild should be possible and update child and parent (since all children of child and then of parent are complete)
        GenericResponse markGrandChild2 = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, grandchild2ID, "complete"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(3);
        assertEquals(task.getId(), grandchild2ID);

        assertEquals(markGrandChild2.getMessage(), "Task successfully marked");
        assertEquals(markGrandChild2.getStatusCode(), 200);
        assertEquals(task.getMarkStatus(), TaskMarkValue.COMPLETE);

        assertEquals(DatabaseAccess.getProject(testProjectName).getTasks().get(0).getMarkStatus(), TaskMarkValue.COMPLETE);
        assertEquals(DatabaseAccess.getProject(testProjectName).getTasks().get(1).getMarkStatus(), TaskMarkValue.COMPLETE);
        assertEquals(DatabaseAccess.getProject(testProjectName).getTasks().get(2).getMarkStatus(), TaskMarkValue.COMPLETE);
        assertEquals(DatabaseAccess.getProject(testProjectName).getTasks().get(3).getMarkStatus(), TaskMarkValue.COMPLETE);

        // marking second grandchild as in progress should change child and parent but not first grandchild
        GenericResponse markGrandChild2InProgress = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, grandchild2ID, "in_progress"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(3);
        assertEquals(task.getId(), grandchild2ID);

        assertEquals(markGrandChild2InProgress.getMessage(), "Task successfully marked");
        assertEquals(markGrandChild2InProgress.getStatusCode(), 200);
        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);

        assertEquals(DatabaseAccess.getProject(testProjectName).getTasks().get(0).getMarkStatus(), TaskMarkValue.IN_PROGRESS);
        assertEquals(DatabaseAccess.getProject(testProjectName).getTasks().get(1).getMarkStatus(), TaskMarkValue.IN_PROGRESS);
        assertEquals(DatabaseAccess.getProject(testProjectName).getTasks().get(2).getMarkStatus(), TaskMarkValue.COMPLETE);
        assertEquals(DatabaseAccess.getProject(testProjectName).getTasks().get(3).getMarkStatus(), TaskMarkValue.IN_PROGRESS);
    }

    @Test
    void testUnsuccessfulMark() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("task");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);

        GenericResponse res = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, task.getId() + 100, "complete"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(res.getStatusCode(), 400);
        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);

        res = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, task.getId(), "not_markable"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(res.getStatusCode(), 400);
        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);
    }

    @Test
    void testArchive() throws Exception {
        Project project = new Project(testProjectName);

        DatabaseAccess.createProject(project);
        Task task = new Task("task");
        project = DatabaseAccess.getProject(testProjectName);
        task = DatabaseAccess.createTask(project, task);

        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);

        DatabaseAccess.updateProjectArchived(project, true);

        GenericResponse res = new MarkTaskController().handleRequest(new MarkTaskRequest(testProjectName, task.getId(), "complete"), null);
        task = DatabaseAccess.getProject(testProjectName).getTasks().get(0);

        assertEquals(res.getStatusCode(), 400);

        assertEquals(task.getMarkStatus(), TaskMarkValue.IN_PROGRESS);
    }
}
