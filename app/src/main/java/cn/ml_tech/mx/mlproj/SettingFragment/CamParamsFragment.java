package cn.ml_tech.mx.mlproj.SettingFragment;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.BaseFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.CameraParams;

/**
 * 创建时间: 2017/6/28
 * 创建人: zhongwang
 * 功能描述: 相机参数fragment
 */

public class CamParamsFragment extends BaseFragment implements View.OnClickListener {
    private List<CameraParams> listConfig = new ArrayList<>();
    private RadioGroup agc;
    private RadioButton agcon;
    private RadioButton agcoff;
    private RadioGroup flassh;
    private RadioButton flashon;
    private RadioButton flashff;
    private RadioGroup aec;
    private RadioButton aecon;
    private RadioButton aecoff;
    private RadioGroup fpga;
    private EditText etexposuretime;
    private Button btExTimeWrite;
    private Button btExTimeRead;
    private RadioGroup fpgaf;
    private RadioButton filteron;
    private RadioButton filteroff;
    private EditText etYAddrEnd;
    private Button btYAddrWrite;
    private Button btYAddrRead;
    private EditText etXAddrEnd;
    private Button btXAddrWrite;
    private Button btXAddrRead;
    private EditText etNumPlus;
    private Button btNumPlusWrite;
    private Button btNumPlusRead;
    private RadioGroup analogplus;
    private RadioButton analogplus1;
    private RadioButton analogplus2;
    private RadioButton analogplus3;
    private RadioButton analogplus4;
    private EditText etSensortem;
    private Button btSensorRead;
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_camparam, null);
        initFindViewById(view);
        return view;
    }
    @Override
    protected void initEvent() {
        super.initEvent();
        btExTimeWrite.setOnClickListener(this);
        btXAddrWrite.setOnClickListener(this);
        btYAddrWrite.setOnClickListener(this);
        btExTimeWrite.setOnClickListener(this);
        btNumPlusWrite.setOnClickListener(this);

    }
    @Override
    public void initFindViewById(View view) {
        agc = (RadioGroup) view.findViewById(R.id.agc);
        agcon = (RadioButton) view.findViewById(R.id.agcon);
        agcoff = (RadioButton) view.findViewById(R.id.agcoff);
        flassh = (RadioGroup) view.findViewById(R.id.flassh);
        flashon = (RadioButton) view.findViewById(R.id.flashon);
        flashff = (RadioButton) view.findViewById(R.id.flashff);
        aec = (RadioGroup) view.findViewById(R.id.aec);
        aecon = (RadioButton) view.findViewById(R.id.aecon);
        aecoff = (RadioButton) view.findViewById(R.id.aecoff);
        fpga = (RadioGroup) view.findViewById(R.id.fpga);
        etexposuretime = (EditText) view.findViewById(R.id.etexposuretime);
        btExTimeWrite = (Button) view.findViewById(R.id.btExTimeWrite);
        btExTimeRead = (Button) view.findViewById(R.id.btExTimeRead);
        fpgaf = (RadioGroup) view.findViewById(R.id.fpgaf);
        filteron = (RadioButton) view.findViewById(R.id.filteron);
        filteroff = (RadioButton) view.findViewById(R.id.filteroff);
        etYAddrEnd = (EditText) view.findViewById(R.id.etYAddrEnd);
        btYAddrWrite = (Button) view.findViewById(R.id.btYAddrWrite);
        btYAddrRead = (Button) view.findViewById(R.id.btYAddrRead);
        etXAddrEnd = (EditText) view.findViewById(R.id.etXAddrEnd);
        btXAddrWrite = (Button) view.findViewById(R.id.btXAddrWrite);
        btXAddrRead = (Button) view.findViewById(R.id.btXAddrRead);
        etNumPlus = (EditText) view.findViewById(R.id.etNumPlus);
        btNumPlusWrite = (Button) view.findViewById(R.id.btNumPlusWrite);
        btNumPlusRead = (Button) view.findViewById(R.id.btNumPlusRead);
        analogplus = (RadioGroup) view.findViewById(R.id.analogplus);
        analogplus1 = (RadioButton) view.findViewById(R.id.analogplus1);
        analogplus2 = (RadioButton) view.findViewById(R.id.analogplus2);
        analogplus3 = (RadioButton) view.findViewById(R.id.analogplus3);
        analogplus4 = (RadioButton) view.findViewById(R.id.analogplus4);
        etSensortem = (EditText) view.findViewById(R.id.etSensortem);
        btSensorRead = (Button) view.findViewById(R.id.btSensorRead);
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        loadData();
    }
    private void loadData() {
        try {
            listConfig = mActivity.getmService().getCameraParams();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (CameraParams config : listConfig
                ) {
            switch (config.getParamName()) {
                case "x_addr_end":
                    etXAddrEnd.setText(config.getParamValue() + "");
                    break;
                case "y_addr_end":
                    etYAddrEnd.setText(config.getParamValue() + "");
                    break;
                case "Exposure":
                    etexposuretime.setText(config.getParamValue() + "");
                    break;
                case "fpgaGain":
                    break;
                case "digitalGain":
                    etNumPlus.setText(config.getParamValue() + "");
                    break;
                case "globalGain":
                    break;
                case "fpgaFilter":
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void onClick(View v) {
        saveData(v.getId());
    }
    private void saveData(int id) {
        try {
            CameraParams cameraParams = new CameraParams();
            switch (id) {
                case R.id.btXAddrWrite:
                    cameraParams.setParamName("x_addr_end");
                    cameraParams.setParamValue(Double.valueOf(etXAddrEnd.getEditableText().toString()));
                    break;
                case R.id.btYAddrWrite:
                    cameraParams.setParamName("y_addr_end");
                    cameraParams.setParamValue(Double.valueOf(etYAddrEnd.getEditableText().toString()));
                    break;
                case R.id.btExTimeWrite:
                    cameraParams.setParamName("Exposure");
                    cameraParams.setParamValue(Double.valueOf(etexposuretime.getEditableText().toString()));
                    break;
                case R.id.btNumPlusWrite:
                    cameraParams.setParamName("digitalGain");
                    cameraParams.setParamValue(Double.valueOf(etNumPlus.getEditableText().toString()));
                    break;
            }

            if (mActivity.getmService().setCameraParam(cameraParams) > 0) {
                showToast("保存成功");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NumberFormatException
                e) {
        }
    }
}
