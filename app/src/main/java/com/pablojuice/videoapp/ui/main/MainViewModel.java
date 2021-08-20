package com.pablojuice.videoapp.ui.main;

import static com.pablojuice.videoapp.utils.Constants.DATABASE_NAME;
import static com.pablojuice.videoapp.utils.VideoUtil.getJsonFromAsset;
import static com.pablojuice.videoapp.utils.VideoUtil.getVideosFromJson;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.pablojuice.videoapp.database.AppDatabase;
import com.pablojuice.videoapp.models.VideoItem;

import java.util.List;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<VideoItem>> videoItems = new MutableLiveData<>();
    private AppDatabase videoDatabase;

    private final String FILE_NAME = "scenes.json";

    private void setupDatabase(Context context) {
        this.videoDatabase = Room.databaseBuilder(context,
                                                  AppDatabase.class,
                                                  DATABASE_NAME).allowMainThreadQueries().build();
    }

    public void loadVideosRequest(Activity activity) {
        setupDatabase(activity.getApplicationContext());
        List<VideoItem> items = getVideosFromJson(getJsonFromAsset(activity, FILE_NAME));
        videoItems.postValue(items);
        if (videoDatabase.videoDao().findAll().isEmpty()) {
            items.forEach(videoItem -> this.videoDatabase.videoDao().insert(videoItem));
        }
    }

    public void loadMyVideosRequest() {
        videoItems.postValue(this.videoDatabase.videoDao().findAllFavourites(true));
    }

    public MutableLiveData<List<VideoItem>> getVideoItems() {
        return videoItems;
    }

    public void setVideoItems(MutableLiveData<List<VideoItem>> videoItems) {
        this.videoItems = videoItems;
    }
}