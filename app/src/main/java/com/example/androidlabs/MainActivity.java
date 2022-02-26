package com.example.androidlabs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView fragOneListView;
    private List<StarWarsActorModel> actorsList = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragOneListView = findViewById(R.id.fragOneListView);

        adapter = new MyAdapter();
        fragOneListView.setAdapter(adapter);

        StarWarsAsyncTask swat = new StarWarsAsyncTask();
        swat.execute();

        fragOneListView.setOnItemClickListener((adapterView, view, position, l) -> {
            FrameLayout fragmentContainer = findViewById(R.id.fragmentContainer);

            StarWarsActorModel swam = actorsList.get(position);

            Bundle b = new Bundle();
            b.putString("NAME", swam.getName());
            b.putString("HEIGHT", swam.getHeight());
            b.putString("MASS", swam.getMass());

            if (fragmentContainer == null) { // It is a phone
                Intent intent = new Intent(this, EmptyActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            } else { // It is a tablet
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(b);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, detailsFragment)
                        .commit();
            }
        });
    }

    private class StarWarsAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL("https://swapi.dev/api/people/?format=json");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();


                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonArrayResults = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArrayResults.length(); i++) {
                    JSONObject jsonObjectResult = jsonArrayResults.getJSONObject(i);

                    String name = jsonObjectResult.getString("name");
                    String mass = jsonObjectResult.getString("mass");
                    String height = jsonObjectResult.getString("height");

                    StarWarsActorModel swam = new StarWarsActorModel(name, height, mass);
                    actorsList.add(swam);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            adapter.notifyDataSetChanged();
        }
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return actorsList.size();
        }

        @Override
        public StarWarsActorModel getItem(int position) {
            return actorsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            View v = getLayoutInflater().inflate(R.layout.row_actor, viewGroup, false);

            TextView name = v.findViewById(R.id.actorName);

            StarWarsActorModel swam = getItem(position);

            name.setText(swam.getName());

            return v;
        }
    }
}
