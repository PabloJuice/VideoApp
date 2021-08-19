package com.pablojuice.videoapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.pablojuice.videoapp.models.VideoItem;

@Database(entities = {VideoItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract VideoDao videoDao();
}
