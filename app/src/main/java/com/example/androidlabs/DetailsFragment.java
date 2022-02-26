package com.example.androidlabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class DetailsFragment extends Fragment {

    private TextView name;
    private TextView height;
    private TextView mass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        name = v.findViewById(R.id.Name);
        height = v.findViewById(R.id.Height);
        mass = v.findViewById(R.id.Mass);

        Bundle b = getArguments();
        String nameString = b.getString("NAME");
        String heightString = b.getString("HEIGHT");
        String massString = b.getString("MASS");

        name.setText(nameString);
        height.setText(heightString);
        mass.setText(massString);

        return v;

    }
}