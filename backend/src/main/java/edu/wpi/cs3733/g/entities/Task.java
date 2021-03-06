package edu.wpi.cs3733.g.entities;

import java.util.ArrayList;

public class Task {
    private String _name;
    private int _id;
    private TaskMarkValue _mark = TaskMarkValue.IN_PROGRESS;
    private ArrayList<Teammate> _assignedTeammates = new ArrayList<Teammate>();
    private ArrayList<Integer> _subtasks = new ArrayList<Integer>();

    public Task(String name, int id){
        _name = name;
        _id = id;
    }

    public Task(String name) {
        _name = name;
    }

    public Task() {}

    public String getName(){
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public int getId(){
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setMarkStatus(TaskMarkValue newMark){
        _mark = newMark;    
    }

    public TaskMarkValue getMarkStatus(){
        return _mark;
    }

    public void setTeammates(ArrayList<Teammate> teamamtes) {
        _assignedTeammates = teamamtes;
    }

    public ArrayList<Teammate> getTeammates(){
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
        _subtasks.add(subtask.getId());
    }

    public ArrayList<Integer> getSubtasks(){
        return _subtasks;
    }
}
