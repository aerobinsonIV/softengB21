package edu.wpi.cs3733.g.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import edu.wpi.cs3733.g.entities.Project;
import edu.wpi.cs3733.g.entities.Task;
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
            System.out.println(statement.toString());
            statement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to create project!");
        }
    }

    private static void populateProjectTasksAndTeammates(Project project) throws Exception{
        String projectName = project.getName();
        //Get team members for this project
        PreparedStatement teamPS = connect().prepareStatement("select * from teammate where project=\"" + projectName + "\"");
        ResultSet rs = teamPS.executeQuery();

        System.out.println("Here1, " + projectName);

        //Loop through all teammates in this project
        while(rs.next()){
            project.addTeammate(new Teammate(rs.getString("name"), projectName));
        }

        System.out.println("Here2, " + projectName);

        //Get task assignments for this project
        PreparedStatement taskAssignmentPS = connect().prepareStatement("select * from task_assignments where teammate_project=\"" + projectName + "\"");
        System.out.println("Here3, " + projectName);
        rs = taskAssignmentPS.executeQuery();
        System.out.println("Here4, " + projectName);

        // TODO: There's probably a prettier way to implement this
        ArrayList<Integer> assignedTaskIds = new ArrayList<>();
        ArrayList<String> assignedTaskTeammateNames = new ArrayList<>();

        //Loop through, populate task assignment ArrayLists
        while(rs.next()){
            assignedTaskIds.add(rs.getInt("task"));
            assignedTaskTeammateNames.add(rs.getString("teammate_name"));
        }

        System.out.println("Here5, " + projectName);

        //Get tasks for this project
        PreparedStatement taskPS = connect().prepareStatement("select * from task where project=\"" + projectName + "\"");
        rs = taskPS.executeQuery();

        System.out.println("Here6, " + projectName);
        //Loop through tasks
        while(rs.next()){
            Task tempTask = new Task(rs.getString("name"), rs.getInt("id"));

            for(int i = 0; i < assignedTaskIds.size(); i ++){
                if(assignedTaskIds.get(i) == tempTask.getId()){
                    //We found a task assignment that applies to this task, add this teammate
                    tempTask.assignTeammate(new Teammate(assignedTaskTeammateNames.get(i), projectName));
                }
            }

            project.addTask(tempTask);
        }

        // TODO: Add logic here for dealing with task children
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

            while(rs.next()){
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
