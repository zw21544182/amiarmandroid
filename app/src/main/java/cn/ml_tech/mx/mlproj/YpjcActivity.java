package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import cn.ml_tech.mx.mlservice.BottlePara;
import cn.ml_tech.mx.mlservice.DAO.Factory;

public class YpjcActivity extends BaseActivity implements YpjcFragment.OnFragmentInteractionListener, View.OnClickListener,
        View.OnTouchListener, YpxjFragment.OnFragmentInteractionListener,
        YpkFragment.OnFragmentInteractionListener, YpxxFragment.OnFragmentInteractionListener, YpxaFragment.OnFragmentInteractionListener
        , BottomFragment.OnFragmentInteractionListener {
    YpjcFragment ypjcFragment = null;
    YpkFragment ypkFragment = null;
    YpxxFragment ypxxFragment = null;
    YpxaFragment ypxaFragment = null;
    YpxjFragment ypxjFragment = null;
    BottlePara bottlePara = null;

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
                if (ypxxFragment == null) {
                    f = new YpxxFragment();
                } else {
                    f = ypxxFragment;
                }
            } else if (tag.equals("YpxaFragment")) {
                f = new YpxaFragment();
            } else if (tag.equals("YpxjFragment")) {
                f = new YpxjFragment();

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                ypxxFragment = (YpxxFragment) switchContentFragment(YpxxFragment.class.getSimpleName());
                ypxxFragment.setmService(mService);
                break;
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
                ypxxFragment.setmService(mService);
                break;
            case R.id.btYpxxNext:
                ypxjFragment = (YpxjFragment) switchContentFragment(YpxjFragment.class.getSimpleName());
                ypxjFragment.setmService(mService);


                break;
            case R.id.btYpxxAddFactory:
                ypxaFragment = (YpxaFragment) switchContentFragment(YpxaFragment.class.getSimpleName());

                break;
            case R.id.btnSaveFactory:
                Factory factory = ypxaFragment.getFactory();
                try {
                    if (factory != null) {
                        mService.addFactory(factory.getName(), factory.getAddress(), factory.getPhone(), factory.getFax(), factory.getMail(), factory.getContactName(), factory.getContactPhone(), factory.getWebSite(), factory.getProvince_code(), factory.getCity_code(), factory.getArea_code());

                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                ypxxFragment = (YpxxFragment) switchContentFragment(YpxxFragment.class.getSimpleName());
                ypxxFragment.setmService(mService);
                break;
            case R.id.btnypxjPre:
                ypxxFragment = (YpxxFragment) switchContentFragment(YpxxFragment.class.getSimpleName());
                break;
            case R.id.btnypxjNext:
                bottlePara = ypxjFragment.getBottlePara();
                try {
                    mService.saveBottlePara(bottlePara);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "保存参数失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            ypxaFragment.showWindow();
        }
        return false;
    }
}
