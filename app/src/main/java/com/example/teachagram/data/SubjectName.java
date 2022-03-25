package com.example.teachagram.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SubjectName {
    @PrimaryKey(autoGenerate = true)
    public  Integer index =null ;
    public  String name ;
    public  Integer res ;

    public SubjectName( String name, Integer res) {
        this.name = name;
        this.res = res;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
