package com.pablojuice.videoapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pablojuice.videoapp.core.Navigator;

import java.util.Objects;

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
            Navigator navigator = (Navigator) Objects.requireNonNull(
                    getSupportFragmentManager().findFragmentById(
                            R.id.fragmentContainerView)).getChildFragmentManager().getFragments().get(0);
            navigator.goBack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}