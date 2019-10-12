package com.example.texthandler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int HAND_UPDATE_TIME = 0x0001; //计数更新消息
    private static final int HAND_START_THREAD = 0x0002; //线程启动消息
    private static final int HAND_STOP_THREAD = 0x0003;  //线程结束消息

    private TextView mShowTime;

    private Thread mCountTimeThread;
    private int mCurrentTime;
    private boolean isRunning;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){  //处理消息
                case HAND_UPDATE_TIME:
                    mShowTime.setText(String.format("正在计数：%d", mCurrentTime));
                    break;
                case HAND_STOP_THREAD:
                    Toast.makeText(MainActivity.this,"线程已结束",Toast.LENGTH_LONG).show();
                    break;
                case HAND_START_THREAD:
                    Toast.makeText(MainActivity.this,"线程已经启动",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowTime = findViewById(R.id.tv_show_time);
    }

    /**
     * 启动线程 按钮绑定事件回调
     * @param view
     */
    public void clickStartThread(View view) {
        startThread();
    }

    /**
     * 结束线程 按钮绑定事件回调
     * @param view
     */
    public void clickStopThread(View view) {
        stopThread();
    }

    /**
     * 通过 Thread 创建一个线程
     */
    private void createThread(){
        mCountTimeThread = new Thread(){
            @Override
            public void run() {
                mHandler.sendEmptyMessage(HAND_START_THREAD);
                while (isRunning){
                    try {
                        Thread.sleep(1000); //休息 1 秒钟
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mCurrentTime++; //计数加 1
                    mHandler.sendEmptyMessage(HAND_UPDATE_TIME);
                }
                mHandler.sendEmptyMessage(HAND_STOP_THREAD);
            }
        };
    }

    /**
     * 启动一个线程
     */
    private void startThread(){
        stopThread();
        isRunning = true;
        createThread();
        mCurrentTime = 0;
        mHandler.sendEmptyMessage(HAND_UPDATE_TIME);
        mCountTimeThread.start();
    }

    /**
     * 结束一个线程
     */
    private void stopThread(){
        if(mCountTimeThread == null) return;
        isRunning = false;
        mCountTimeThread = null;
    }
}
