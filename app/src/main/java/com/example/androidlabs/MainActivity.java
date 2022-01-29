package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText EDid;
    private Button MyBut;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    private String Text;

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
        Toast.makeText(this, "On Pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EDid = findViewById(R.id.EDid);
        MyBut = findViewById(R.id.MyBut);

        MyBut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String text = "Welcome " + EDid.getText().toString() + "!";

                Intent intent = new Intent(MainActivity.this,NameActivity.class);
                intent.putExtra("Text", text);
                startActivityForResult(intent, 1);
            }
        });

        loadData();
        updateViews();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, EDid.getText().toString());

        editor.apply();
    }

    public void loadData(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Text = sharedPreferences.getString(TEXT, "");
    }

    public void updateViews(){
        EDid.setText(Text);

    }
}