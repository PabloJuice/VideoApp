package com.pablojuice.videoapp.ui.main;

import static com.pablojuice.videoapp.utils.VideoUtil.getJSONFromAsset;
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

    MutableLiveData<List<VideoItem>> videoItems = new MutableLiveData<>();
    private AppDatabase videoDatabase;

    private void setupDatabase(Context context) {
        this.videoDatabase = Room.databaseBuilder(context,
                                                  AppDatabase.class,
                                                  "video-items").allowMainThreadQueries().build();
    }

    public void loadVideosRequest(Activity activity) {
        setupDatabase(activity.getApplicationContext());
        List<VideoItem> items = getVideosFromJson(getJSONFromAsset(activity, "scenes.json"));
        videoItems.postValue(items);
        if (videoDatabase.videoDao().findAll().isEmpty()) {
            items.forEach(videoItem -> this.videoDatabase.videoDao().insert(videoItem));
        }
    }

    public void loadMyVideosRequest() {
        videoItems.postValue(this.videoDatabase.videoDao().findAllFavourites(true));
    }


}