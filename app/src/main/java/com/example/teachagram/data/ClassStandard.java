package com.example.teachagram.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
@Entity
public class ClassStandard {
    @PrimaryKey(autoGenerate = true)
    public Integer index = null ;
    public String className;
    public String standardName ;

    public ClassStandard(String className, String standardName) {
        this.className = className;
        this.standardName = standardName;
    }
}
