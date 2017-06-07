package cn.ml_tech.mx.mlservice.Bean;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import cn.ml_tech.mx.mlproj.BaseActivity;
import cn.ml_tech.mx.mlservice.DAO.Tray;
import cn.ml_tech.mx.mlservice.IMlService;
/**
 * Created by ml on 2017/6/6.
 */

public class TrayHelper {
    private static final String TAG ="TrayHelp" ;
    Tray tray;
    private Context mcontext;
    private IMlService mlService;
    private String trayIcId=null;
    public TrayHelper(Context context)
    {
        this.mcontext=context;
        tray=new Tray();
        BaseActivity activity=(BaseActivity)mcontext;
        mlService = activity.getmService();
    }
    public void resetTray()
    {
        this.tray.setIcId("");
        this.tray.setDiameter(0);
        this.tray.setDisplayId(0);
        this.tray.setInnerDiameter(0);
        this.tray.setExternalDiameter(0);
        this.tray.setMark("");
    }
    public String getTrayIcId()
    {
        try {
            trayIcId = mlService.getTrayIcId();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(!TextUtils.isEmpty(trayIcId))tray.setIcId(trayIcId);
        return tray.getIcId();
    }
    public boolean saveOrUpdateTray(Tray tray)
    {
        Log.d(TAG, "saveOrUpdateTray: "+tray.getIcId());
        Log.d(TAG, "saveOrUpdateTray: "+tray.getId());
        Log.d(TAG, "saveOrUpdateTray: "+tray.getMark());
        Log.d(TAG, "saveOrUpdateTray: "+tray.getDiameter());
        Log.d(TAG, "saveOrUpdateTray: "+tray.getDisplayId());
        Log.d(TAG, "saveOrUpdateTray: "+tray.getInnerDiameter());
        Log.d(TAG, "saveOrUpdateTray: "+tray.getExternalDiameter());

        boolean flag=false;
        try {
           flag= mlService.setTray(tray);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return flag;
    }
    public void EditTray(Tray tray){this.tray=tray;}
    public boolean DelTray(Tray tray)
    {
        boolean flag=false;
        try {
            flag=mlService.delTray(tray);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return flag;

    }


}
