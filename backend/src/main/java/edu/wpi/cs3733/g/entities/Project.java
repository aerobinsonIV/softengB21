package edu.wpi.cs3733.g.entities;

public class Project {
    private String _name;
    private boolean _archived = false;

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
}
