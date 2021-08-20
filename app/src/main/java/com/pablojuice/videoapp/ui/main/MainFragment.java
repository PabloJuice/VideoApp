package com.pablojuice.videoapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.pablojuice.videoapp.R;
import com.pablojuice.videoapp.core.BaseFragment;
import com.pablojuice.videoapp.databinding.FragmentMainBinding;
import com.pablojuice.videoapp.ui.main.adapter.VideoAdapter;
import com.pablojuice.videoapp.utils.Constants;


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
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAdded() && isVisible()) toggleTopBar();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTabBar();
        setupObservers();
        setupRecyclerView();
    }

    private void setupTabBar() {
        for (Constants.MainVideoTabs value : Constants.MainVideoTabs.values()) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(value.name()));
        }
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (String.valueOf(tab.getText()).equals(Constants.MainVideoTabs.MYVIDEOS.name())) {
                    mViewModel.loadMyVideosRequest();
                } else mViewModel.loadVideosRequest(requireActivity());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });

        binding.tabLayout.getTabAt(0).select();
    }

    private void setupObservers() {
        mViewModel.getVideoItems().observe(getViewLifecycleOwner(),
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getText(R.string.app_name));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

}