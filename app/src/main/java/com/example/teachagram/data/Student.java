package com.example.teachagram.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Student {
    @PrimaryKey(autoGenerate = true)
    public Integer index = null;
    public String name;
    public Integer color ;
    public String className;

    public Student(String name, Integer color, String className) {
        this.name = name;
        this.color = color;
        this.className = className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) && Objects.equals(className, student.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, className);
    }
}
