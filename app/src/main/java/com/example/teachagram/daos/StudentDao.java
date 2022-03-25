package com.example.teachagram.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.teachagram.data.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Student... students);

    @Delete
    void delete(Student sTudent);

    @Query("SELECT * FROM student")
    List<Student> getAll();

    @Query("DELETE FROM student")
    void deleteAll();


    @Query("SELECT * FROM student WHERE className LIKE :classs ")
    List<Student> getStudents( String classs );

    @Query("SELECT * FROM student WHERE className LIKE :classs ")
    LiveData<List<Student>> getStudentsAsync(String classs );

    @Query("SELECT * FROM student WHERE className LIKE :classs AND" +
            " name LIKE :student LIMIT 1")
    Student getStudents( String student, String classs );

    @Query("DELETE FROM student WHERE className LIKE :classs ")
    void deleteByClass( String classs );



}