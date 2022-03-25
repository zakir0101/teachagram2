package com.example.teachagram.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
@Entity
public class Classes {
    @PrimaryKey(autoGenerate = true)
    public Integer index = null ;
    public String name;
    public Integer color ;

    public Classes( String name ,Integer color ) {
        this.name = name;
        this.color = color;
    }
}
