package edu.wpi.cs3733.g.db;

import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Teammate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
            statement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to create project!");
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

    public static boolean updateProjectArchived(Project project, boolean archived) throws Exception {
        try {
            PreparedStatement statement = connect().prepareStatement("update project set archived = ? where name = ?");

            statement.setBoolean(1, archived);
            statement.setString(2, project.getName());

            statement.execute();

            return true;
        } catch (Exception e) {
            throw new Exception("Failed to update project archived status!");
        }
    }

}
