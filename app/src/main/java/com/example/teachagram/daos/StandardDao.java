package com.example.teachagram.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.teachagram.data.Standard;
import com.google.android.material.circularreveal.CircularRevealHelper;

import java.util.List;

@Dao
public interface StandardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Standard... standards);

    @Delete
    void delete(Standard standard);

    @Query("SELECT * FROM standard")
    List<Standard> getAll();

    @Query("DElETE FROM standard")
    void   deleteAll();

    @Query("SELECT * FROM standard")
    LiveData<List<Standard>> getAllAsync();


    @Query("SELECT * FROM standard WHERE name LIKE :standard LIMIT 1")
    Standard getStandard( String standard );

}