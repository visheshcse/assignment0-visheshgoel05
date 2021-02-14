package com.example.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BatteryBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /*StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction());
        String log = sb.toString();*/
        String message = "";
        switch(intent.getAction()){
            case Intent.ACTION_BATTERY_LOW:
                message = "Action: "+ "ACTION_BATTERY_LOW";
                break;
            case Intent.ACTION_BATTERY_OKAY:
                message = "Action: " + "ACTION_BATTERY_OKAY";
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                message = "Action: " + "ACTION_POWER_DISCONNECTED";
                break;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
