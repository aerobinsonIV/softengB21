package edu.wpi.cs3733.g.db;

import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.entities.TaskMarkValue;
import edu.wpi.cs3733.g.entities.Teammate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

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

    public static boolean createTeammate(Teammate teammate) throws Exception {
        try {
            PreparedStatement statement = connect().prepareStatement("insert into teammate values (?, ?);");
            statement.setString(1, teammate.getName());
            statement.setString(2, teammate.getProject());
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
            statement.setString(2, teammate.getProject());
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
            System.out.println(statement.toString());
            statement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to create project!");
        }
    }

    public static Project getProject(Project project) throws Exception {
        try {
            PreparedStatement proj = connect().prepareStatement("select * from project where name=?");
            proj.setString(1, project.getName());
            proj.execute();

            proj.getResultSet().last();

            if(proj.getResultSet().getRow() == 0) {
                throw new Exception("Project row count was 0");
            }

            if(proj.getResultSet().getInt(2) == 1) {
                project.archive();
            }

            // TODO: Populate the project object with tasks and teammates
            /*
            PreparedStatement tasks = connect().prepareStatement("select * from task where project=?");
            tasks.setString(1, project.getName());
            tasks.execute();

            ResultSet rs = tasks.getResultSet();
            ResultSetMetaData md = rs.getMetaData();

            while(rs.next()) {
                //                   Task name          Task id
                Task task = new Task(rs.getString(2), rs.getInt(1));
                //                                  TaskMarkValue
                task.setMark(TaskMarkValue.values()[rs.getInt(4)]);
                task.
            }*/

            return project;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to get project!");
        }
    }

    public static ArrayList<Project> getAllProjects() throws Exception {
        try {
            PreparedStatement proj = connect().prepareStatement("select * from project");
            proj.execute();

            ResultSet results = proj.getResultSet();
            results.last(); //Move cursor to last row
            int numRows = results.getRow();

            if(numRows == 0) {
                throw new Exception("Project row count was 0");
            }

            ArrayList<Project> projects = new ArrayList<>();
            for(int i = 1; i <= numRows; i ++){
                results.absolute(i); // Move cursor to row i

                int archived = results.getInt(2);
                String projectName = results.getString(1);
                
                Project tempProject = new Project(projectName);

                if(archived == 1){
                    tempProject.archive();
                }

                projects.add(tempProject);
            }

            return projects;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to get all the projects!");
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

    public static Task createTask(Project project, Task task) throws Exception {
        try {
            Connection conn = DatabaseAccess.connect();

            PreparedStatement proj = conn.prepareStatement("select * from project where name=?");

            proj.setString(1, project.getName());
            proj.execute();

            proj.getResultSet().last();

            if(proj.getResultSet().getRow() == 0) {
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
}
