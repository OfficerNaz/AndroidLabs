package com.example.androidlabs;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> elements;
    private MyListAdapter myAdapter;
    private Button button;
    private Switch aswitch;
    private EditText eText;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String delMessage = getString(R.string.delete);
        String sureMessage = getString(R.string.sure);
        String yessir = getString(R.string.yes);
        String no = getString(R.string.no);
        eText = findViewById(R.id.Etext);
        aswitch = findViewById(R.id.urgent);
        button = findViewById(R.id.addButton);
        initAddButton();

        MainActivity.this.elements = new ArrayList<>();
        ListView myList = findViewById(R.id.lView);
        myAdapter = new MyListAdapter(elements);
        myList.setAdapter(myAdapter);

        loadDataFromDatabase();



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

                String item = eText.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.COL_ITEMS, item );
                contentValues.put(DatabaseHelper.COL_URG, aswitch.isChecked());
                long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues );


                elements.add(new Item(item, aswitch.isChecked(), id));
                myAdapter.notifyDataSetChanged();
                eText.setText("");
                aswitch.setChecked(false);

            }
        });


    }

    private void loadDataFromDatabase()
    {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        String [] columns = {DatabaseHelper.COL_ID, DatabaseHelper.COL_ITEMS, DatabaseHelper.COL_URG};
        Cursor cursor = db.query(false, DatabaseHelper.TABLE_NAME, columns, null, null, null,
                null,null,null);

        int itemColumn = cursor.getColumnIndex(DatabaseHelper.COL_ITEMS);
        int itemIdColumn = cursor.getColumnIndex(DatabaseHelper.COL_ID);
        int isUrgentCol = cursor.getColumnIndex(DatabaseHelper.COL_URG);

        while(cursor.moveToNext())
        {
            String item = cursor.getString(itemColumn);
            long id = cursor.getLong(itemIdColumn);
            int urgent = cursor.getInt(isUrgentCol);

            elements.add(new Item(item,urgent ,id));



        }
    }

    private class MyListAdapter extends BaseAdapter {

        private ArrayList<Item> jobby;

        public MyListAdapter(ArrayList<Item> jobby) {
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
            Item jon = (Item) getItem(position);
            tView.setText((jon).getText());
            //tView.setText(((Item)getItem(position)).getText());

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

   
}
