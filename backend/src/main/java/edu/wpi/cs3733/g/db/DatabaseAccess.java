package edu.wpi.cs3733.g.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.entities.TaskMarkValue;
import edu.wpi.cs3733.g.entities.Teammate;

public class DatabaseAccess {
    public static String database_url = System.getenv("DB_URL");
    public static String database_username = System.getenv("DB_USERNAME");
    public static String database_password = System.getenv("DB_PASSWORD");

    private static Connection connection;

    private static Connection connect() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        if (connection != null) {
            return connection;
        }

        try {
            connection = DriverManager.getConnection(database_url, database_username, database_password);
            connection.prepareStatement("use group_project;").execute();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to connect to database");
        }
    }

    public static void forceReconnectForTesting() {
        try {
            Class.forName("org.h2.Driver");
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection("jdbc:h2:mem:" + UUID.randomUUID().toString() + ";DB_CLOSE_ON_EXIT=TRUE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean createTeammate(Teammate teammate) throws Exception {
        try {
            PreparedStatement statement = connect().prepareStatement("insert into teammate values (?, ?);");
            statement.setString(1, teammate.getName());
            statement.setString(2, teammate.getProjectName());
            statement.execute();
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to create teammate!");
        }
    }

    public static boolean removeTeammate(Teammate teammate) throws Exception {
        try {
            PreparedStatement statement = connect().prepareStatement("delete from teammate where name = ? and project = ?;");
            statement.setString(1, teammate.getName());
            statement.setString(2, teammate.getProjectName());
            statement.execute();
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to delete teammate!");
        }
    }

    public static boolean createProject(Project project) throws Exception {
        try {
            PreparedStatement statement = connect().prepareStatement("insert into project values (?, 0);");
            statement.setString(1, project.getName());
            statement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to create project!");
        }
    }

    private static void populateProjectTasksAndTeammates(Project project) throws Exception {
        String projectName = project.getName();
        //Get team members for this project
        PreparedStatement teamPS = connect().prepareStatement("select * from teammate where project = ?;");
        teamPS.setString(1, projectName);
        ResultSet rs = teamPS.executeQuery();

        //Loop through all teammates in this project
        while (rs.next()) {
            project.addTeammate(new Teammate(rs.getString("name"), projectName));
        }

        //Get task assignments for this project
        PreparedStatement taskAssignmentPS = connect().prepareStatement("select * from task_assignments where teammate_project = ?;");
        taskAssignmentPS.setString(1, projectName);
        rs = taskAssignmentPS.executeQuery();

        // TODO: There's probably a prettier way to implement this
        ArrayList<Integer> assignedTaskIds = new ArrayList<>();
        ArrayList<String> assignedTaskTeammateNames = new ArrayList<>();

        //Loop through, populate task assignment ArrayLists
        while (rs.next()) {
            assignedTaskIds.add(rs.getInt("task"));
            assignedTaskTeammateNames.add(rs.getString("teammate_name"));
        }

        //Get tasks for this project
        PreparedStatement taskPS = connect().prepareStatement("select * from task where project = ?;");
        taskPS.setString(1, projectName);
        rs = taskPS.executeQuery();

        //Loop through tasks

        Map<Integer, List<Integer>> subtasks = new HashMap<Integer, List<Integer>>(); // maps parent ID to all child IDs
        while (rs.next()) {
            int taskID = rs.getInt("id");
            Task tempTask = new Task(rs.getString("name"), taskID);

            // update task assigned members
            for (int i = 0; i < assignedTaskIds.size(); i++) {
                if (assignedTaskIds.get(i) == taskID) {
                    //We found a task assignment that applies to this task, add this teammate
                    tempTask.assignTeammate(new Teammate(assignedTaskTeammateNames.get(i), projectName));
                }
            }

            // find out and update task status
            PreparedStatement taskStatus = connect().prepareStatement("select * from task where id = ?;");
            taskStatus.setInt(1, tempTask.getId());
            ResultSet taskRs = taskStatus.executeQuery();
            taskRs.next();
            
            TaskMarkValue status = TaskMarkValue.valueOf(taskRs.getString("status").toUpperCase());
            tempTask.setMarkStatus(status);

            // find any and all subtasks (will update after all subtasks have been found)
            int parentID = taskRs.getInt("parent");

            if (parentID != 0) { // task is a subtask
                subtasks.putIfAbsent(parentID, new ArrayList<Integer>());
                subtasks.get(parentID).add(taskID);
            }

            project.addTask(tempTask);
        }

        // update all tasks with subtasks
        for (Task task : project.getTasks()) {
            if (subtasks.containsKey(task.getId())) {
                for (int childID : subtasks.get(task.getId()))
                task.addSubtask(new Task("", childID));
            }
        }
    }

    public static Project getProject(String projectName) throws Exception {
        try {

            //Get project with specified name (only for archive status)
            PreparedStatement projectPS = connect().prepareStatement("select * from project where name = ?");
            projectPS.setString(1, projectName);

            ResultSet rs = projectPS.executeQuery();

            rs.next();

            Project project = new Project(projectName);
            project.setArchived(rs.getInt("archived") > 0);

            populateProjectTasksAndTeammates(project);

            return project;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to get project!");
        }
    }

    public static ArrayList<Project> getAllProjects() throws Exception {
        try {

            //Get project with specified name (only for archive status)
            PreparedStatement projectPS = connect().prepareStatement("select * from project");
            ResultSet rs = projectPS.executeQuery();

            ArrayList<Project> projects = new ArrayList<>();

            while (rs.next()) {
                Project project = new Project(rs.getString("name"));
                project.setArchived(rs.getInt("archived") > 0);
                populateProjectTasksAndTeammates(project);
                projects.add(project);

            }

            return projects;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to get all the projects!");
        }
    }

    public static boolean checkProjectExists(String projectName) throws Exception{
        try {

            //Get project with specified name (only for archive status)
            PreparedStatement projectPS = connect().prepareStatement("select * from project where name = ?");
            projectPS.setString(1, projectName);

            ResultSet rs = projectPS.executeQuery();

            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to check if project exists!");
        }
    }

    public static boolean deleteProject(Project project) throws Exception {
        try {
            PreparedStatement statement = connect().prepareStatement("delete from project where name = ?;");
            statement.setString(1, project.getName());
            statement.execute();
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to delete project!");
        }
    }

    public static boolean createTaskAssignment(String projectName, int taskId, String teammate_name) throws Exception {
        try {
            PreparedStatement statement = connect().prepareStatement("insert into task_assignments values (?, ?, ?);");
            statement.setString(1, "" + taskId);
            statement.setString(2, teammate_name);
            statement.setString(3, projectName);
            statement.execute();
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to create task assignment!");
        }
    }

    public static void initSchemaForTesting() {
        try {
            connect().prepareStatement("create table project\n" +
                    "(\n" +
                    "    name     varchar(100)   not null primary key,\n" +
                    "    archived boolean not null default false\n" +
                    ");\n" +
                    "\n" +
                    "create table task\n" +
                    "(\n" +
                    "    id      int                                       not null primary key auto_increment,\n" +
                    "    name    varchar(100)                                         not null,\n" +
                    "    parent  int                                          null,\n" +
                    "    status  enum ('complete', 'in_progress', 'not_markable') not null default 'in_progress',\n" +
                    "    project varchar(100)                                         not null,\n" +
                    "    foreign key (parent) references task (id) on delete cascade on update cascade,\n" +
                    "    foreign key (project) references project (name) on delete cascade on update cascade\n" +
                    ");\n" +
                    "\n" +
                    "create table teammate\n" +
                    "(\n" +
                    "    name    varchar(100) not null,\n" +
                    "    project varchar(100) not null,\n" +
                    "    primary key (name, project),\n" +
                    "    foreign key (project) references project (name) on delete cascade on update cascade\n" +
                    ");\n" +
                    "\n" +
                    "create table task_assignments\n" +
                    "(\n" +
                    "    task             int  not null,\n" +
                    "    teammate_name    varchar(100) not null,\n" +
                    "    teammate_project varchar(100) not null,\n" +
                    "    primary key (task, teammate_name, teammate_project),\n" +
                    "    foreign key (task) references task (id) on delete cascade on update cascade,\n" +
                    "    foreign key (teammate_name, teammate_project) references teammate (name, project) on delete cascade on update cascade\n" +
                    ");\n").execute();
        } catch (Exception ignored) {
        }
    }

    public static Task createTask(Project project, Task task) throws Exception {
        try {
            Connection conn = DatabaseAccess.connect();

            PreparedStatement proj = conn.prepareStatement("select * from project where name=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            proj.setString(1, project.getName());
            // System.out.println("Before createTask exec");
            proj.execute();
            // System.out.println("After createTask exec");

            proj.getResultSet().last();

            if (proj.getResultSet().getRow() == 0) {
                throw new Exception("Project of name " + project.getName() + " not found in database.");
            }

            PreparedStatement newTask = conn.prepareStatement("insert into task (name, project) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            newTask.setString(1, task.getName());
            newTask.setString(2, project.getName());

            newTask.execute();

            // https://stackoverflow.com/questions/1915166/how-to-get-the-insert-id-in-jdbc
            try (ResultSet generatedKeys = newTask.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getInt(1));
                } else {
                    throw new Exception("Create task failed, no ID obtained.");
                }
            }

            return task;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to create task.");
        }
    }

    public static boolean renameTask(int id, String newName) throws Exception {
        try {
            Connection conn = DatabaseAccess.connect();

            PreparedStatement proj = conn.prepareStatement("update task set name = ? where id = ?");

            proj.setString(1, newName);
            proj.setInt(2, id);
            proj.execute();

            return proj.getUpdateCount() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to rename task.");
        }
    }

    public static boolean markTask(int id, TaskMarkValue status) throws Exception {
        try {
            Connection conn = DatabaseAccess.connect();

            PreparedStatement proj = conn.prepareStatement("update task set status = ? where id = ?");

            proj.setString(1, status.name().toLowerCase());
            proj.setInt(2, id);
            proj.execute();

            return proj.getUpdateCount() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to mark task.");
        }
    }

    public static void updateParentStatus(int childID) throws Exception {
        try {
            Connection conn = DatabaseAccess.connect();

            PreparedStatement getParent = conn.prepareStatement("select * from task where id = ?");
            getParent.setInt(1, childID);
            ResultSet rs = getParent.executeQuery();
            rs.next();

            int parentID = rs.getInt("parent");

            while(parentID != 0) {
                PreparedStatement getChildren = conn.prepareStatement("select * from task where parent = ?");
                getChildren.setInt(1, parentID);
                ResultSet rsChildren = getChildren.executeQuery();
                
                boolean allComplete = true;

                while (allComplete && rsChildren.next()) {
                    allComplete &= rsChildren.getString("status").equals("complete");
                }

                if (allComplete) {
                    markTask(parentID, TaskMarkValue.COMPLETE);
                } else {
                    markTask(parentID, TaskMarkValue.IN_PROGRESS);
                }

                // Update rs so that the outer while loop will look for the parent's parent
                getParent = conn.prepareStatement("select * from task where id = ?");
                getParent.setInt(1, parentID);
                ResultSet rsParent = getParent.executeQuery();
                rsParent.next();

                parentID = rsParent.getInt("parent");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to mark task.");
        }
    }

    public static boolean hasChild(int taskID) throws Exception {
        try {
            Connection conn = DatabaseAccess.connect();
            PreparedStatement checkParent = conn.prepareStatement("select * from task where parent = ?");
            checkParent.setInt(1, taskID);
            ResultSet rs = checkParent.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to set parent task.");
        }
    }

    public static void setTaskParent(int parentID, int childID) throws Exception {
        try {
            Connection conn = DatabaseAccess.connect();
            PreparedStatement proj = conn.prepareStatement("update task set parent = ? where id = ?");

            proj.setInt(1, parentID);
            proj.setInt(2, childID);
            proj.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to set parent task.");
        }
    }

    public static Project findProjectWithTask(int id) throws Exception {
        try {
            PreparedStatement task = connect().prepareStatement("select * from task where id = ?");
            task.setInt(1, id);
            ResultSet rs = task.executeQuery();
            rs.next();
            String projectName = rs.getString("project");

            return DatabaseAccess.getProject(projectName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to find project");
        }
    }

    public static int getParent(int childID) throws Exception {
        try {
            PreparedStatement task = connect().prepareStatement("select parent from task where id = ?");
            task.setInt(1, childID);
            ResultSet rs = task.executeQuery();
            rs.next();
            int parentID = rs.getInt("parent");

            return parentID;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to find project");
        }
    }

    public static boolean updateProjectArchived(Project project, boolean archived) throws Exception {
        try {
            PreparedStatement statement = connect().prepareStatement("update project set archived = ? where name = ?");

            statement.setBoolean(1, archived);
            statement.setString(2, project.getName());

            statement.execute();

            // Ensure that we actually found a project to archive.
            return statement.getUpdateCount() > 0;
        } catch (Exception e) {
            throw new Exception("Failed to update project archived status!");
        }
    }
}
