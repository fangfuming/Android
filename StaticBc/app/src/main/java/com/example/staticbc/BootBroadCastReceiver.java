package com.example.staticbc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("hh",action);
        Toast.makeText(context,"监听到开机广播",Toast.LENGTH_LONG).show();
    }
}
