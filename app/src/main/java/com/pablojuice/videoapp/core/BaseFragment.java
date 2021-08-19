package com.pablojuice.videoapp.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment implements Navigator {

    protected B binding;
    protected NavController navController;

    protected abstract B bindLayout(LayoutInflater inflater, ViewGroup container);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = bindLayout(inflater, container);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            binding.unbind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected <VM extends ViewModel> VM getViewModel(Class<VM> classType) {
        return new ViewModelProvider(requireActivity()).get(classType);
    }

    public void goBack() {
        navController.popBackStack();
    }
}
