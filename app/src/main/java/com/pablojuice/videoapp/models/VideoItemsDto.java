package com.pablojuice.videoapp.models;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoItemsDto {
    private ArrayList<VideoItem> videos = new ArrayList<>();

    public VideoItemsDto() {}

    public VideoItemsDto(List<VideoItem> list) {
        videos.addAll(list);
    }

    public ArrayList<VideoItem> getVideos() {
        return videos;
    }

    public void setVideos(@NonNull ArrayList<VideoItem> videos) {
        this.videos = videos;
    }

    public void setVideos(@NonNull VideoItem... list) {
        videos.addAll(Arrays.asList(list));
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, VideoItem.class);
    }
}
