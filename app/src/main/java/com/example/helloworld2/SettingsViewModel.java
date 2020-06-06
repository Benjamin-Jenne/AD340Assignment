package com.example.helloworld2;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    public LiveData<Settings> getSettings(Context context){
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.settingsDao().getSettings();
    }
    public void insert(Context context, Settings settings){
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.settingsDao().insert(settings);
    }
}
