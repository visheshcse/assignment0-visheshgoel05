package com.example.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

public class WifiBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        boolean success = intent.getBooleanExtra(
                WifiManager.EXTRA_RESULTS_UPDATED, false);
        if (success) {

        }

    }
}
