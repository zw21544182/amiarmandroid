package cn.ml_tech.mx.mlproj;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class YpjcActivity extends BaseActivity implements YpjcFragment.OnFragmentInteractionListener, View.OnClickListener,
        YpkFragment.OnFragmentInteractionListener {
    YpjcFragment ypjcFragment = null;
    YpkFragment ypkFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ypjcFragment = (YpjcFragment) switchContentFragment(YpjcFragment.class.getSimpleName());

    }
    @Override
    protected Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);
        if (f == null) {
            if (tag.equals("content")) {

            } else if (tag.equals("YpjcFragment")) {
                f = new YpjcFragment();
            } else if (tag.equals("YpkFragment")) {
                f = new YpkFragment();
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
    public void onStart() {
        super.onStart();
        findViewById(R.id.btPre).setOnClickListener(this);
        findViewById(R.id.btNext).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btPre:

                break;
            case R.id.btNext:
                ypkFragment = (YpkFragment) switchContentFragment(YpkFragment.class.getSimpleName());
                break;
            default:
                break;
        }
    }
}
