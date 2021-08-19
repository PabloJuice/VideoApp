package com.pablojuice.videoapp.ui.video;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pablojuice.videoapp.R;
import com.pablojuice.videoapp.core.BaseFragment;
import com.pablojuice.videoapp.databinding.FragmentItemMainBinding;
import com.pablojuice.videoapp.models.VideoItem;

public class VideoFragment extends BaseFragment<FragmentItemMainBinding> {

    private VideoViewModel mViewModel;
    private VideoItem videoItem;

    @Override
    protected FragmentItemMainBinding bindLayout(LayoutInflater inflater, ViewGroup container) {
        return FragmentItemMainBinding.inflate(inflater, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mViewModel = getViewModel(VideoViewModel.class);
        videoItem = getArguments().getParcelable("videoItem");
        mViewModel.checkIfVideoIsFavourite();
        setupTopActionbar(mViewModel.isVideoPlaying());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OnBackPressedCallback callback;
        if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            playVideo();
            callback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    stopVideo();
                }
            };
        } else {
            fetchVideoInfo();
            setupOnClickListeners();
            callback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    goBack();
                }
            };
        }
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                                                                   callback);
        navController = Navigation.findNavController(binding.getRoot());
    }

    private void fetchVideoInfo() {
        loadImage();
        binding.tvTitle.setText(videoItem.getTitle());
        binding.tvSubtitle.setText(videoItem.getSubtitle());
        binding.tvDescription.setText(videoItem.getDescription());
        toggleLikeBtn(mViewModel.isFavourite.getValue());
    }

    private void loadImage() {
        binding.ivMainPic.post(() -> Glide.with(requireContext())
                .load(videoItem.getThumb()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model,
                                                Target<Drawable> target,
                                                boolean isFirstResource) {
                        binding.ivMainPic.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
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
                }).into(binding.ivMainPic));
    }

    private void setupOnClickListeners() {
        binding.ivLike.setOnClickListener(v -> {
            mViewModel.addOrDeleteFromFavourites();
            toggleLikeBtn(mViewModel.isFavourite.getValue());
        });
        binding.btnPlay.setOnClickListener(v -> {
            binding.mainScrollView.setVisibility(View.GONE);
            mViewModel.setVideoPlaying(true);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });
    }


    private void toggleLikeBtn(boolean status) {
        binding.ivLike.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                                                                    status ? R.drawable.favourite_icon : R.drawable.not_favourite_icon,
                                                                    null));
    }

    private void playVideo() {
        binding.mainScrollView.setVisibility(View.GONE);
        binding.videoView.setVisibility(View.VISIBLE);
        toggleFullscreen();
        MediaController mediaController = new MediaController(requireContext());
        mediaController.setAnchorView(binding.videoView);
        binding.videoView.setMediaController(mediaController);
        binding.videoView.setVideoURI(Uri.parse(videoItem.getSources().get(0)));

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        binding.videoView.setLayoutParams(new RelativeLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels));



        binding.videoView.start();
    }

    private void stopVideo() {
        binding.videoView.stopPlayback();
        mViewModel.setVideoPlaying(false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void setupTopActionbar(boolean status) {
        if (!status) {
            getActivity().setTheme(R.style.Theme_VideoApp_ActionBar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getActivity().setTheme(R.style.Theme_VideoApp_NoActionBar);
            getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void toggleFullscreen() {
        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if (visibility == 0) {
                decorView.setSystemUiVisibility(uiOptions);
            }
        });
    }
}
