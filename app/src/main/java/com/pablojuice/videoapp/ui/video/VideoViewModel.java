package com.pablojuice.videoapp.ui.video;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VideoViewModel extends ViewModel {
    public MutableLiveData<Boolean> isFavourite = new MutableLiveData<>();

    private boolean isVideoPlaying = false;

    public void checkIfVideoIsFavourite(){
        isFavourite.setValue(false);
    }

    public void addOrDeleteFromFavourites(){
        if (isFavourite.getValue()){
            isFavourite.setValue(false);
        }else isFavourite.setValue(true);
    }

    public boolean isVideoPlaying() {
        return isVideoPlaying;
    }

    public void setVideoPlaying(boolean videoPlaying) {
        isVideoPlaying = videoPlaying;
    }
}
