package cn.ml_tech.mx.mlproj;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlservice.IMlService;
import cn.ml_tech.mx.mlservice.MotorControl;

public class BaseActivity extends AppCompatActivity {

    protected String mCurrentContentFragmentTag;
    protected String mCurrentTopFragmentTag;
    protected String mCurrentBottomFragmentTag;
    protected FragmentTransaction mFragmentTransaction;
    protected FragmentManager mFragmentManager;
    private ActivityCollector activityCollector = new ActivityCollector();
    protected IMlService mService;

    protected void logv(String msg) {
        Log.v(getClass().getSimpleName(), msg);
        activityCollector.addActivity(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setContentView(R.layout.activity_base);
        logv("created\n");
        mFragmentManager = getSupportFragmentManager();

        Intent serviceIntent = new Intent();
        serviceIntent.setAction("cn.ml_tech.mx.mlservice.MotorServices");
        serviceIntent.setPackage("cn.ml_tech.mx.mlservice");
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            //mLog.append("Service binded!\n");
            mService = IMlService.Stub.asInterface(service);

            try {
                performSomething();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };

    private void performSomething() throws RemoteException {
        MotorControl mControl = null;
        mService.addMotorControl(mControl);
    }
    @Override
    protected  void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        activityCollector.removeActivity(this);
    }

    protected Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);

        if (f == null) {

        }

        return f;
    }
    protected FragmentTransaction ensureTransaction() {
        if (mFragmentTransaction == null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }

        return mFragmentTransaction;
    }
    protected void attachFragment(int layout, Fragment f, String tag) {
        if (f != null) {
            if (f.isDetached()) {
                ensureTransaction();
                mFragmentTransaction.attach(f);
            } else if (!f.isAdded()) {
                ensureTransaction();
                mFragmentTransaction.add(layout, f, tag);
            }
        }
    }

    protected void detachFragment(Fragment f) {
        if (f != null && !f.isDetached()) {
            ensureTransaction();
            mFragmentTransaction.detach(f);
        }
    }

    protected void commitTransactions() {
        if (mFragmentTransaction != null && !mFragmentTransaction.isEmpty()) {
            mFragmentTransaction.commit();
            mFragmentTransaction = null;
        }
    }
    protected Fragment switchContentFragment(String tag){
        Fragment f = null;
        if(!tag.equals(mCurrentContentFragmentTag)){
            if (mCurrentContentFragmentTag != null) detachFragment(getFragment(mCurrentContentFragmentTag));
            //attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(tag), tag);
            attachFragment(R.id.linearlayout_middle,  f=getFragment(tag), tag);
            mCurrentContentFragmentTag = tag;
            commitTransactions();
        }
        return f;
    }
    protected Fragment switchTopFragment(String tag){
        Fragment f = null;
        if(!tag.equals( mCurrentTopFragmentTag)){
            if (mCurrentTopFragmentTag != null) detachFragment(getFragment(mCurrentTopFragmentTag));
            //attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(tag), tag);
            attachFragment(R.id.linearlayout_top,  f=getFragment(tag), tag);
            mCurrentTopFragmentTag = tag;
            commitTransactions();
        }
        return f;
    }
    protected Fragment switchBottomFragment(String tag){
        Fragment f = null;
        if(!tag.equals(mCurrentBottomFragmentTag)){
            if (mCurrentBottomFragmentTag != null) detachFragment(getFragment(mCurrentBottomFragmentTag));
            //attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(tag), tag);
            attachFragment(R.id.linearlayout_bottom,  f=getFragment(tag), tag);
            mCurrentBottomFragmentTag = tag;
            commitTransactions();
        }
        return f;
    }

    public class ActivityCollector {
        public List<Activity> activityList = new ArrayList<>();

        public void addActivity(Activity activity) {
            activityList.add(activity);
        }
        public void removeActivity(Activity activity) {
            activityList.remove(activity);
        }
        public void finishAll() {
            for (Activity activity : activityList) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }

    }
}
