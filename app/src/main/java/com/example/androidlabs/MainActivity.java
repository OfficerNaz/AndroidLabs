package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private CheckBox checkBoxVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);

        CheckBox cb = findViewById(R.id.check);

        Button button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,getResources().getString(R.string.toast_message), Toast.LENGTH_SHORT).show();
            }
        });

        cb.setOnCheckedChangeListener( (compoundButton, b)  -> {

            Snackbar snackbar = Snackbar.make(cb, this.getResources().getString(R.string.onCheck) + " " + b, Snackbar.LENGTH_LONG);
                    snackbar.setAction("Undo", click-> compoundButton.setChecked( !b ))
                            .show();
        });


    }

    }

