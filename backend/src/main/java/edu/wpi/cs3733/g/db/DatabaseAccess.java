package edu.wpi.cs3733.g.db;

import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Teammate;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseAccess {
    public static String database_url = System.getenv("DB_URL");
    public static String database_username = System.getenv("DB_USERNAME");
    public static String database_password = System.getenv("DB_PASSWORD");

    private static Connection connection;

    private static Connection connect() throws Exception {
        if (connection != null) {
            return connection;
        }

        try {
            connection = DriverManager.getConnection(database_url, database_username, database_password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to connect to database");
        }
    }

    public static boolean createTeammate(Teammate teammate) throws Exception {
        try {
            var statement = connect().prepareStatement("insert into teammate values (?, ?);");
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
            var statement = connect().prepareStatement("delete from teammate where name = ? and project = ?;");
            statement.setString(1, teammate.getName());
            statement.setString(2, teammate.getProject());
            statement.execute();
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to delete teammate!");
        }
    }

    public static boolean deleteProject(Project project) throws Exception {
        try {
            var statement = connect().prepareStatement("delete from project where name = ?;");
            statement.setString(1, project.getName());
            statement.execute();
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to delete project!");
        }
    }

}
