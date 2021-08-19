package com.pablojuice.videoapp.ui.video;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.pablojuice.videoapp.database.AppDatabase;
import com.pablojuice.videoapp.models.VideoItem;

public class VideoViewModel extends ViewModel {

    public VideoItem videoItem;
    public MutableLiveData<Boolean> isFavourite = new MutableLiveData<>();
    private AppDatabase videoDatabase;
    private boolean isVideoPlaying = false;

    public void setVideoItem(VideoItem videoItem) {
        this.videoItem = videoItem;
    }

    public void setupDatabaseConnection(Context context) {
        this.videoDatabase = Room.databaseBuilder(context,
                                                  AppDatabase.class,
                                                  "video-items").allowMainThreadQueries().build();
    }

    public void checkIfVideoIsFavourite() {
        this.isFavourite.setValue(videoDatabase.videoDao().findIfIsFavourite(videoItem.getTitle()));
    }

    public void addOrDeleteFromFavourites() {
        this.isFavourite.setValue(!this.isFavourite.getValue());
        videoItem.setFavourite(this.isFavourite.getValue());
        videoDatabase.videoDao().insert(videoItem);
    }

    public boolean isVideoPlaying() {
        return this.isVideoPlaying;
    }

    public void setVideoPlaying(boolean videoPlaying) {
        this.isVideoPlaying = videoPlaying;
    }
}
