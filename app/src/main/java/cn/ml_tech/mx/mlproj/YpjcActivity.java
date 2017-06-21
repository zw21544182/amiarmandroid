package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.jeesoft.widget.pickerview.CharacterPickerWindow;

public class YpjcActivity extends BaseActivity implements YpjcFragment.OnFragmentInteractionListener, View.OnClickListener,
        View.OnTouchListener,
        YpkFragment.OnFragmentInteractionListener, YpxxFragment.OnFragmentInteractionListener, YpxaFragment.OnFragmentInteractionListener
        , BottomFragment.OnFragmentInteractionListener {
    YpjcFragment ypjcFragment = null;
    YpkFragment ypkFragment = null;
    YpxxFragment ypxxFragment = null;
    YpxaFragment ypxaFragment = null;

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
            } else if (tag.equals("YpxaFragment")) {
                f = new YpxaFragment();

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
        switchTopFragment("");//hiden powerbutton on this Activity
        findViewById(R.id.btPre).setOnClickListener(this);
        findViewById(R.id.btNext).setOnClickListener(this);
    }

    private void showWindow() {

        final CharacterPickerWindow window = OptionsWindowHelper.builder(this, new OptionsWindowHelper.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(String province, String city, String area) {
                ((TextView) findViewById(R.id.etMachineFactoryAddr)).setText(province + city + area);
            }
        });
        window.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btPre:
                this.finish();
                break;
            case R.id.btNext:
                ypkFragment = (YpkFragment) switchContentFragment(YpkFragment.class.getSimpleName());
                ypkFragment.setmService(mService);
                break;
            case R.id.addphonetic:
                logv("add drug..........");
                ypxxFragment = (YpxxFragment) switchContentFragment(YpxxFragment.class.getSimpleName());
                break;
            case R.id.btYpxxNext:
                try {
                    mService.addDrugInfo(((EditText) findViewById(R.id.etDrugName)).getText().toString(),
                            ((EditText) findViewById(R.id.etEnName)).getText().toString(),
                            ((EditText) findViewById(R.id.etPinYin)).getText().toString(),
                            1, 1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btYpxxAddFactory:
                ypxaFragment = (YpxaFragment) switchContentFragment(YpxaFragment.class.getSimpleName());

                break;
            case R.id.etMachineFactoryAddr:
                showWindow();
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            showWindow();
        }
        return false;
    }
}
