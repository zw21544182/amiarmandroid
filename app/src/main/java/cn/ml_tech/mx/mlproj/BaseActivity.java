package cn.ml_tech.mx.mlproj;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlservice.Bean.User;
import cn.ml_tech.mx.mlservice.IMlService;
import cn.ml_tech.mx.mlservice.MotorControl;

public class BaseActivity extends Activity implements HeadFragment.OnFragmentInteractionListener {
    public static final int OVERLAY_PERMISSION_REQ_CODE = 4545;
    protected AmiApp app = null;
    protected BottomFragment bottomFragment = null;
    protected HeadFragment headFragment = null;
    protected String mCurrentContentFragmentTag;
    protected String mCurrentTopFragmentTag;
    protected String mCurrentBottomFragmentTag;
    protected FragmentTransaction mFragmentTransaction;
    protected FragmentManager mFragmentManager;

    public IMlService getmService() {
        return mService;
    }

    public void setmService(IMlService mService) {
        this.mService = mService;
    }

    protected IMlService mService;
    public static final String FULL_SCREEN_EXPAND_STATUSBAR = "android.settings.FULL_SCREEN_EXPAND_STATUSBAR";

    protected void logv(String msg) {
        Log.v(getClass().getSimpleName(), msg);
    }

    protected void LogDebug(String msg) {
        Log.d(getClass().getSimpleName() + " " + " debug ", msg);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LogDebug("Base Resume");

    }

    //禁止下拉
    private void prohibitDropDown() {
        customViewGroup view;
        WindowManager manager;
        manager = ((WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                // this is to enable the notification to recieve touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = (int) (50 * getResources()
                .getDisplayMetrics().scaledDensity);
        localLayoutParams.format = PixelFormat.TRANSPARENT;
        view = new customViewGroup(this);
        manager.addView(view, localLayoutParams);

        customViewGroup viewBottom = new customViewGroup(this);
        localLayoutParams.gravity = Gravity.BOTTOM;
        localLayoutParams.height = (int) (50 * getResources()
                .getDisplayMetrics().scaledDensity);
        manager.addView(viewBottom, localLayoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (AmiApp) getApplication();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setContentView(R.layout.activity_base);
        logv("created\n");
        ActivityCollector.addActivity(this);
        mFragmentManager = getFragmentManager();
        Intent serviceIntent = new Intent();
        serviceIntent.setAction("cn.ml_tech.mx.mlservice.MotorServices");
        serviceIntent.setPackage("cn.ml_tech.mx.mlservice");
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
       /*
        try {
            List<User>list= mService.getUserList();
            Log.d("fer", String.valueOf(list.size()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        LogDebug("mService get userlist");
        app.setmMLService(mService);
        try {
            List<User> list= app.getmMLService().getUserList();
            //LogDebug("User Size is "+list.size());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        hideBottomUIMenu();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //if (Settings.System.canWrite(this)) {
            if (Settings.canDrawOverlays(this)) {
                //Toast.makeText(this, "Write allowed :-)", Toast.LENGTH_LONG).show();
                //Settings.System.putInt(this.getContentResolver(), FULL_SCREEN_EXPAND_STATUSBAR, 0);
                prohibitDropDown();
            } else {
                Toast.makeText(this, "Write not allowed :-(", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
                //intent.setData(Uri.parse("package:" + getPackageName()));
                // startActivity(intent);
            }

        }
        bottomFragment = (BottomFragment) switchBottomFragment(BottomFragment.class.getSimpleName());
        headFragment = (HeadFragment) switchTopFragment(HeadFragment.class.getSimpleName());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "User can access system settings without this permission!", Toast.LENGTH_SHORT).show();
            } else {
                prohibitDropDown();
            }
        }
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
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        LogDebug("on destory " + this.getPackageName());
        super.onDestroy();
        unbindService(mConnection);
        ActivityCollector.removeActivity(this);
    }

    protected Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);
        if (f == null) {
            if (tag.equals("BottomFragment")) {
                f = new BottomFragment();
            } else if (tag.equals("HeadFragment")) {
                f = new HeadFragment();
            }
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

    public Fragment switchContentFragment(String tag) {
        Fragment f = null;
        if (!tag.equals(mCurrentContentFragmentTag)) {
            if (mCurrentContentFragmentTag != null)
                detachFragment(getFragment(mCurrentContentFragmentTag));
            attachFragment(R.id.linearlayout_middle, f = getFragment(tag), tag);
            mCurrentContentFragmentTag = tag;
            commitTransactions();
        }
        return f;
    }

    protected Fragment switchTopFragment(String tag) {
        Fragment f = null;
        if (!tag.equals(mCurrentTopFragmentTag)) {
            if (mCurrentTopFragmentTag != null) detachFragment(getFragment(mCurrentTopFragmentTag));
            //attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(tag), tag);
            LogDebug("replace fragment " + tag);
            attachFragment(R.id.linearlayout_top, f = getFragment(tag), tag);
            mCurrentTopFragmentTag = tag;
            commitTransactions();
        }
        return f;
    }

    protected Fragment switchBottomFragment(String tag) {
        Fragment f = null;
        if (!tag.equals(mCurrentBottomFragmentTag)) {
            if (mCurrentBottomFragmentTag != null)
                detachFragment(getFragment(mCurrentBottomFragmentTag));
            //attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(tag), tag);
            LogDebug("replace fragment " + tag);
            attachFragment(R.id.linearlayout_bottom, f = getFragment(tag), tag);
            mCurrentBottomFragmentTag = tag;
            commitTransactions();
        }
        return f;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        collapseStatusBar();
    }

    private void collapseStatusBar() {
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        try {
            Object service = getSystemService("statusbar");

            Class<?> statusbarManager = Class
                    .forName("android.app.StatusBarManager");
            Method collapse = null;
            if (service != null) {
                if (currentApiVersion <= 16) {
                    collapse = statusbarManager.getMethod("collapse");
                } else {
                    collapse = statusbarManager.getMethod("collapsePanels");
                }
                //collapse.setAccessible(true);
                collapse.invoke(service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class customViewGroup extends ViewGroup {

        public customViewGroup(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            Log.v("customViewGroup", "**********Intercepted");
            return true;
        }
    }
}
