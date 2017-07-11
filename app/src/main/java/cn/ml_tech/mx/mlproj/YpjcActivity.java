package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
import cn.ml_tech.mx.mlservice.DAO.DrugParam;
import cn.ml_tech.mx.mlservice.DAO.Factory;
import cn.ml_tech.mx.mlservice.DrugControls;

public class YpjcActivity extends BaseActivity implements YpjcFragment.OnFragmentInteractionListener, View.OnClickListener,
        View.OnTouchListener, YpjqFragment.OnFragmentInteractionListener, YpxjFragment.OnFragmentInteractionListener,
        YpkFragment.OnFragmentInteractionListener, YpxxFragment.OnFragmentInteractionListener, YpxaFragment.OnFragmentInteractionListener
        , YpjccFragment.OnFragmentInteractionListener, YpjcjFragment.OnFragmentInteractionListener, BottomFragment.OnFragmentInteractionListener {
    YpjcFragment ypjcFragment = null;
    YpkFragment ypkFragment = null;
    YpxxFragment ypxxFragment = null;
    YpxaFragment ypxaFragment = null;
    YpxjFragment ypxjFragment = null;
    YpjqFragment ypjqFragment = null;
    YpjcjFragment YpjcjFragment = null;
    YpjccFragment ypjccFragment = null;
    String name = "";
    String enName = "";
    String pinyin = "";
    int factoryid;
    int containnerid;
    Map<String, String> data;
    public int pos, druginfo_id;
    public DrugControls drugControl = new DrugControls();
    public DetectionReport detectionReport = new DetectionReport();


    private List<DrugParam> drugParams = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        data = new HashMap<>();
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
            } else if (tag.equals("YpjccFragment")) {
                f = new YpjccFragment();
            } else if (tag.equals("YpxjFragment")) {
                if (ypxjFragment == null) {
                    f = new YpxjFragment();
                } else f = ypxjFragment;
            } else if (tag.equals("YpjqFragment")) {
                f = new YpjqFragment();
            } else if (tag.equals("YpjcjFragment")) {
                f = new YpjcjFragment();
            } else {
                f = super.getFragment(tag);
            }
        }
        return f;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void moveToMainFragment() {
        ypkFragment = (YpkFragment) switchContentFragment(YpkFragment.class.getSimpleName());
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
            case R.id.ypjcjNext:
                String detecitonBatch = YpjcjFragment.getDetectionBatch();
                String detectionCount = YpjcjFragment.getDetectionCount();
                String detectionNumber = YpjcjFragment.getDetectionNumber();
                if (TextUtils.isEmpty(detecitonBatch) || TextUtils.isEmpty(detectionCount) || TextUtils.isEmpty(detectionNumber)) {
                    showToast("请将信息填写完整");
                    return;
                }
                if (Integer.parseInt(detectionCount) > 20) {
                    showToast("检测数量不能大于20");
                    return;
                }
                detectionReport.setDetectionBatch(detecitonBatch);
                detectionReport.setDetectionCount(Integer.parseInt(detectionCount));
                detectionReport.setDetectionNumber(detectionNumber);
                ypjccFragment = (YpjccFragment) switchContentFragment(YpjccFragment.class.getSimpleName());
                break;
            case R.id.btnypxNext:
                detectionReport.setDruginfo_id(drugControl.getId());
                detectionReport.setDrugName(drugControl.getDrugName());
                detectionReport.setFactoryName(drugControl.getDrugFactory());
                YpjcjFragment = (YpjcjFragment) switchContentFragment(YpjcjFragment.class.getSimpleName());
                break;
            case R.id.bt_back:
                ypxxFragment = (YpxxFragment) switchContentFragment(YpxxFragment.class.getSimpleName());
                ypxxFragment.setmService(mService);
                break;
            case R.id.btPre:
                this.finish();
                break;
            case R.id.btNext:
                if (!ypjcFragment.isContinue()) {
                    ypkFragment = (YpkFragment) switchContentFragment(YpkFragment.class.getSimpleName());
                    ypkFragment.setmService(mService);
                } else {
                    ypjccFragment = (YpjccFragment) switchContentFragment(YpjccFragment.class.getSimpleName());
                    ypjccFragment.setState("continue");
                }
                break;
            case R.id.addphonetic:
                druginfo_id = 0;
                moveToAddDrug();
                break;
            case R.id.btYpxxNext:
                name = ypxxFragment.getName();
                pinyin = ypxxFragment.getPinyin();
                enName = ypxxFragment.getEnname();
                factoryid = ypxxFragment.getFactoryId();
                containnerid = ypxxFragment.getSpecificationTypeId();
                ypxjFragment = (YpxjFragment) switchContentFragment(YpxjFragment.class.getSimpleName());
                ypxjFragment.setmService(mService);
                pos = ((Spinner) findViewById(R.id.etBottleType)).getSelectedItemPosition();
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
                data = ypxjFragment.getData();
                Log.d("zw", data.size() + "datasize");
                ypjqFragment = (YpjqFragment) switchContentFragment(YpjqFragment.class.getSimpleName());
                break;
            case R.id.btSave:
                try {
                    data.putAll(ypjqFragment.getData());
                    Log.d("zw", "drugid" + druginfo_id);
                    mService.addDrugInfo(name, enName, pinyin, containnerid, factoryid, String.valueOf(druginfo_id));
                    saveDrugParams(data, druginfo_id);
                    Toast.makeText(this, data.size() + "size", Toast.LENGTH_SHORT).show();
                    showToast("保存成功");
                    ypkFragment = (YpkFragment) switchContentFragment(YpkFragment.class.getSimpleName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    showToast("保存失败");
                }
                break;
            case R.id.query:
                String name = ypkFragment.getDrugName();
                String pinyin = ypkFragment.getPinyin();
                String enname = ypkFragment.getEnName();
                try {
                    int page = -1;
                    logv("mmp" + page);
                    List<DrugControls> drugControlses = mService.queryDrugControlByInfo(name, pinyin, enname, page);
                    ypkFragment.setDataToView(drugControlses, true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btYpxxPre:
                ypkFragment = (YpkFragment) switchContentFragment(YpkFragment.class.getSimpleName());
                break;
            case R.id.reseting:
                ypkFragment.resetting();
                List<DrugControls> drugControlses = null;
                try {
                    drugControlses = mService.queryDrugControl();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                ypkFragment.setDataToView(drugControlses, true);
                break;
            case R.id.ibPre:
                try {
                    ypkFragment.setPreDataToView();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ibNext:
                try {
                    ypkFragment.setNexTDataToView();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ibSearch:
                try {
                    ypkFragment.setSearchDataToView();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            default:
                break;
        }
    }

    public void logMap(Map<String, String> data) {
        Iterator iter = data.entrySet().iterator();

        Map.Entry entry = (Map.Entry) iter.next();
        while (iter.hasNext()) {
            Map.Entry s = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            Log.d("zw", "key" + key + " val" + val);
        }
    }

    private void saveDrugParams(Map<String, String> map, int id) throws RemoteException {
        List<DrugParam> drugParams = new ArrayList<>();
        String drug_id = "";
        if (id == 0) {
            List<DrugControls> drugControlses = mService.queryDrugControl();
            drug_id = drugControlses.get(drugControlses.size() - 1).getId() + "";
        } else {
            drug_id = id + "";

        }
        for (String key : data.keySet()
                ) {
            DrugParam drugParam = new DrugParam();
            drugParam.setDruginfo_id(Long.parseLong(drug_id));
            drugParam.setParamname(key);
            drugParam.setParamvalue(Double.parseDouble(data.get(key)));
            drugParams.add(drugParam);
        }
        mService.setDrugParamList(drugParams);
    }

    public void moveToAddDrug() {
        ypxxFragment = (YpxxFragment) switchContentFragment(YpxxFragment.class.getSimpleName());
        ypxxFragment.setmService(mService);

    }

    public List<DrugParam> getDrugParams() throws RemoteException {
        Log.d("zw", "druginfo_id " + druginfo_id);
        drugParams = mService.getDrugParamById(druginfo_id);

        return drugParams;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            ypxaFragment.showWindow();
        }
        return false;
    }
}
