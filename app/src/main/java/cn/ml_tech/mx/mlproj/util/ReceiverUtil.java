package cn.ml_tech.mx.mlproj.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.util.Log;

/**
 * 创建时间: 2017/7/6
 * 创建人: zhongwang
 * 功能描述: 广播接受者工具类
 */

public abstract class ReceiverUtil {
    public static final String ENTERBOTTLE = "com.enterbottle";
    private String action = "";
    private MyReceiver receiver;
    private Context context;

    public ReceiverUtil(String action, Context context) {
        this.action = action;
        this.context = context;
    }

    private ReceiverUtil() {
    }

    public void unRefister() {
        if (receiver != null)
            context.unregisterReceiver(receiver);
    }

    public void inRegister() {
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        context.registerReceiver(receiver, filter);

    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("zw", "onReceive");
            try {
                operate(context, intent);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    protected abstract void operate(Context context, Intent intent) throws RemoteException;
}
