package cn.ml_tech.mx.mlproj;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.ml_tech.mx.mlservice.Bean.User;

public class LoginActivity extends BaseActivity implements LoginFragment.OnFragmentInteractionListener,OptionFragment.OnFragmentInteractionListener,
                                                BottomFragment.OnFragmentInteractionListener {
    private LoginFragment loginFragment = null;
    private OptionFragment optionFragment = null;
    private CheckBox chkRember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogDebug(LoginFragment.class.getSimpleName());
        loginFragment = (LoginFragment) switchContentFragment(LoginFragment.class.getSimpleName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        *
        *@author wl
        *create at  2017/4/27 14:42
        * the function maybe not use
        */

        if(loginFragment!=null&&loginFragment.getView()!=null)
        {
            LogDebug(" on start loginfragment is not null");
            chkRember= (CheckBox) loginFragment.getView().findViewById(R.id.checkBoxRember);
            if(chkRember!=null)
                chkRember.setVisibility(View.INVISIBLE);//set the rember password invisible
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        final EditText etUserName = (EditText)this.findViewById(R.id.etUserName);
        final EditText etPassword = (EditText)this.findViewById(R.id.etPassword);
        View btn = this.findViewById(R.id.btLogin);
        if(btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    try {
            List<User> list=mService.getUserList();
                        LogDebug(String.valueOf(list.size()));
                        boolean flag= mService.checkAuthority("1230","123");
                        LogDebug("checkAuthority "+ String.valueOf(flag));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    logv("login....");
                    String userName = etUserName.getText().toString();
                    String password = etPassword.getText().toString();
                    logv(userName);
                    logv(password);
                    boolean result = false;
                    try {
                        result = mService.checkAuthority(userName, password);
                        result=true;//the result set true on debug
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    String msg = "login failed";
                    if (result) {
                        msg = "login success";
                        optionFragment = (OptionFragment) switchContentFragment(OptionFragment.class.getSimpleName());
                        app.setUserName(userName);
                        app.setLogined(true);
                        bottomFragment.updateDisplay();
                    }
                    Toast.makeText(v.getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


}
