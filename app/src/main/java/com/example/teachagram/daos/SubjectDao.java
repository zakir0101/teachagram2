package com.example.teachagram.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.teachagram.data.SubjectName;

import java.util.List;

@Dao
public interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(SubjectName... subjectNames);

    @Delete
    void delete(SubjectName subjectName);

    @Query("SELECT * FROM subjectname")
    List<SubjectName> getAll();

 @Query("SELECT * FROM subjectname")
    LiveData<List<SubjectName>> getAllAsync();


    @Query("SELECT * FROM subjectname WHERE name LIKE :subjectName LIMIT 1 ")
    SubjectName getSubject( String subjectName );

    @Query("SELECT * FROM subjectname WHERE name LIKE :subjectName LIMIT 1 ")
    LiveData<SubjectName> getSubjectAsync(String subjectName );

}