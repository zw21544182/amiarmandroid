package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import cn.ml_tech.mx.mlproj.SettingFragment.ManchineManagerFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.SysConfigFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.TrayManagerFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.UserManagerFragment;
import cn.ml_tech.mx.mlproj.util.CommonUtil;
import cn.ml_tech.mx.mlservice.DAO.P_Source;
import cn.ml_tech.mx.mlservice.DAO.Permission;

public class XtwhActivity extends BaseActivity implements  BottomFragment.OnFragmentInteractionListener
        , View.OnClickListener {
    public XtwhFragment getXtwhFragment() {
        return xtwhFragment;
    }

    private static final int PERMISSIONSUCESS = 47;
    private static final int PERMISSIONFAILURE = 48;
    private boolean isPermissionSucess = false;
    public Permission permission;
    private AmiApp amiApp;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PERMISSIONSUCESS:
                    isPermissionSucess = true;
                    xtwhFragment = (XtwhFragment) switchContentFragment(XtwhFragment.class.getSimpleName());
                    break;
                case PERMISSIONFAILURE:
                    showToast("权限验证失败");
                    break;
            }
        }
    };
    public XtwhFragment xtwhFragment = null;
    LinearLayout mllSysSetUpContent = null;

    @Override
    public void doAfterGetService() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String url = "";
                try {
                    for (P_Source p_source : amiApp.getP_sources()
                            ) {
                        if (p_source.getId() == 27) {
                            url = p_source.getUrl();
                            Log.d("zw", url);
                            break;
                        }
                    }
                    mService.addAudittrail(5,5,"", CommonUtil.ENTERSYSTEMSETUP);
                    permission = mService.getPermissonByUrl(url, false);
                    handler.sendEmptyMessage(PERMISSIONSUCESS);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(PERMISSIONFAILURE);

                }
            }
        }.start();
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