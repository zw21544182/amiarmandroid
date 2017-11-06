package cn.ml_tech.mx.mlproj.activity;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.base.AmiApp;
import cn.ml_tech.mx.mlproj.base.BaseActivity;
import cn.ml_tech.mx.mlproj.fragment.BottomFragment;
import cn.ml_tech.mx.mlproj.fragment.XtwhFragment;
import cn.ml_tech.mx.mlproj.settingfragment.ManchineManagerFragment;
import cn.ml_tech.mx.mlproj.settingfragment.SysConfigFragment;
import cn.ml_tech.mx.mlproj.settingfragment.TrayManagerFragment;
import cn.ml_tech.mx.mlproj.settingfragment.UserManagerFragment;
import cn.ml_tech.mx.mlproj.util.CommonUtil;

public class XtwhActivity extends BaseActivity implements BottomFragment.OnFragmentInteractionListener
        , View.OnClickListener {
    public XtwhFragment getXtwhFragment() {
        return xtwhFragment;
    }

    private AmiApp amiApp;
    public XtwhFragment xtwhFragment = null;

    @Override
    public void doAfterGetService() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String url = "";
                try {
                    mService.addAudittrail(5, 5, "", CommonUtil.ENTERSYSTEMSETUP);
                } catch (RemoteException e) {
                    e.printStackTrace();

                }
            }
        }.start();
        xtwhFragment = (XtwhFragment) switchContentFragment(XtwhFragment.class.getSimpleName());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amiApp = (AmiApp) getApplication();

    }


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