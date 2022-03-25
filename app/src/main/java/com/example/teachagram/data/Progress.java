package com.example.teachagram.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Progress {
    @PrimaryKey(autoGenerate = true)
    public Integer index = null;
    public String className;
    public Integer objectiveId;
    public Boolean done = false;


    public Progress(String className, Integer objectiveId) {
        this.className = className;
        this.objectiveId = objectiveId;
    }
}
