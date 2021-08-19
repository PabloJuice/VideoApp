package com.pablojuice.videoapp.ui.main.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pablojuice.videoapp.R;
import com.pablojuice.videoapp.models.VideoItem;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<VideoItem> items;

    public VideoAdapter(List<VideoItem> items) {
        if (items != null) {
            this.items = items;
        } else this.items = new ArrayList<>();
    }

    public void addItems(List<VideoItem> items) {
        if (items != null) {
            this.items.addAll(items);
        }
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_video_item,
                                                                                       viewGroup,
                                                                                       false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int i) {
        videoViewHolder.bind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView videoImage;
        TextView videoTitle;
        TextView videoSubtitle;
        Context context;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            videoImage = itemView.findViewById(R.id.ivVideoImage);
            videoTitle = itemView.findViewById(R.id.tvVideoTitle);
            videoSubtitle = itemView.findViewById(R.id.tvVideoSubtitle);
            itemView.setOnClickListener(v -> {
                VideoItem videoItem = items.get(getAdapterPosition());

                Bundle bundle = new Bundle();
                bundle.putParcelable("videoItem", videoItem);

                AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                NavController navController = Navigation.findNavController(activity,
                                                                           R.id.fragmentContainerView);
                navController.navigate(R.id.action_mainFragment_to_videoFragment, bundle);
            });
        }

        public void bind(VideoItem videoItem) {
            videoImage.post(() -> Glide.with(context)
                    .load(videoItem.getThumb()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e,
                                                    Object model,
                                                    Target<Drawable> target,
                                                    boolean isFirstResource) {
                            videoImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
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
                    }).into(videoImage));
            videoTitle.setText(videoItem.getTitle());
            videoSubtitle.setText(videoItem.getSubtitle());
        }
    }
}
