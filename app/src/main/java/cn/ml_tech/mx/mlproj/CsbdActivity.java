package cn.ml_tech.mx.mlproj;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CsbdActivity  extends BaseActivity implements CsbdFragment.OnFragmentInteractionListener, View.OnClickListener {
    CsbdFragment csbdFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_csbd);
        csbdFragment = (CsbdFragment) switchContentFragment(CsbdFragment.class.getSimpleName());

    }
    protected void onStart() {
        super.onStart();
        this.findViewById(R.id.btBack).setOnClickListener(this);
    }
    @Override
    protected Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);
        if (f == null) {
            if (tag.equals("content")) {

            } else if (tag.equals("CsbdFragment")) {
                f = new CsbdFragment();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btBack:
                CsbdActivity.this.finish();
                break;
            default:

        }
    }
}
