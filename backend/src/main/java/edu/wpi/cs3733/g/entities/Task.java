package edu.wpi.cs3733.g.entities;

public class Task {
    private String _name;
    private int _id;
    private TaskMarkValue _mark = TaskMarkValue.IN_PROGRESS;

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
}
