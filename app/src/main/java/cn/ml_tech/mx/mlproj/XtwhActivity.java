package cn.ml_tech.mx.mlproj;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class XtwhActivity extends BaseActivity implements XtwhFragment.OnFragmentInteractionListener {
    XtwhFragment xtwhFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xtwhFragment = (XtwhFragment)switchContentFragment(XtwhFragment.class.getSimpleName());
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
            } else {
                f = super.getFragment(tag);
            }
        }
        return f;
    }
}
