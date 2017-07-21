package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import cn.ml_tech.mx.mlproj.SettingFragment.ManchineManagerFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.SysConfigFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.TrayManagerFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.UserManagerFragment;

public class XtwhActivity extends BaseActivity implements XtwhFragment.OnFragmentInteractionListener, BottomFragment.OnFragmentInteractionListener
        , View.OnClickListener {
    public XtwhFragment getXtwhFragment() {
        return xtwhFragment;
    }

    public XtwhFragment xtwhFragment = null;
    LinearLayout mllSysSetUpContent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xtwhFragment = (XtwhFragment) switchContentFragment(XtwhFragment.class.getSimpleName());
        LogDebug(XtwhFragment.class.getSimpleName());
    }

//    @Override
//    public Fragment switchContentFragment(String tag) {
//        Fragment f = null;
//        if(!tag.equals(mCurrentContentFragmentTag)){
//            if (mCurrentContentFragmentTag != null) detachFragment(getFragment(mCurrentContentFragmentTag));
//            attachFragment(R.id.llSysSetUpContent,  f=getFragment(tag), tag);
//            mCurrentContentFragmentTag = tag;
//            commitTransactions();
//        }
//        return f;
////        return super.switchContentFragment(tag);
//    }

    @Override
    protected void onStart() {
        super.onStart();
        switchTopFragment("");//hiden powerbutton on this Activity
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    protected Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);
        if (f == null) {
            if (tag.equals("content")) {

            } else if (tag.equals("XtwhFragment")) {
                f = new XtwhFragment();
            } else if (tag.equals("UserManagerFragment")) {
                UserManagerFragment fragmentuser = new UserManagerFragment();
                f = fragmentuser;
            } else if (tag.equals("ManchineManagerFragment")) {
                f = new ManchineManagerFragment();
            } else if (tag.equals("TrayManagerFragment")) {
                TrayManagerFragment trayManagerFragment = new TrayManagerFragment();
                f = (Fragment) trayManagerFragment;

                //f=(Fragment) new TrayManagerFragment();
            } else if (tag.equals("DeviceDebugFragment")) {
                f = new DeviceDebugFragment();
            } else if (tag.equals("SysConfigFragment")) {
                f = new SysConfigFragment();
            } else {
                f = super.getFragment(tag);
            }
        }
        return f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                finish();
                break;
            default:
                break;
        }
    }


}