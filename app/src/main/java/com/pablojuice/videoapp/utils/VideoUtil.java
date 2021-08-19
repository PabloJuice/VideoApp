package com.pablojuice.videoapp.utils;

import static com.pablojuice.videoapp.utils.Constants.STANDARD_CHARSET;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.pablojuice.videoapp.R;
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

    public static String getJsonFromAsset(Activity activity, String filename) {
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

    public static void loadImageFromVideoItem(VideoItem videoItem,
                                              ImageView imageView,
                                              Context context) {
        imageView.post(() -> Glide.with(context)
                .load(videoItem.getThumb()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model,
                                                Target<Drawable> target,
                                                boolean isFirstResource) {
                        imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                                                                               R.drawable.sync_error_icon,
                                                                               null));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource,
                                                   Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource,
                                                   boolean isFirstResource) {
                        return false;
                    }
                }).into(imageView));
    }
}
