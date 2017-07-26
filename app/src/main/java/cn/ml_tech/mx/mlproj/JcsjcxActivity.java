package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cn.ml_tech.mx.mlservice.DAO.P_Source;
import cn.ml_tech.mx.mlservice.DAO.Permission;

public class JcsjcxActivity extends BaseActivity implements
        BottomFragment.OnFragmentInteractionListener, View.OnClickListener {
    JcsjcxFragment jcsjcxFragment = null;
    private static final int PERMISSIONSUCESS = 47;
    private static final int PERMISSIONFAILURE = 48;
    private boolean isPermissionSucess = false;
    JcsjcxmainFragment jcsjcxmainFragment = null;
    Permission permission;
    private AmiApp amiApp;
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
                    jcsjcxFragment = (JcsjcxFragment) switchContentFragment(JcsjcxFragment.class.getSimpleName());
                    break;
                case PERMISSIONFAILURE:
                    showToast("权限验证失败");
                    break;
            }
        }
    };
    private Button btnBack;

    @Override
    public void doAfterGetService() {
        amiApp = (AmiApp) getApplication();
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("权限加载中....");
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
                        if (p_source.getId() == 19) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    protected Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);
        if (f == null) {
            if (tag.equals("content")) {
            } else if (tag.equals("JcsjcxFragment")) {
                f = new JcsjcxFragment();
            } else if (tag.equals("JcsjcxmainFragment")) {
                f = new JcsjcxmainFragment();
            } else {
                f = super.getFragment(tag);
            }
        }
        return f;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        switchTopFragment("");//hiden powerbutton on this Activity

    }

    public Permission getPermission() {
        return permission;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btBack:
                if (jcsjcxFragment.isReportLayout())
                    JcsjcxActivity.this.finish();
                else jcsjcxFragment.ShowReport();
                break;
            default:
                break;
        }
    }
}
