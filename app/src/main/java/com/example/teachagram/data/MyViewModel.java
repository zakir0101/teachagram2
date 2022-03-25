package com.example.teachagram.data;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.teachagram.R;
import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.load.AdditionalMathematics1Sudan;
import com.example.teachagram.load.AddittionalMathematics2Sudan;
import com.example.teachagram.load.CambridgeAdditionalMathematics;

import java.util.ArrayList;
import java.util.List;


public class MyViewModel extends ViewModel {

    private AppDatabase db;

    public MyViewModel() {

    }

    public Standard selStandard;
    public Classes selClass;
    public Unit selUnit;
    public Objective selObjective;
    public Boolean editMood = false;

    public AppDatabase getDb(Fragment fragment) {
        if (db == null) {
            db = Room.databaseBuilder( fragment.getContext(),
                    AppDatabase.class, "database-name").build();
            db.getAllSubjectAsync().observe(fragment, subjectNames -> {
                if (subjectNames.isEmpty()) {
                    db.loadDataAsync();
                }
            });
        }
        return db;
    }


}