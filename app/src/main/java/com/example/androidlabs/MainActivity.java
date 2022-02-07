package com.example.androidlabs;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TodoItem> elements;
    private MyListAdapter myAdapter;
    private Button button;
    private Switch aswitch;
    private EditText eText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String delMessage = getString(R.string.delete);
        String sureMessage = getString(R.string.sure);
        String yessir = getString(R.string.yes);
        String no = getString(R.string.no);

        setContentView(R.layout.activity_main);
        MainActivity.this.elements = new ArrayList<>();
        ListView myList = findViewById(R.id.lView);
        myAdapter = new MyListAdapter(elements);
        myList.setAdapter(myAdapter);
        eText = findViewById(R.id.Etext);
        aswitch = findViewById(R.id.urgent);
        button = findViewById(R.id.addButton);
        initAddButton();


        myList.setOnItemLongClickListener((p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(delMessage)


                    .setMessage(sureMessage + " " + pos)


                    .setPositiveButton(yessir, (click, arg) -> {
                        elements.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })

                    .setNegativeButton(no, (click, arg) -> {
                    })


                    .setView(getLayoutInflater().inflate(R.layout.activity_todo, null))

                    .create().show();
            return true;
        });
    }

    private void initAddButton() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                elements.add(new TodoItem(eText.getText().toString(), aswitch.isChecked()));
                myAdapter.notifyDataSetChanged();
                eText.setText("");
                aswitch.setChecked(false);
            }
        });


    }

    private class MyListAdapter extends BaseAdapter {

        private ArrayList<TodoItem> jobby;

        public MyListAdapter(ArrayList<TodoItem> jobby) {
            this.jobby = jobby;

        }

        public int getCount() {
            return jobby.size();
        }

        public Object getItem(int position) {
            return jobby.get(position);

        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();


            if (newView == null) {
                newView = inflater.inflate(R.layout.activity_todo, parent, false);

            }

            TextView tView = newView.findViewById(R.id.textGoesHere);
            TodoItem jon = (TodoItem) getItem(position);
            tView.setText((jon).getText());
            //tView.setText(((TodoItem)getItem(position)).getText());

            //set what the text should be for this row:

            if (elements.get(position).isUrgent()) {
                newView.setBackgroundColor(Color.RED);
                tView.setTextColor(Color.WHITE);
            } else {
                newView.setBackgroundColor(Color.WHITE);
                tView.setTextColor(Color.BLACK);
            }
            //return it to be put in the table
            return newView;
        }
    }

    public class TodoItem {

        private String text;
        private boolean isUrgent;

        public TodoItem(String text, boolean isUrgent) {
            this.text = text;
            this.isUrgent = isUrgent;


        }

        public String getText() {
            return text;
        }

        public boolean isUrgent() {
            return isUrgent;
        }
    }
}
