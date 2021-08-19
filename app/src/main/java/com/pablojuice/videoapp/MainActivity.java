package com.pablojuice.videoapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.pablojuice.videoapp.core.BaseFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView).getChildFragmentManager().getFragments().get(0);
            baseFragment.goBack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}