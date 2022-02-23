package com.example.androidlabs;



import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private ImageView trat;
    private ProgressBar progress;
    private  int progressBarStatus;
    private Handler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        //StrictMode.setThreadPolicy(policy);




        progress = findViewById(R.id.progress);
        trat = findViewById(R.id.imageMain);
        //trat.setBackgroundResource(R.drawable.jojo);
        progressBarHandler = new Handler();
        URL url = null;

    //  while(true) {

        try {
        CatImages req = new CatImages();
        req.execute("https://cataas.com/cat?json=true");


                initProgressBar();
                Thread.sleep(4000);
            //req = new CatImages();
//            req.execute("https://cataas.com/cat?json=true");
            } catch (Exception e) {
                e.printStackTrace();
           }

       }


  // }
private void initProgressBar() {
    // progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    progress.setProgress(0);
    progress.setMax(100);
    // progress.show();
    //reset progress bar and filesize status
    progressBarStatus = 0;

    new Thread(new Runnable() {
        public void run() {
            while (progressBarStatus < 4) {
                // performing operation
               // progressBarStatus++;
                progress.incrementProgressBy(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Updating the progress bar
                progressBarHandler.post(new Runnable() {
                    public void run() {
                        progress.setProgress(progressBarStatus);
                    }
                });
            }
            // performing operation if file is downloaded,
            if (progressBarStatus >= 100) {
                // sleeping for 1 second after operation completed
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // close the progress bar dialog
                //progress.dismiss();
            }
        }
    }).start();
}
   public class CatImages extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String catURL = null;
            try {

                //create a URL object of what server to contact:
                URL url = new URL(strings[0]);
                Bitmap myBitmap = null;

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                JSONObject obj = new JSONObject(content.toString());
                catURL = "https://cataas.com" + obj.getString("url");
                //catURL = "https://cataas.com/cat/610f082c029b39001141db53";
                String[] split = catURL.split("/");
                String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/catPics";
                File dir = new File(file_path);
                if(!dir.exists()){
                    boolean r = dir.mkdirs();
                    if(!r){
                        Log.i("failed","I CAN SAVE YOUR FUCKING DIR!!!!!!!!!!!!");
                    }
                }
                File file = new File(dir, split[split.length-1] + ".png");
                if(!file.exists()){
                    url=new URL(catURL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inStream = urlConnection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(inStream);

                    if(!file.exists()){
                        boolean result = file.createNewFile();
                        if (!result){
                            Log.i("failed","I CAN SAVE YOUR FUCKING FILE!!!!!!!!!!!!!");
                        }
                        FileOutputStream fOut = new FileOutputStream(file);

                        myBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                        fOut.flush();
                        fOut.close();
                    }

                }
                else{
                    myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                }

                trat.setImageBitmap(myBitmap);




             // try {
                //    Thread.sleep(4000);
               // } catch (InterruptedException e) {
                 //   e.printStackTrace();
                //}
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return catURL;
        }
    }
}
