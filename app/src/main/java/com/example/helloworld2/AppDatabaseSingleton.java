package com.example.helloworld2;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Room;
import androidx.room.migration.Migration;
import android.content.Context;
import androidx.annotation.NonNull;

public class AppDatabaseSingleton {

    private static AppDatabase db;

    public static AppDatabase getDatabase(Context context) {
        if(db == null) {
            db = Room.databaseBuilder(context,
                    AppDatabase.class, "sample-database")
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }

        return db;
    }

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE settings ADD COLUMN min_age INTEGER");
            database.execSQL("ALTER TABLE settings ADD COLUMN max_age INTEGER");
            database.execSQL("ALTER TABLE settings ADD COLUMN range INTEGER");
            database.execSQL("ALTER TABLE settings ADD COLUMN privacy INTEGER");
            database.execSQL("ALTER TABLE settings ADD COLUMN reminder INTEGER");
            database.execSQL("ALTER TABLE settings ADD COLUMN reminder_hours INTEGER");
            database.execSQL("ALTER TABLE settings ADD COLUMN reminder_minutes INTEGER");

            database.execSQL("update user\n" +
                    "set last_name=substr(display_name, instr(display_name, ' ')+1, length(display_name)-(instr(display_name, ' ')-1))");

            database.execSQL("update user\n" +
                    "set first_name=substr(display_name, 1, instr(display_name, ' ')-1)");

            database.execSQL("ALTER TABLE settings RENAME TO temp_settings;");

            database.execSQL("CREATE TABLE settings (\n" +
                    " min_age INTEGER NOT NULL PRIMARY KEY,\n" +
                    " max_age INTEGER,\n" +
                    " range INTEGER, \n" +
                    " privacy INTEGER, \n" +
                    " reminder INTEGER, \n"+
                    " reminder_hours INTEGER, \n" +
                    " reminder_minutes INTEGER, \n" +
                    ");");

            database.execSQL("INSERT INTO user \n" +
                    "SELECT\n" +
                    " email, first_name, last_name, photo_url\n" +
                    "FROM\n" +
                    " temp_user;");

            database.execSQL("\n" +
                    "DROP TABLE temp_settings;");
        }
    };
}
