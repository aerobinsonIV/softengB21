package edu.wpi.cs3733.g.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Task {
    private String _name;
    private int _id;
    private TaskMarkValue _mark = TaskMarkValue.IN_PROGRESS;
    private ArrayList<Teammate> _assignedTeammates = new ArrayList<Teammate>();
    private ArrayList<Task> _subtasks = new ArrayList<Task>();

    public Task(String name, int id){
        _name = name;
        _id = id;
    }

    public String getName(){
        return _name;
    }

    public int getId(){
        return _id;
    }

    public void setMark(TaskMarkValue newMark){
        _mark = newMark;    
    }

    public TaskMarkValue getMark(){
        return _mark;
    }

    public Collection<Teammate> getAssignedTeammates(){
        return _assignedTeammates;
    }

    public void assignTeammate(Teammate t){
        _assignedTeammates.add(t);
    }

    //Remove methods are void return type because it's not the entities responsibility to determine if these fail.
    //As much logic as possible should be in controller.

    public void removeTeammate(Teammate t){
        _assignedTeammates.remove(t);
    }

    public void removeTeammate(String name){

        //Loop through all assigned teammates
        for(Teammate curTeammate : _assignedTeammates){

            //If we find a teammate whose name matches the arg, remove
            if(curTeammate.getName() == name){
                _assignedTeammates.remove(curTeammate);
                return;
            }
        }
    }

    public boolean isLeafTask(){
        return _subtasks.size() == 0;
    }

    public void addSubtask(Task subtask){
        _subtasks.add(subtask);
    }

    public ArrayList<Task> getSubtasks(){
        return _subtasks;
    }
}
