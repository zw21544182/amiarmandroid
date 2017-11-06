package cn.ml_tech.mx.mlproj.activity;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.base.AmiApp;
import cn.ml_tech.mx.mlproj.base.BaseActivity;
import cn.ml_tech.mx.mlproj.fragment.BottomFragment;
import cn.ml_tech.mx.mlproj.fragment.JcsjcxFragment;
import cn.ml_tech.mx.mlproj.util.CommonUtil;
import cn.ml_tech.mx.mlservice.DAO.Permission;

public class JcsjcxActivity extends BaseActivity implements
        BottomFragment.OnFragmentInteractionListener, View.OnClickListener {
    JcsjcxFragment jcsjcxFragment = null;
    private static final int PERMISSIONSUCESS = 47;
    private static final int PERMISSIONFAILURE = 48;
    private boolean isPermissionSucess = false;
    Permission permission;
    private AmiApp amiApp;

    private Button btnBack;

    @Override
    public void doAfterGetService() {
        amiApp = (AmiApp) getApplication();
        jcsjcxFragment = (JcsjcxFragment) switchContentFragment(JcsjcxFragment.class.getSimpleName());

        new Thread() {
            @Override
            public void run() {
                super.run();
                String url = "";
                try {
                    mService.addAudittrail(5, 5, "", CommonUtil.ENTERDRUGSTANDARD);
                } catch (RemoteException e) {
                    e.printStackTrace();

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
    }

    @Override
    protected Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);
        if (f == null) {
            if (tag.equals("content")) {
            } else if (tag.equals("JcsjcxFragment")) {
                f = new JcsjcxFragment();
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
    protected void handlerExtraInfo(String extra) {
        super.handlerExtraInfo(extra);
        jcsjcxFragment.initRecycleReport();
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
