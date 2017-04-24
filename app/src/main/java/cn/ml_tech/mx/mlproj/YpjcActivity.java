package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.net.Uri;
import android.os.RemoteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class YpjcActivity extends BaseActivity implements YpjcFragment.OnFragmentInteractionListener, View.OnClickListener,
        YpkFragment.OnFragmentInteractionListener, YpxxFragment.OnFragmentInteractionListener {
    YpjcFragment ypjcFragment = null;
    YpkFragment ypkFragment = null;
    YpxxFragment ypxxFragment = null;
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
            } else if (tag.equals("YpxxFragment")) {
                f = new YpxxFragment();
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
                ypkFragment.setmService(mService);
                break;
            case R.id.btAddDrug:
                logv("add drug..........");
                ypxxFragment = (YpxxFragment) switchContentFragment(YpxxFragment.class.getSimpleName());
                break;
            case R.id.btYpxxNext:
                try {
                    mService.addDrugInfo(((EditText)findViewById(R.id.etDrugName)).getText().toString(),
                            ((EditText)findViewById(R.id.etEnName)).getText().toString(),
                            ((EditText)findViewById(R.id.etPinYin)).getText().toString(),
                            1,1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }
}
