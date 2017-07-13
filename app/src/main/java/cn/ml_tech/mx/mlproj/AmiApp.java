package cn.ml_tech.mx.mlproj;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import cn.ml_tech.mx.mlservice.IMlService;

/**
 * Created by mx on 2017/5/8.
 */
public class AmiApp extends Application {
    private IMlService mMLService;
    private static Context context;


    public AmiApp() {
        this.isLogined = false;


    }

    public static Context getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Debug", "onCreate: ");
        context = this;
        Intent intent = new Intent();
        intent.setAction("cn.ml_tech.mx.mlservice.MotorServices");
        intent.setPackage("cn.ml_tech.mx.mlservice");
        getApplicationContext().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mMLService = IMlService.Stub.asInterface(service);
                Log.d("zw", "onServiceConnected");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mMLService = null;
            }
        }, Context.BIND_AUTO_CREATE);
//        try {
//            List<User> list = mMLService.getUserList();
//            Log.d("debug", "Application onCreate: " + String.valueOf(list.size()));
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    public IMlService getmMLService() {
        return mMLService;
    }

    public void setmMLService(IMlService mMLService) {
        this.mMLService = mMLService;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getLogined() {
        return isLogined;
    }

    public void setLogined(Boolean logined) {
        isLogined = logined;
    }

    private String userName;
    private Boolean isLogined;
}
