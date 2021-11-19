package edu.wpi.cs3733.g.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Task {
    private String _name;
    private int _id;
    private TaskMarkValue _mark = TaskMarkValue.IN_PROGRESS;
    private ArrayList<Teammate> _assignedTeammates = new ArrayList<Teammate>();

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

    public void removeTeammate(Teammate t){
        _assignedTeammates.remove(t);
    }
}
