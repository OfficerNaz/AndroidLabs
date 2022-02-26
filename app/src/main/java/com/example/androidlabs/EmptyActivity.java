package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_activity);

        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle b = getIntent().getExtras();
        detailsFragment.setArguments(b);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, detailsFragment)
                .commit();
    }
}