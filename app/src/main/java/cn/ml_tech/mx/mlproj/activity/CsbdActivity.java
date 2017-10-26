package cn.ml_tech.mx.mlproj.activity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import cn.ml_tech.mx.mlproj.base.AmiApp;
import cn.ml_tech.mx.mlproj.base.BaseActivity;
import cn.ml_tech.mx.mlproj.fragment.BottomFragment;
import cn.ml_tech.mx.mlproj.fragment.CsbdFragment;
import cn.ml_tech.mx.mlservice.DAO.P_Source;
import cn.ml_tech.mx.mlservice.DAO.Permission;

public class CsbdActivity extends BaseActivity implements BottomFragment.OnFragmentInteractionListener {
    CsbdFragment csbdFragment = null;
    BottomFragment bottomFragment = null;
    private static final int PERMISSIONSUCESS = 47;
    private static final int PERMISSIONFAILURE = 48;
    public Permission permission;
    private AmiApp amiApp;//
    private boolean isPermissionSucess = false;
    private ProgressDialog progressDialog;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            switch (msg.what) {
                case PERMISSIONSUCESS:
                    isPermissionSucess = true;
                    csbdFragment = (CsbdFragment) switchContentFragment(CsbdFragment.class.getSimpleName());
                    bottomFragment = (BottomFragment) switchBottomFragment(BottomFragment.class.getSimpleName());
                    break;
                case PERMISSIONFAILURE:
                    showToast("权限验证失败");
                    break;
            }
        }
    };

    @Override
    public void doAfterGetService() {
        amiApp = (AmiApp) getApplication();
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("数据加载中....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                String url = "";
                try {
                    for (P_Source p_source : amiApp.getP_sources()
                            ) {
                        if (p_source.getId() == 6) {
                            url = p_source.getUrl();
                            Log.d("zw", url);
                            break;
                        }
                    }
                    if (mService != null)
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
    }

    protected void onStart() {
        super.onStart();
        switchTopFragment("");


    }

    @Override
    protected Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);
        if (f == null) {
            if (tag.equals("content")) {
            } else if (tag.equals("CsbdFragment")) {
                LogDebug("new CsbdFragment");
                f = new CsbdFragment();
            } else if (tag.equals("BottomFragment")) {
                LogDebug("new BottomFragment");
                f = new BottomFragment();
            } else {
                f = super.getFragment(tag);
            }
        }
        return f;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
