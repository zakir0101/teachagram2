package com.example.teachagram.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

@Entity
public class Standard {
    @PrimaryKey(autoGenerate = true)
    public Integer index = null;
    public String name ;
    public Integer color ;
    public String subjectName;

    public Standard(String name, Integer color, String subjectName) {
        this.name = name;
        this.color = color;
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Standard standard = (Standard) o;
        return Objects.equals(index, standard.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    public String  toJson() {
        return new Gson().toJson(this);
    }
}
