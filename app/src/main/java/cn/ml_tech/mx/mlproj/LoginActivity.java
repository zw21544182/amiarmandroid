package cn.ml_tech.mx.mlproj;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements LoginFragment.OnFragmentInteractionListener,OptionFragment.OnFragmentInteractionListener {

    private LoginFragment loginFragment = null;
    private OptionFragment optionFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginFragment = (LoginFragment) switchContentFragment(LoginFragment.class.getSimpleName());

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);
        if (f == null) {
            if (tag.equals("content")) {
//		  		programframe = new ProgrammeFragment();
//		  		f = programframe;
            } else if (tag.equals("right_toolbar")) {
                //f = new RightToolbarFragment();
            } else if (tag.equals("left_toolbar")) {
                //f = new LeftToolbarFragment();
            } else if (tag.equals("bottom_toolbar")) {
                //f = new ProgramToolbar();
            } else if (tag.equals("LoginFragment")) {
                f = new LoginFragment();
            } else if (tag.equals("ProgramToolbar")) {
                //f = new ProgramToolbar();
            } else if (tag.equals("ProcessContent")) {
                //f = new ProcessContent();
            } else if (tag.equals("ProcessToolbar")) {
                //f = new ProcessToolbar();
            } else if (tag.equals("OptionFragment")) {
                f = new OptionFragment();
            } else if (tag.equals("AboutBottomToolbarFragment")) {
                //f = new AboutBottomToolbarFragment();
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
                    logv("login....");
                    String userName = etUserName.getText().toString();
                    String password = etPassword.getText().toString();
                    logv(userName);
                    logv(password);
                    boolean result = false;
                    try {
                        result = mService.checkAuthority(userName, password);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    String msg = "login failed";
                    if (result) {
                        msg = "login success";
                        optionFragment = (OptionFragment) switchContentFragment(OptionFragment.class.getSimpleName());
                    }
                    Toast.makeText(v.getContext(), msg, Toast.LENGTH_SHORT).show();


                }
            });
        }


    }


}
