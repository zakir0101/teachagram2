package com.example.teachagram.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.teachagram.data.Classes;

import java.util.List;

@Dao
public interface ClassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Classes... classes);

    @Delete
    void delete(Classes classes);

    @Query("SELECT * FROM classes")
    List<Classes> getAll();

    @Query("DELETE FROM classes")
    void deleteAll();

    @Query("SELECT * FROM classes")
    LiveData<List<Classes>> getAllAsync();

    @Query("SELECT * FROM classes WHERE name LIKE :className LIMIT 1")
    Classes getClassByName(String className);
}