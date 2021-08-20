package com.pablojuice.videoapp.ui.main.adapter;

import static com.pablojuice.videoapp.utils.VideoUtil.loadImageFromVideoItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablojuice.videoapp.R;
import com.pablojuice.videoapp.models.VideoItem;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<VideoItem> items;
    private final OnClickListener onClickListener;

    public VideoAdapter(@NonNull OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public List<VideoItem> getItems() {
        return items;
    }

    public void setItems(List<VideoItem> items) {
        this.items = items;
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

    public interface OnClickListener {
        void onItemClicked(VideoItem videoItem);
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
            videoSubtitle = itemView.findViewById(R.id.tvVideoSubtitle);
            itemView.setOnClickListener(v -> onClickListener.onItemClicked(items.get(
                    getBindingAdapterPosition())));
        }

        public void bind(VideoItem videoItem) {
            loadImageFromVideoItem(videoItem, videoImage, context);
            videoTitle.setText(videoItem.getTitle());
            videoSubtitle.setText(videoItem.getSubtitle());
        }
    }
}
