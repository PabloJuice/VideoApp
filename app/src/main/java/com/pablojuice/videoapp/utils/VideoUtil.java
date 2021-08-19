package com.pablojuice.videoapp.utils;

import static com.pablojuice.videoapp.utils.Constants.STANDARD_CHARSET;

import android.app.Activity;

import com.google.gson.Gson;
import com.pablojuice.videoapp.models.VideoItem;
import com.pablojuice.videoapp.models.VideoItemsDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class VideoUtil {
    public static List<VideoItem> getVideosFromJson(String res) {
        List<VideoItem> videoItems = new ArrayList<>(new Gson().fromJson(res,
                                                                         VideoItemsDto.class).getVideos());
        videoItems.forEach(VideoItem::normalizeItem);
        return videoItems;
    }

    public static String getJSONFromAsset(Activity activity, String filename) {
        String json;
        try {
            InputStream is = activity.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, STANDARD_CHARSET);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
