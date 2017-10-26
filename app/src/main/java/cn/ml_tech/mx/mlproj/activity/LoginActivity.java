package cn.ml_tech.mx.mlproj.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.ml_tech.mx.mlproj.base.BaseActivity;
import cn.ml_tech.mx.mlproj.fragment.BottomFragment;
import cn.ml_tech.mx.mlproj.fragment.LoginFragment;
import cn.ml_tech.mx.mlproj.fragment.OptionFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.util.CommonUtil;
import cn.ml_tech.mx.mlproj.util.SharedPreferencesUtils;
import cn.ml_tech.mx.mlproj.util.VerSionUtil;
import cn.ml_tech.mx.mlservice.DAO.Permission;
import cn.ml_tech.mx.mlservice.DAO.User;

public class LoginActivity extends BaseActivity implements BottomFragment.OnFragmentInteractionListener {
    private LoginFragment loginFragment = null;
    private OptionFragment optionFragment = null;
    private CheckBox chkRember;
    private Permission permission;
    private ProgressDialog progressDialog;
    private boolean isPermission;
    private static final int RESTARTSUCESS = 123;
    private VerSionUtil verSionUtil;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            switch (msg.what) {
                case GETPERMISSIONSUCESS:
                    isPermission = true;
                    optionFragment = (OptionFragment) switchContentFragment(OptionFragment.class.getSimpleName());
                    break;
                case RESTARTSUCESS:
                    if (optionFragment != null)
                        optionFragment.restart();
                case GETPERMISSIONFAILURE:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    32);
        } else {
            if ((boolean) SharedPreferencesUtils.getParam(this, CommonUtil.ISUPDATE, true)) {
                verSionUtil = new VerSionUtil(this);
                verSionUtil.updateVersion();
            }

        }
        LogDebug(LoginFragment.class.getSimpleName());
        loginFragment = (LoginFragment) switchContentFragment(LoginFragment.class.getSimpleName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (loginFragment != null && loginFragment.getView() != null) {
            chkRember = (CheckBox) loginFragment.getView().findViewById(R.id.checkBoxRember);
            if (chkRember != null)
                chkRember.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void doAfterGetService() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    permission = mService.getPermissonByUrl("", true);
                    handler.sendEmptyMessage(RESTARTSUCESS);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(GETPERMISSIONFAILURE);
                }
            }
        }.start();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    protected Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);
        if (f == null) {
            if (tag.equals("LoginFragment")) {
                f = new LoginFragment();
            } else if (tag.equals("OptionFragment")) {
                f = new OptionFragment();
            } else {
                f = super.getFragment(tag);
            }
        }
        return f;
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        LoginActivity.this.finish();

                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        final EditText etUserName = (EditText) this.findViewById(R.id.etUserName);
        final EditText etPassword = (EditText) this.findViewById(R.id.etPassword);
        View btn = this.findViewById(R.id.btLogin);
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        List<User> list = mService.getUserList();
                        LogDebug(String.valueOf(list.size()));
                        boolean flag = mService.checkAuthority("1230", "123");
                        LogDebug("checkAuthority " + String.valueOf(flag));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    String userName = etUserName.getText().toString();
                    String password = etPassword.getText().toString();
                    boolean result = false;
                    try {
                        result = mService.checkAuthority(userName, password);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    String msg = "login failed";
                    if (result) {
                        if (progressDialog == null)
                            progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setTitle("载入数据中....");
                        progressDialog.show();
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    long userid = mService.getUserId();
                                    app.setUserid(userid);
                                    permission = mService.getPermissonByUrl("", true);
                                    handler.sendEmptyMessage(GETPERMISSIONSUCESS);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                    handler.sendEmptyMessage(GETPERMISSIONFAILURE);
                                }
                            }
                        }.start();
                        msg = "login success";
                        app.setUserName(userName);
                        app.setLogined(true);
//                        bottomFragment.updateDisplay();
                    }
                    Toast.makeText(v.getContext(), msg, Toast.LENGTH_SHORT).show();
                    final String finalMsg = msg;
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                if (finalMsg.equals("login success"))
                                    mService.addAudittrail(4, 1, "1", CommonUtil.LOGINSUCESS);
                                else

                                    mService.addAudittrail(4, 1, "1", CommonUtil.LOGINREFUSE);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();
                }
            });
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 32: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if ((boolean) SharedPreferencesUtils.getParam(this, CommonUtil.ISUPDATE, true))
                        new VerSionUtil(this).updateVersion();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    public boolean isPermission() {
        return isPermission;
    }

    public Permission getPermission() {
        return permission;
    }
}


