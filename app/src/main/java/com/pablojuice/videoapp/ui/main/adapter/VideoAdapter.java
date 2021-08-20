package com.pablojuice.videoapp.ui.main.adapter;

import static com.pablojuice.videoapp.utils.Constants.ITEM_KEY;
import static com.pablojuice.videoapp.utils.VideoUtil.loadImageFromVideoItem;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.pablojuice.videoapp.R;
import com.pablojuice.videoapp.models.VideoItem;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<VideoItem> items;

    public VideoAdapter(List<VideoItem> items) {
        if (items != null) {
            this.items = items;
        } else this.items = new ArrayList<>();
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

        private ImageView videoImage;
        private TextView videoTitle;
        private TextView videoSubtitle;
        private Context context;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            videoImage = itemView.findViewById(R.id.ivVideoImage);
            videoTitle = itemView.findViewById(R.id.tvVideoTitle);
            videoSubtitle = itemView.findViewById(R.id.tvVideoSubtitle);////TODO
            itemView.setOnClickListener(v -> {
                VideoItem videoItem = items.get(getBindingAdapterPosition());
                Bundle bundle = new Bundle();
                bundle.putParcelable(ITEM_KEY, videoItem);
                AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                NavController navController = Navigation.findNavController(activity,
                                                                           R.id.fragmentContainerView);
                navController.navigate(R.id.action_mainFragment_to_videoFragment, bundle);
            });
        }

        public void bind(VideoItem videoItem) {
            loadImageFromVideoItem(videoItem, videoImage, context);
            videoTitle.setText(videoItem.getTitle());
            videoSubtitle.setText(videoItem.getSubtitle());
        }
    }
}
