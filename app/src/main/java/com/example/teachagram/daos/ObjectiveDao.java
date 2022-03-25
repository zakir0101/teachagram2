package com.example.teachagram.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.teachagram.data.Objective;

import java.util.List;

@Dao
public interface ObjectiveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Objective... objectives);

    @Delete
    void delete(Objective Objective);

    @Query("SELECT * FROM objective")
    List<Objective> getAll();

    @Query("DELETE FROM objective")
    void deleteAll();

    @Query("Delete  FROM objective WHERE standardName LIKE :standard ")
    void deleteByStandard( String standard);

    @Query("Delete  FROM objective WHERE standardName LIKE :standard AND" +
            " unitName LIKE :unit ")
    void deleteByUnit( String standard , String unit);

    @Query("SElECT *  FROM objective WHERE standardName LIKE :standard ")
    List<Objective> getObjectives(String standard);

    @Query("SELECT * FROM objective WHERE unitName LIKE :unit " +
            "AND standardName LIKE :standard ")
    List<Objective> getObjectives(String unit , String standard);

    @Query("SELECT * FROM objective WHERE unitName LIKE :unit " +
            "AND standardName LIKE :standard ")
    LiveData<List<Objective>> getObjectivesAsync(String unit, String standard);

    @Query("SELECT * FROM objective WHERE unitName LIKE :unit " +
            "AND standardName LIKE :standard AND " +
            " text LIKE :objectiveText LIMIT 1")
    Objective getObjective(String objectiveText , String unit , String standard );

}