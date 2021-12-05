package edu.wpi.cs3733.g.db;

import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
import edu.wpi.cs3733.g.entities.TaskMarkValue;
import edu.wpi.cs3733.g.entities.Teammate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

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

            if (proj.getResultSet().getRow() == 0) {
                throw new Exception("Project row count was 0");
            }

            project.setArchived(proj.getResultSet().getInt(2) > 0);

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

            if (numRows == 0) {
                throw new Exception("Project row count was 0");
            }

            ArrayList<Project> projects = new ArrayList<>();
            for (int i = 1; i <= numRows; i++) {
                results.absolute(i); // Move cursor to row i

                int archived = results.getInt(2);
                String projectName = results.getString(1);

                Project tempProject = new Project(projectName);
                tempProject.setArchived(archived > 0);
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
}
