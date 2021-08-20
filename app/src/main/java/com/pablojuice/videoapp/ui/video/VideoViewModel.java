package com.pablojuice.videoapp.ui.video;

import static com.pablojuice.videoapp.utils.Constants.DATABASE_NAME;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.pablojuice.videoapp.database.AppDatabase;
import com.pablojuice.videoapp.models.VideoItem;

public class VideoViewModel extends ViewModel {

    private VideoItem videoItem;
    private MutableLiveData<Boolean> isFavourite = new MutableLiveData<>();
    private AppDatabase videoDatabase;
    private boolean isVideoPlaying = false;

    public void setupDatabaseConnection(Context context) {
        this.videoDatabase = Room.databaseBuilder(context,
                                                  AppDatabase.class,
                                                  DATABASE_NAME).allowMainThreadQueries().build();
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

    public VideoItem getVideoItem() {
        return videoItem;
    }

    public void setVideoItem(VideoItem videoItem) {
        this.videoItem = videoItem;
    }

    public MutableLiveData<Boolean> getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(MutableLiveData<Boolean> isFavourite) {
        this.isFavourite = isFavourite;
    }
}
