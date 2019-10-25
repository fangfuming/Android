package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    BatteryLevelReceiver batteryLevelReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter();
        //我们要收听的广播是电量变化
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        //插上usb进行充电 广播
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        //断开usb连接  广播
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
         batteryLevelReceiver = new BatteryLevelReceiver();
        //动态注册广播
        this.registerReceiver(batteryLevelReceiver,intentFilter);
    }

    //取消广播的监听，防止内存泄露
    @Override
    protected void onDestroy() {
        if (batteryLevelReceiver != null) {
            unregisterReceiver(batteryLevelReceiver);
        }
        super.onDestroy();
    }

    /**
     * 第一步：创建一个广播接收者，继承自BroadcastReceiver
     */
    private  class  BatteryLevelReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            TextView textView = findViewById(R.id.hh);

            String action = intent.getAction();
            if(Intent.ACTION_BATTERY_CHANGED.equals(action)){
            int level = intent.getIntExtra("level",0);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,100);
            Log.d("ss",action+level);
            Log.d("ss", String.valueOf(scale));
            int percent = (level  * 100 / scale);
            textView.setText("当前电量是"+percent+"%");
            }else if(Intent.ACTION_POWER_CONNECTED.equals(action)){
                textView.setText("usb已连接");
            }else if(Intent.ACTION_POWER_DISCONNECTED.equals(action)){
                textView.setText("usb已断开");
            }
        }
    }
}
