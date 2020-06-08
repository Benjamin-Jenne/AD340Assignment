package com.example.helloworld2;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    public LiveData<Settings> getSettingsById(Context context, int id){
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.settingsDao().getSettingsById(id);
    }
    public void insert(Context context, Settings settings){
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        db.getTransactionExecutor().execute(() -> {
            db.settingsDao().insert(settings);
        });
    }
}
