package com.pablojuice.videoapp.ui.video;

import static com.pablojuice.videoapp.utils.Constants.ITEM_KEY;
import static com.pablojuice.videoapp.utils.VideoUtil.loadImageFromVideoItem;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.Navigation;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.pablojuice.videoapp.R;
import com.pablojuice.videoapp.core.BaseFragment;
import com.pablojuice.videoapp.databinding.FragmentItemMainBinding;

public class VideoFragment extends BaseFragment<FragmentItemMainBinding> {

    private VideoViewModel mViewModel;
    private SimpleExoPlayer exoPlayer;

    @Override
    protected FragmentItemMainBinding bindLayout(LayoutInflater inflater, ViewGroup container) {
        return FragmentItemMainBinding.inflate(inflater, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setupViewModel();
        setupTopActionbar(mViewModel.isVideoPlaying());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setupVideoPlayer();
        } else {
            fetchVideoInfo();
            setupOnClickListeners();
        }
        navController = Navigation.findNavController(binding.getRoot());
        setupNativeBackButton();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (binding.videoPlayer.getPlayer() != null) {
            binding.videoPlayer.getPlayer().pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding.videoPlayer.getPlayer() != null) {
            binding.videoPlayer.getPlayer().setPlayWhenReady(true);
        }
    }

    private void setupViewModel() {
        mViewModel = getViewModel(VideoViewModel.class);
        mViewModel.setupDatabaseConnection(requireContext());
        mViewModel.setVideoItem(getArguments().getParcelable(ITEM_KEY));
        mViewModel.checkIfVideoIsFavourite();
    }

    private void fetchVideoInfo() {
        loadImageFromVideoItem(mViewModel.getVideoItem(), binding.ivMainPic, requireContext());
        binding.tvTitle.setText(mViewModel.getVideoItem().getTitle());
        binding.tvSubtitle.setText(mViewModel.getVideoItem().getSubtitle());
        binding.tvDescription.setText(mViewModel.getVideoItem().getDescription());
        mViewModel.getIsFavourite().observe(getViewLifecycleOwner(), this::toggleLikeBtn);
    }

    private void setupOnClickListeners() {
        binding.ivLike.setOnClickListener(v -> mViewModel.addOrDeleteFromFavourites());
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

    private void setupVideoPlayer() {
        binding.mainScrollView.setVisibility(View.GONE);
        binding.videoPlayer.setVisibility(View.VISIBLE);
        toggleFullscreen();
        exoPlayer = new SimpleExoPlayer.Builder(requireContext()).build();
        MediaSource mediaSource = new ProgressiveMediaSource
                .Factory(new DefaultDataSourceFactory(requireContext(), ITEM_KEY))
                .createMediaSource(MediaItem.fromUri(Uri.parse(mViewModel.getVideoItem().getSource())));
        binding.videoPlayer.setPlayer(exoPlayer);
        exoPlayer.setMediaSource(mediaSource);
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.prepare();

        getView().findViewById(R.id.exo_back).setOnClickListener(v -> goBack());
    }

    private void stopVideo() {
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.release();
        mViewModel.setVideoPlaying(false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void setupTopActionbar(boolean status) {
        if (!status) {
            getActivity().setTheme(R.style.Theme_VideoApp_ActionBar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mViewModel.getVideoItem().getTitle());
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getActivity().setTheme(R.style.Theme_VideoApp_NoActionBar);
            getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                               WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void toggleFullscreen() {
        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> decorView.setSystemUiVisibility(
                uiOptions));
    }

    private void setupNativeBackButton() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goBack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                                                                   callback);
    }

    @Override
    public void goBack() {
        if (mViewModel.isVideoPlaying()) stopVideo();
        else super.goBack();
    }
}
