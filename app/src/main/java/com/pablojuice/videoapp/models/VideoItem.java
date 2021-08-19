package com.pablojuice.videoapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class VideoItem implements Parcelable {

    public static final Creator<VideoItem> CREATOR = new Creator<VideoItem>() {
        @Override
        public VideoItem createFromParcel(Parcel in) {
            return new VideoItem(in);
        }

        @Override
        public VideoItem[] newArray(int size) {
            return new VideoItem[size];
        }
    };
    private String description;
    private List<String> sources;
    private String subtitle;
    private String thumb;
    private String title;
    private boolean isFavourite;

    protected VideoItem(Parcel in) {
        description = in.readString();
        sources = in.createStringArrayList();
        subtitle = in.readString();
        thumb = in.readString();
        title = in.readString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void normalizeItem() {
        if (sources != null && !sources.isEmpty()) {
            sources.add(0, sources.get(0).replace("http", "https"));
            this.thumb = sources.get(0).substring(0,
                                                  sources.get(0).lastIndexOf("/") + 1) + this.thumb;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeStringArray((String[]) sources.toArray());
        dest.writeString(subtitle);
        dest.writeString(thumb);
        dest.writeString(title);
    }
}
