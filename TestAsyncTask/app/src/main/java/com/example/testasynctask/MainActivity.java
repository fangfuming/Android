package com.example.testasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar2);
        MyAsync myAsync = new MyAsync();
        myAsync.execute();
    }



    private class  MyAsync extends AsyncTask<Void,Integer,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("haha","执行异步操作之前");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0;i<=100;i++){
                publishProgress(i);
                try{
                    sleep(100);
                }catch (Exception e){
                    Log.d("haha",e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("haha",String.valueOf(values.length));
            textView.setText(String.valueOf(values[0])+"%");
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("haha","执行异步操作完成");
            textView.setText("下载完成");

        }
    }
}
