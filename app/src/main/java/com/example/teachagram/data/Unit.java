package com.example.teachagram.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
@Entity
public class Unit {
    @PrimaryKey
    public Integer index = null ;
    public String name ;
    public String standardName;

    public Unit( String name, String standardName) {
        this.name = name;
        this.standardName = standardName;
    }
}
