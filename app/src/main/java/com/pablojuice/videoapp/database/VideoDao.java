package com.pablojuice.videoapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pablojuice.videoapp.models.VideoItem;

import java.util.List;

@Dao
public interface VideoDao {
    @Query("SELECT * FROM videoitem")
    List<VideoItem> findAll();

    @Query("SELECT * FROM videoitem WHERE isFavourite IS :isFavourite")
    List<VideoItem> findAllFavourites(boolean isFavourite);

    @Query("SELECT * FROM videoitem WHERE title like :title LIMIT 1")
    VideoItem findByTitle(String title);

    @Query("SELECT isFavourite FROM videoitem WHERE title like :title LIMIT 1")
    Boolean findIfIsFavourite(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VideoItem... videoItems);

    @Delete
    void delete(VideoItem videoItem);
}
