package com.example.teachagram.data;

import com.google.gson.Gson;

import java.util.List;

public class Backup {
    public List<Classes> classList ;
    public List<Standard> standardList;
    public List<ClassStandard> classStandardList;
    public List<Student> studentList;
    public List<Progress> progressList;
    public List<Unit> unitList;
    public List<Objective> objectiveList;

    public Backup() {
    }

    public String toJson(){
        return new Gson().toJson(this);
    }

    public static Backup fromJson(String backup) {
        Backup backup1 = new Gson().fromJson(backup, Backup.class);
        return backup1;
    }
}
