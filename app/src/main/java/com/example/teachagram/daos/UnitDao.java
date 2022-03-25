package com.example.teachagram.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.teachagram.data.Unit;

import java.util.List;

@Dao
public interface UnitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Unit... units);

    @Delete
    void delete(Unit unit);

    @Query("SELECT * FROM unit")
    List<Unit> getAll();

    @Query("DELETE FROM unit")
    void deleteAll();


    @Query("DELETE FROM unit WHERE standardName LIKE :standard ")
    void deleteByStandard( String standard );

    @Query("SELECT * FROM unit WHERE standardName LIKE :standard ")
    List<Unit> getUnits( String standard );

    @Query("SELECT * FROM unit WHERE standardName LIKE :standard ")
    LiveData<List<Unit>> getUnitsAsync(String standard );

    @Query("SELECT * FROM unit WHERE standardName LIKE :standard AND" +
            " name LIKE :unit LIMIT 1")
    Unit getUnit( String unit ,String standard );

}