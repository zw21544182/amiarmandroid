package cn.ml_tech.mx.mlproj;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

import cn.ml_tech.mx.mlproj.util.CommonUtil;
import cn.ml_tech.mx.mlservice.DAO.P_Operator;
import cn.ml_tech.mx.mlservice.DAO.P_Source;
import cn.ml_tech.mx.mlservice.IMlService;

/**
 * Created by mx on 2017/5/8.
 */
public class AmiApp extends Application {
    private IMlService mMLService;
    private static Context context;
    private List<P_Source> p_sources;
    private List<P_Operator> p_operators;

    public AmiApp() {
        this.isLogined = false;


    }

    public static Context getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Intent intent = new Intent();
        intent.setAction("cn.ml_tech.mx.mlservice.MotorServices");
        intent.setPackage("cn.ml_tech.mx.mlservice");
        getApplicationContext().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("zw","service connect");
                mMLService = IMlService.Stub.asInterface(service);
                try {
                    mMLService.addAudittrail(5, 5, "", CommonUtil.POWERON);
                    p_operators = mMLService.getAllP_Operator();
                    p_sources = mMLService.getAllP_Source();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }


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

    public List<P_Source> getP_sources() {
        return p_sources;
    }

    public List<P_Operator> getP_operators() {
        return p_operators;
    }

    private String userName;
    private Boolean isLogined;
}
