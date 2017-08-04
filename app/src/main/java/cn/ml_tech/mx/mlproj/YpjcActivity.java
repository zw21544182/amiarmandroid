package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import cn.ml_tech.mx.mlproj.util.CommonUtil;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
import cn.ml_tech.mx.mlservice.DAO.DrugParam;
import cn.ml_tech.mx.mlservice.DAO.Factory;
import cn.ml_tech.mx.mlservice.DAO.P_Source;
import cn.ml_tech.mx.mlservice.DAO.Permission;
import cn.ml_tech.mx.mlservice.DrugControls;

public class YpjcActivity extends BaseActivity implements View.OnClickListener,
        View.OnTouchListener, YpjqFragment.OnFragmentInteractionListener, YpxjFragment.OnFragmentInteractionListener,
        BottomFragment.OnFragmentInteractionListener {
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
    private static final int PERMISSIONSUCESS = 47;
    private static final int PERMISSIONFAILURE = 48;
    Permission permission;
    private AmiApp amiApp;
    private boolean isPermissionSucess = false;
    private ProgressDialog progressDialog;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            switch (msg.what) {
                case PERMISSIONSUCESS:
                    isPermissionSucess = true;
                    ypjcFragment = (YpjcFragment) switchContentFragment(YpjcFragment.class.getSimpleName());
                    break;
                case PERMISSIONFAILURE:
                    showToast("权限验证失败");
                    break;
            }
        }
    };
    private List<DrugParam> drugParams = null;

    @Override
    public void doAfterGetService() {
        amiApp = (AmiApp) getApplication();
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("数据加载中....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                String url = "";
                try {
                    mService.addAudittrail(5, 5, "", CommonUtil.ENTERDRUGDETECTION);
                    for (P_Source p_source : amiApp.getP_sources()
                            ) {
                        if (p_source.getId() == 15) {
                            url = p_source.getUrl();
                            Log.d("zw", url);
                            break;
                        }
                    }
                    if (mService != null)
                        permission = mService.getPermissonByUrl(url, false);
                    handler.sendEmptyMessage(PERMISSIONSUCESS);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(PERMISSIONFAILURE);

                }
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        data = new HashMap<>();
        super.onCreate(savedInstanceState);
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
                if (ypkFragment.getPermissionById(10, 8)) {
                    detectionReport.setDruginfo_id(drugControl.getId());
                    detectionReport.setDrugName(drugControl.getDrugName());
                    detectionReport.setFactoryName(drugControl.getDrugFactory());
                    YpjcjFragment = (YpjcjFragment) switchContentFragment(YpjcjFragment.class.getSimpleName());

                } else {
                    showToast("拒绝访问");
                }
                break;
            case R.id.bt_back:
                ypxxFragment = (YpxxFragment) switchContentFragment(YpxxFragment.class.getSimpleName());
                ypxxFragment.setmService(mService);
                break;
            case R.id.btPre:
                this.finish();
                break;
            case R.id.btNext:
                try {
                    if ((!ypjcFragment.isContinue()) || (null == mService.getLastReport())) {
                        ypkFragment = (YpkFragment) switchContentFragment(YpkFragment.class.getSimpleName());
                        ypkFragment.setmService(mService);
                    } else {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    mService.addAudittrail(4, 1, mService.getLastReport().getId() + "", CommonUtil.CONTINUINGDETECTION);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        ypjccFragment = (YpjccFragment) switchContentFragment(YpjccFragment.class.getSimpleName());
                        ypjccFragment.setState("continue");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
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
                if (ypxaFragment.getPermissionById(12, 3)) {
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
                } else {
                    showToast("拒绝访问");
                }
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
                ypjcFragment.setPermission(permission);
                if (!ypjcFragment.getPermissionById(8, 3)) {
                    showToast("拒绝访问");
                }
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
                break;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    mService.addAudittrail(5, 1, "", CommonUtil.EXITDRUGDETECTION);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
