package com.example.teachagram.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Objective {
    @PrimaryKey(autoGenerate = true)
    public Integer index = null ;
    public String text;
    public String unitName ;
    public String standardName;

    public Objective(String text, String unitName, String standardName) {
        this.text = text;
        this.unitName = unitName;
        this.standardName = standardName;
    }
}
