package com.pablojuice.videoapp.ui.main;

import static com.pablojuice.videoapp.utils.VideoUtil.getJSONFromAsset;
import static com.pablojuice.videoapp.utils.VideoUtil.getVideosFromJson;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pablojuice.videoapp.models.VideoItem;

import java.util.List;

public class MainViewModel extends ViewModel {

    MutableLiveData<List<VideoItem>> videoItems = new MutableLiveData<>();

    public void loadVideosRequest(Activity activity){
        videoItems.postValue(getVideosFromJson(getJSONFromAsset(activity, "scenes.json")));
    }



}