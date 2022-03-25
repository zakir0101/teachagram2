package com.example.teachagram.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.teachagram.data.Progress;

import java.util.List;

@Dao
public interface ProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Progress... progresses);

    @Delete
    void delete(Progress progress);

    @Query("SELECT * FROM progress")
    List<Progress> getAll();

    @Query("DELETE FROM progress")
    void deleteAll();

    @Query("SELECT * FROM progress WHERE className  LIKE :classs " +
            " AND objectiveId = :objecId LIMIT 1")
    Progress getProgresses(String classs, Integer objecId );

    @Query("DELETE FROM progress WHERE className  LIKE :classs " +
            " AND objectiveId = :objecId ")
    void deleteProgresses(String classs, Integer objecId );

    @Query("SELECT * FROM progress WHERE className  LIKE :classs " )
    List<Progress> getProgresses(String classs );

    @Query("DELETE FROM progress WHERE className  LIKE :classs " )
    void deleteByClass(String classs );



}