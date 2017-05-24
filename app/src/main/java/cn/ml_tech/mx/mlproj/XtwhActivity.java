package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;

public class XtwhActivity extends BaseActivity implements XtwhFragment.OnFragmentInteractionListener, BottomFragment.OnFragmentInteractionListener {
    XtwhFragment xtwhFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xtwhFragment = (XtwhFragment)switchContentFragment(XtwhFragment.class.getSimpleName());
        LogDebug(XtwhFragment.class.getSimpleName());
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
            }else if (tag.equals("UserManagerFragment"))
            {
                UserManagerFragment fragmentuser=new UserManagerFragment();
                f=fragmentuser;
            }else if(tag.equals("ManchineManagerFragment"))
            {
                f=new ManchineManagerFragment();
            }
            else {
                f = super.getFragment(tag);
            }
        }
        return f;
    }
}
