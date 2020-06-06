package com.example.helloworld2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Referenced https://www.youtube.com/watch?v=0cg09tlAAQ0

@Dao
public interface SettingsDao {
    @Query("SELECT * FROM settings")
    LiveData<Settings> getSettings();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Settings settings);
}
