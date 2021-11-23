package edu.wpi.cs3733.g.entities;

import java.util.ArrayList;

public class Project {
    private String _name;
    private boolean _archived = false;
    private ArrayList<Task> _tasks = new ArrayList<>();
    private ArrayList<Teammate> _team = new ArrayList<>();

    public Project(String name){
        _name = name;
    }
    
    public String getName(){
        return _name;   
    }

    public boolean isArchived(){
        return _archived;
    }

    public void archive(){
        _archived = true;
    }

    public void addTeammate(Teammate t){
        _team.add(t);
    }

    public void removeTeammate(Teammate t){
        _team.remove(t);
    }

    public void removeTeammate(String name){
        //Loop through all teammates
        for(Teammate curTeammate : _team){

            //If we find a teammate whose name matches, remove
            if(curTeammate.getName() == name){
                _team.remove(curTeammate);
                return;
            }
        }
    }

    public ArrayList<Teammate> getTeam(){
        return _team;
    }

    public void addTask(Task t){
        _tasks.add(t);
    }

    public void removeTask(Task t){
        _tasks.remove(t);
    }

    public void removeTask(int id){
        _tasks.remove(getTask(id));
    }

    public Task getTask(int id){
        //Loop through all assigned tasks
        for(Task curTask : _tasks){

            //If we find a task whose id matches, return
            if(curTask.getId() == id){
                return curTask;
            }
        }
        return null;
    }

    public ArrayList<Task> getAllTasks(){
        return _tasks;
    }
}
