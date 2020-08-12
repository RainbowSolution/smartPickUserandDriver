package com.alpha.smartpickuser.droplocationAddFragmentPkg.Roomdatabase;



import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.alpha.smartpickuser.droplocationAddFragmentPkg.PlaceModel;

@Database(entities = {PlaceModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabaseMain extends RoomDatabase {
    public abstract TaskDao taskDao();
}