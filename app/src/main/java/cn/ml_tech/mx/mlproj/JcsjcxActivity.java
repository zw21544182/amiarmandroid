package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class JcsjcxActivity extends BaseActivity implements JcsjcxFragment.OnFragmentInteractionListener,
        BottomFragment.OnFragmentInteractionListener, View.OnClickListener {
    JcsjcxFragment jcsjcxFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jcsjcxFragment = (JcsjcxFragment)switchContentFragment(JcsjcxFragment.class.getSimpleName());
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
        findViewById(R.id.btBack).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btBack:
                JcsjcxActivity.this.finish();
                break;
            default:
                break;
        }
    }
}
