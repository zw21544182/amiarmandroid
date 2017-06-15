package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JcsjcxActivity extends BaseActivity implements
        BottomFragment.OnFragmentInteractionListener, View.OnClickListener {
    JcsjcxFragment jcsjcxFragment = null;
    private Button btnBack;


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
        switchTopFragment("");//hiden powerbutton on this Activity
        btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btBack:
               if(jcsjcxFragment.isReportLayout())
                JcsjcxActivity.this.finish();
                else jcsjcxFragment.ShowReport();
                break;
            default:
                break;
        }
    }
}
