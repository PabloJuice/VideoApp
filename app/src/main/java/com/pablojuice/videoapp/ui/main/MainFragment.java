package com.pablojuice.videoapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.pablojuice.videoapp.R;
import com.pablojuice.videoapp.core.BaseFragment;
import com.pablojuice.videoapp.databinding.FragmentMainBinding;
import com.pablojuice.videoapp.ui.main.adapter.VideoAdapter;


public class MainFragment extends BaseFragment<FragmentMainBinding> {

    private MainViewModel mViewModel;
    private VideoAdapter videoAdapter;

    @Override
    protected FragmentMainBinding bindLayout(LayoutInflater inflater, ViewGroup container) {
        return FragmentMainBinding.inflate(inflater, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.loadVideosRequest(requireActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAdded() && isVisible()) toggleTopBar();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObservers();
        setupRecyclerView();
        navController = Navigation.findNavController(binding.getRoot());
    }

    private void setupObservers() {
        mViewModel.videoItems.observe(getViewLifecycleOwner(),
                                      videoItems -> {
                                          videoAdapter = new VideoAdapter(videoItems);
                                          binding.videoRecyclerView.setAdapter(videoAdapter);
                                      });
    }

    private void setupRecyclerView() {
        binding.videoRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
    }

    private void toggleTopBar() {
        getActivity().setTheme(R.style.Theme_VideoApp_ActionBar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("VideoApp");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

}