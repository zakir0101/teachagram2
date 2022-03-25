package com.example.teachagram.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.teachagram.data.ClassStandard;

import java.util.List;

@Dao
public interface ClassStandardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ClassStandard... classStandards);

    @Delete
    void delete(ClassStandard classStandard);

    @Query("SELECT * FROM classstandard")
    List<ClassStandard> getAll();

    @Query("DELETE FROM classstandard")
    void deleteAll();

    @Query("SELECT * FROM classstandard WHERE className LIKE :name ")
    List<ClassStandard> getByClassName(String name);

    @Query("SELECT * FROM classstandard WHERE standardName LIKE :name ")
    List<ClassStandard> getByStandardName(String name);

    @Query("SELECT * FROM classstandard WHERE standardName LIKE :standardName AND " +
            " className LIKE :classs")
    List<ClassStandard> getClassStandard(String standardName, String classs);


    @Query("DELETE FROM classstandard WHERE className LIKE :name ")
    void deleteByClassName(String name);

    @Query("DELETE FROM classstandard WHERE className LIKE :cName AND " +
            "standardName LIKE :sName")
    void deleteClassStandard(String cName , String sName);

    @Query("DELETE FROM classstandard WHERE standardName LIKE :name ")
    void deleteByStandardName(String name);
}