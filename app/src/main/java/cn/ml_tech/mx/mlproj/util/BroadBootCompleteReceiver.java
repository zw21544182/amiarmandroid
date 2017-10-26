package cn.ml_tech.mx.mlproj.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.ml_tech.mx.mlproj.activity.LoginActivity;

/**
 * Created by ml on 2017/6/16.
 */

public class BroadBootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentStart=new Intent(context,LoginActivity.class);
        intentStart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentStart);
    }
}
