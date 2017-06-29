package cn.ml_tech.mx.mlproj.SettingFragment;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ml_tech.mx.mlproj.BaseFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.SystemConfig;

/**
 * Created by ml on 2017/6/9.
 */

public class SysConfigFragment extends BaseFragment implements View.OnClickListener {

    private EditText etStandRotateNum;
    private EditText etFloatMaxCount;
    private EditText etFiberMaxCount;
    private EditText etGlassPrecentMax;
    private EditText etGlassTimeMax;
    private EditText etDiskPrecentMax;
    private EditText etIpAddress;
    private EditText etNetMask;
    private EditText etGateWay;
    private Button btnApply;
    private List<SystemConfig> listConfig = new ArrayList<SystemConfig>();
    private Map<String, String> mapConfig = new HashMap<String, String>();

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_systemconfig, null);
        initFindViewById(view);
        return view;
    }

    @Override
    protected void initEvent() {
        btnApply.setOnClickListener(this);
    }

    private boolean CheckInfoComplete() {
        boolean isComplete = true;
        if (TextUtils.isEmpty(etStandRotateNum.getText().toString())
                || TextUtils.isEmpty(etFloatMaxCount.getText().toString())
                || TextUtils.isEmpty(etFiberMaxCount.getText().toString())
                || TextUtils.isEmpty(etGlassPrecentMax.getText().toString())
                || TextUtils.isEmpty(etGlassTimeMax.getText().toString())
                || TextUtils.isEmpty(etDiskPrecentMax.getText().toString())
                || TextUtils.isEmpty(etIpAddress.getText().toString())
                || TextUtils.isEmpty(etNetMask.getText().toString())
                || TextUtils.isEmpty(etGateWay.getText().toString()))
            isComplete = false;
        return isComplete;
    }

    @Override
    public void initFindViewById(View view) {
        etStandRotateNum = (EditText) view.findViewById(R.id.etStandRotateNum);
        etFloatMaxCount = (EditText) view.findViewById(R.id.etFloatMaxCount);
        etFiberMaxCount = (EditText) view.findViewById(R.id.etFiberMaxCount);
        etGlassPrecentMax = (EditText) view.findViewById(R.id.etGlassPrecentMax);
        etGlassTimeMax = (EditText) view.findViewById(R.id.etGlassTimeMax);
        etDiskPrecentMax = (EditText) view.findViewById(R.id.etDiskPrecentMax);
        etIpAddress = (EditText) view.findViewById(R.id.etIpAddress);
        etNetMask = (EditText) view.findViewById(R.id.etNetMask);
        etGateWay = (EditText) view.findViewById(R.id.etGateWay);
        btnApply = (Button) view.findViewById(R.id.btnApply);
    }

    private void LoadData() {
        try {
            listConfig = mActivity.getmService().getSystemConfig();
            Log.d("LoadData: ", String.valueOf(listConfig.size()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (SystemConfig config : listConfig
                ) {
            switch (config.getParamName()) {
                case "StandardDrugRotateCount":
                    etStandRotateNum.setText(config.getParamValue());
                    mapConfig.put(config.getParamName(), config.getParamValue());
                    break;
                case "FloatMax":
                    etFloatMaxCount.setText(String.valueOf(config.getParamValue()));
                    mapConfig.put(config.getParamName(), config.getParamValue());
                    break;
                case "FiberMax":
                    etFiberMaxCount.setText(String.valueOf(config.getParamValue()));
                    mapConfig.put(config.getParamName(), config.getParamValue());
                    break;
                case "GlassCountMax":
                    etGlassPrecentMax.setText(String.valueOf(config.getParamValue()));
                    mapConfig.put(config.getParamName(), config.getParamValue());
                    break;
                case "GlassTimeMax":
                    etGlassTimeMax.setText(String.valueOf(config.getParamValue()));
                    mapConfig.put(config.getParamName(), config.getParamValue());
                    break;
                case "DiskUsedMax":
                    etDiskPrecentMax.setText(String.valueOf(config.getParamValue()));
                    mapConfig.put(config.getParamName(), config.getParamValue());
                    break;
                case "IpAddress":
                    etIpAddress.setText(String.valueOf(config.getParamValue()));
                    mapConfig.put(config.getParamName(), config.getParamValue());
                    break;
                case "NetMask":
                    etNetMask.setText(String.valueOf(config.getParamValue()));
                    mapConfig.put(config.getParamName(), config.getParamValue());
                    break;
                case "GateWay":
                    etGateWay.setText(String.valueOf(config.getParamValue()));
                    mapConfig.put(config.getParamName(), config.getParamValue());
                    break;
                default:
                    break;
            }
        }
    }

    private void SaveData() {
        if (!CheckInfoComplete()) {
            Toast.makeText(mActivity, "请检查信息是否填写完整 ", Toast.LENGTH_SHORT).show();
            return;
        }

        List<SystemConfig> list = new ArrayList<SystemConfig>();
        mapConfig.put("StandardDrugRotateCount", etStandRotateNum.getText().toString());
        mapConfig.put("FloatMax", etFloatMaxCount.getText().toString());
        mapConfig.put("FiberMax", etFiberMaxCount.getText().toString());
        mapConfig.put("GlassCountMax", etGlassPrecentMax.getText().toString());
        mapConfig.put("GlassTimeMax", etGlassTimeMax.getText().toString());
        mapConfig.put("DiskUsedMax", etDiskPrecentMax.getText().toString());
        mapConfig.put("IpAddress", etIpAddress.getText().toString());
        mapConfig.put("NetMask", etNetMask.getText().toString());
        mapConfig.put("GateWay", etGateWay.getText().toString());
        for (String key : mapConfig.keySet()
                ) {
            SystemConfig config = new SystemConfig();
            config.setParamName(key);
            config.setParamValue(mapConfig.get(key));
            list.add(config);
        }
        listConfig = list;
        int count = mapConfig.size();
        Log.d("SaveData: ", String.valueOf(listConfig.size()));
        Log.d("SaveData: mapConfigSize", String.valueOf(mapConfig.size()));
        try {
            int success = mActivity.getmService().setSystemConfig(listConfig);
            if (success >= count)
                Toast.makeText(mActivity, "保存成功", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity, String.format("总项目%d条,成功保存%d条", count, success), Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        LoadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnApply:
                SaveData();
                break;
            default:
                break;
        }
    }
}
