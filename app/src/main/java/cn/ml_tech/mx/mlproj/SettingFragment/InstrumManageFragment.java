package cn.ml_tech.mx.mlproj.SettingFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.BaseFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.util.CommonUtil;
import cn.ml_tech.mx.mlservice.DAO.DevParam;

/**
 * 创建时间: 2017/6/30
 * 创建人: zhongwang
 * 功能描述:仪器管理Fragment
 */

public class InstrumManageFragment extends BaseFragment implements View.OnClickListener {
    private Button btnReadTray;
    private Button btnLaserSwitch;
    private Button btnReset;
    private Button btnRelease;
    private Button btnSaveParam;
    private EditText etSendMotorSpeed;
    private EditText etSendMotorOffsetLocation;
    private TextView txtSendMotorState;
    private Button btnSendMotorStart;
    private Button btnSendMotorWrite;
    private EditText etMachineHandMotorSpeed;
    private EditText etMachineHandMotorCatchLocation;
    private EditText etMachineHandMotorDetLocation;
    private EditText etMachineHandMotorWaitLocation;
    private EditText etMachineHandMotorOutPutLocation;
    private TextView txtMachineHandMotorState;
    private Button btnMachineHandMotorStart;
    private Button btnMachineHandMotorWrite;
    private EditText etCatchMaxDiatance;
    private EditText etCatchPutterDistance;
    private EditText etCatchOpenDistance;
    private Button btnCatchCal;
    private EditText etCatchSpeed;
    private EditText etCatchParam2;
    private EditText etCatchDelayTime;
    private TextView txtCatchRunState;
    private Button btnCatchMotorStart;
    private Button btnCatchMotorWrite;
    private EditText etCatchParam1;
    private EditText etPressedSpeed;
    private EditText etPressedParam1;
    private TextView txtPressedRunState;
    private Button btnPressedMotorStart;
    private Button btnPressedMotorWrite;
    private EditText etShadeMotorSpeed;
    private EditText etShadeMotorParam;

    private Button btnShadeMotorParam;
    private Button btnShadeMotorStart;
    private Button btnShadeMotorWrite;
    private EditText etRotateMotorSpeed;
    private TextView txtShadeRunState;
    private Button btnRotateMotorStart;
    private Button btnRotateMotorStop;
    private EditText etOutPut10mlStandardParam1;
    private EditText etOutPut10mlStandardParam2;
    private EditText etOutPut10mlStandardParamDiameter;
    private Button btnOutPutCal;
    private EditText etOutPut10mlStandardSpeed;
    private EditText etOutPutParam1;
    private EditText etOutPutParam2;
    private TextView txtOutPutRunState;
    private Button btnOutPutStart;
    private Button btnOutPutWrite;
    private Button btnAutoRun;
    private EditText etAutoCount;
    private Button btnAutoSet;
    private List<DevParam> devParams;
    private List<DevParam> params;
    private DevParam devParam;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CommonUtil.SUCESS:
                    addDataToView(devParams);
                    break;
                case CommonUtil.FAILURE:
                    showToast("加载数据失败");
                    break;
            }
        }


    };


    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_instrummanage, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        btnReadTray = (Button) view.findViewById(R.id.btnReadTray);
        btnLaserSwitch = (Button) view.findViewById(R.id.btnLaserSwitch);
        btnReset = (Button) view.findViewById(R.id.btnReset);
        btnRelease = (Button) view.findViewById(R.id.btnRelease);
        btnSaveParam = (Button) view.findViewById(R.id.btnSaveParam);
        etSendMotorSpeed = (EditText) view.findViewById(R.id.etSendMotorSpeed);
        etSendMotorOffsetLocation = (EditText) view.findViewById(R.id.etSendMotorOffsetLocation);
        txtSendMotorState = (TextView) view.findViewById(R.id.txtSendMotorState);
        btnSendMotorStart = (Button) view.findViewById(R.id.btnSendMotorStart);
        btnSendMotorWrite = (Button) view.findViewById(R.id.btnSendMotorWrite);
        etMachineHandMotorSpeed = (EditText) view.findViewById(R.id.etMachineHandMotorSpeed);
        etMachineHandMotorCatchLocation = (EditText) view.findViewById(R.id.etMachineHandMotorCatchLocation);
        etMachineHandMotorDetLocation = (EditText) view.findViewById(R.id.etMachineHandMotorDetLocation);
        etMachineHandMotorWaitLocation = (EditText) view.findViewById(R.id.etMachineHandMotorWaitLocation);
        etMachineHandMotorOutPutLocation = (EditText) view.findViewById(R.id.etMachineHandMotorOutPutLocation);
        txtMachineHandMotorState = (TextView) view.findViewById(R.id.txtMachineHandMotorState);
        btnMachineHandMotorStart = (Button) view.findViewById(R.id.btnMachineHandMotorStart);
        btnMachineHandMotorWrite = (Button) view.findViewById(R.id.btnMachineHandMotorWrite);
        etCatchMaxDiatance = (EditText) view.findViewById(R.id.etCatchMaxDiatance);
        etCatchPutterDistance = (EditText) view.findViewById(R.id.etCatchPutterDistance);
        etCatchOpenDistance = (EditText) view.findViewById(R.id.etCatchOpenDistance);
        btnCatchCal = (Button) view.findViewById(R.id.btnCatchCal);
        etCatchSpeed = (EditText) view.findViewById(R.id.etCatchSpeed);
        etCatchParam2 = (EditText) view.findViewById(R.id.etCatchParam2);
        etCatchDelayTime = (EditText) view.findViewById(R.id.etCatchDelayTime);
        txtCatchRunState = (TextView) view.findViewById(R.id.txtCatchRunState);
        btnCatchMotorStart = (Button) view.findViewById(R.id.btnCatchMotorStart);
        btnCatchMotorWrite = (Button) view.findViewById(R.id.btnCatchMotorWrite);
        etCatchParam1 = (EditText) view.findViewById(R.id.etCatchParam1);
        etPressedSpeed = (EditText) view.findViewById(R.id.etPressedSpeed);
        etPressedParam1 = (EditText) view.findViewById(R.id.etPressedParam1);
        txtPressedRunState = (TextView) view.findViewById(R.id.txtPressedRunState);
        btnPressedMotorStart = (Button) view.findViewById(R.id.btnPressedMotorStart);
        btnPressedMotorWrite = (Button) view.findViewById(R.id.btnPressedMotorWrite);
        etShadeMotorSpeed = (EditText) view.findViewById(R.id.etShadeMotorSpeed);
        etShadeMotorParam = (EditText) view.findViewById(R.id.etShadeMotorParam);
        txtShadeRunState = (TextView) view.findViewById(R.id.txtShadeRunState);
        btnShadeMotorParam = (Button) view.findViewById(R.id.btnShadeMotorParam);
        btnShadeMotorStart = (Button) view.findViewById(R.id.btnShadeMotorStart);
        btnShadeMotorWrite = (Button) view.findViewById(R.id.btnShadeMotorWrite);
        etRotateMotorSpeed = (EditText) view.findViewById(R.id.etRotateMotorSpeed);
        txtShadeRunState = (TextView) view.findViewById(R.id.txtShadeRunState);
        btnRotateMotorStart = (Button) view.findViewById(R.id.btnRotateMotorStart);
        btnRotateMotorStop = (Button) view.findViewById(R.id.btnRotateMotorStop);
        etOutPut10mlStandardParam1 = (EditText) view.findViewById(R.id.etOutPut10mlStandardParam1);
        etOutPut10mlStandardParam2 = (EditText) view.findViewById(R.id.etOutPut10mlStandardParam2);
        etOutPut10mlStandardParamDiameter = (EditText) view.findViewById(R.id.etOutPut10mlStandardParamDiameter);
        btnOutPutCal = (Button) view.findViewById(R.id.btnOutPutCal);
        etOutPut10mlStandardSpeed = (EditText) view.findViewById(R.id.etOutPut10mlStandardSpeed);
        etOutPutParam1 = (EditText) view.findViewById(R.id.etOutPutParam1);
        etOutPutParam2 = (EditText) view.findViewById(R.id.etOutPutParam2);
        txtOutPutRunState = (TextView) view.findViewById(R.id.txtOutPutRunState);
        btnOutPutStart = (Button) view.findViewById(R.id.btnOutPutStart);
        btnOutPutWrite = (Button) view.findViewById(R.id.btnOutPutWrite);
        btnAutoRun = (Button) view.findViewById(R.id.btnAutoRun);
        etAutoCount = (EditText) view.findViewById(R.id.etAutoCount);
        btnAutoSet = (Button) view.findViewById(R.id.btnAutoSet);

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnReadTray.setOnClickListener(this);
        btnLaserSwitch.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnRelease.setOnClickListener(this);
        btnSaveParam.setOnClickListener(this);
        btnSendMotorStart.setOnClickListener(this);
        btnSendMotorWrite.setOnClickListener(this);
        btnMachineHandMotorStart.setOnClickListener(this);
        btnMachineHandMotorWrite.setOnClickListener(this);
        btnCatchMotorWrite.setOnClickListener(this);
        btnPressedMotorWrite.setOnClickListener(this);
        btnShadeMotorWrite.setOnClickListener(this);
        btnRotateMotorStart.setOnClickListener(this);
        btnRotateMotorStop.setOnClickListener(this);
        btnOutPutWrite.setOnClickListener(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (params == null)
            params = new ArrayList<>();
        if (devParam == null)
            devParam = new DevParam();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    devParams = mlService.getAllDevParam();
                    handler.sendEmptyMessage(CommonUtil.SUCESS);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(CommonUtil.FAILURE);
                }
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReadTray:
                try {
                    String trayId = mlService.getTrayIcId();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnLaserSwitch:
                // TODO: 2017/8/28 打开激光
                try {
                    mlService.operateLight(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnReset:
                // TODO: 2017/8/28 关闭激光
                try {
                    mlService.operateLight(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnRelease:
                // TODO: 2017/8/28 激光复位
                break;
            case R.id.btnSaveParam:
                // TODO: 2017/8/28 保存参数
                break;
            case R.id.btnSendMotorStart:
                // TODO: 2017/8/28 启动抓瓶电机
                String sendMotorSpeed = etSendMotorSpeed.getEditableText().toString();
                String sendMotorOffsetLocation = etSendMotorOffsetLocation.getEditableText().toString();
                try {
                    mlService.operateMlMotor(CommonUtil.Device_MachineHand, 1, Double.parseDouble(sendMotorSpeed), Double.parseDouble(sendMotorOffsetLocation));
                } catch (NumberFormatException e) {
                    showToast("请输入数字");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnSendMotorWrite:
                // TODO: 2017/8/28 写入抓瓶数据
                params.clear();
                params.add(new DevParam("SendSpeed", Double.parseDouble(etSendMotorSpeed.getEditableText().toString())));
                params.add(new DevParam("SendDistance", Double.parseDouble(etSendMotorOffsetLocation.getEditableText().toString())));

                try {
                    mlService.saveAllDevParam(params);
                    initData(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnMachineHandMotorStart:
                // TODO: 2017/8/28 启动机械手
                String machineHandMotorSpeed = etMachineHandMotorSpeed.getEditableText().toString();
                String machineHandMotorCatchLocation = etMachineHandMotorCatchLocation.getEditableText().toString();
                String machineHandMotorDetLocation = etMachineHandMotorDetLocation.getEditableText().toString();
                String machineHandMotorOutPutLocation = etMachineHandMotorOutPutLocation.getEditableText().toString();
                // TODO: 2017/8/28 判断机械手状态
                try {
                    mlService.operateMlMotor(CommonUtil.Device_MachineHand, 1, Double.parseDouble(machineHandMotorSpeed), Double.parseDouble(machineHandMotorCatchLocation));
                } catch (NumberFormatException e) {
                    showToast("请输入中文数字");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnMachineHandMotorWrite:
                // TODO: 2017/8/28 写入机械手数据
                params.clear();
                params.add(new DevParam("MachineHandSpeed", Double.parseDouble(etMachineHandMotorSpeed.getEditableText().toString().trim())));
                params.add(new DevParam("MachineHandDistance1", Double.parseDouble(etMachineHandMotorCatchLocation.getEditableText().toString())));
                params.add(new DevParam("MachineHandDistance2", Double.parseDouble(etMachineHandMotorDetLocation.getEditableText().toString())));
                params.add(new DevParam("MachineHandDistance3", Double.parseDouble(etMachineHandMotorWaitLocation.getEditableText().toString())));
                params.add(new DevParam("MachineHandDistance4", Double.parseDouble(etMachineHandMotorOutPutLocation.getEditableText().toString())));
                try {
                    mlService.saveAllDevParam(params);
                    initData(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnCatchMotorWrite:
                params.clear();
                params.add(new DevParam("CatchHandMaxDistance", Double.parseDouble(etCatchMaxDiatance.getEditableText().toString().trim())));
                params.add(new DevParam("CatchHandStchDistance", Double.parseDouble(etCatchPutterDistance.getEditableText().toString())));
                params.add(new DevParam("CatchHandOpenDistance", Double.parseDouble(etCatchOpenDistance.getEditableText().toString())));
                params.add(new DevParam("CatchHandSpeed", Double.parseDouble(etCatchSpeed.getEditableText().toString())));
                params.add(new DevParam("CatchHandDistance1", Double.parseDouble(etCatchParam1.getEditableText().toString())));
                params.add(new DevParam("CatchHandDistance2", Double.parseDouble(etCatchParam2.getEditableText().toString())));
                params.add(new DevParam("CatchHandTime", Double.parseDouble(etCatchDelayTime.getEditableText().toString())));

                try {
                    mlService.saveAllDevParam(params);
                    initData(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btnPressedMotorWrite:
                params.clear();
                params.add(new DevParam("PressedSpeed", Double.parseDouble(etPressedSpeed.getEditableText().toString().trim())));
                params.add(new DevParam("PressedDistance1", Double.parseDouble(etPressedParam1.getEditableText().toString())));
                try {
                    mlService.saveAllDevParam(params);
                    initData(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btnShadeMotorWrite:
                params.clear();
                params.add(new DevParam("ShadeLightSpeed", Double.parseDouble(etShadeMotorSpeed.getEditableText().toString().trim())));
                params.add(new DevParam("ShadeLightDistance", Double.parseDouble(etShadeMotorParam.getEditableText().toString())));
                try {
                    mlService.saveAllDevParam(params);
                    initData(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btnRotateMotorStart:
                int rotateSpeed = Integer.parseInt(etRotateMotorSpeed.getEditableText().toString().trim());
                // TODO: 2017/8/31 开始旋瓶速度为 rotateSpeed
            case R.id.btnRotateMotorStop:
                // TODO: 2017/8/31 停止旋瓶
                break;
            case R.id.btnOutPutWrite:

                params.clear();
                params.add(new DevParam("OutPut10mlDistance1", Double.parseDouble(etOutPut10mlStandardParam1.getEditableText().toString().trim())));
                params.add(new DevParam("OutPut10mlDistance2", Double.parseDouble(etOutPut10mlStandardParam2.getEditableText().toString())));
                params.add(new DevParam("OutPut10mlDiameter", Double.parseDouble(etOutPut10mlStandardParamDiameter.getEditableText().toString())));
                params.add(new DevParam("OutPutSpeed", Double.parseDouble(etOutPut10mlStandardSpeed.getEditableText().toString())));
                params.add(new DevParam("OutPutDistance1", Double.parseDouble(etOutPutParam1.getEditableText().toString())));
                params.add(new DevParam("OutPutDistance2", Double.parseDouble(etOutPutParam2.getEditableText().toString())));
                try {
                    mlService.saveAllDevParam(params);
                    initData(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;

        }
    }

    private void addDataToView(List<DevParam> devParams) {
        for (DevParam devParam : devParams) {
            switch (devParam.getParamName()) {
                case "SendSpeed":
                    etSendMotorSpeed.setText(devParam.getParamValue() + "");
                    break;
                case "SendDistance":
                    etSendMotorOffsetLocation.setText(devParam.getParamValue() + "");
                    break;
                case "MachineHandSpeed":
                    etMachineHandMotorSpeed.setText(devParam.getParamValue() + "");
                    break;
                case "MachineHandDistance1":
                    etMachineHandMotorCatchLocation.setText(devParam.getParamValue() + "");
                    break;
                case "MachineHandDistance2":
                    etMachineHandMotorDetLocation.setText(devParam.getParamValue() + "");
                    break;
                case "MachineHandDistance3":
                    etMachineHandMotorWaitLocation.setText(devParam.getParamValue() + "");
                    break;
                case "MachineHandDistance4":
                    etMachineHandMotorOutPutLocation.setText(devParam.getParamValue() + "");
                    break;
                case "CatchHandMaxDistance":
                    etCatchMaxDiatance.setText(devParam.getParamValue() + "");
                    break;
                case "CatchHandStchDistance":
                    etCatchPutterDistance.setText(devParam.getParamValue() + "");
                    break;
                case "CatchHandOpenDistance":
                    etCatchOpenDistance.setText(devParam.getParamValue() + "");
                    break;
                case "CatchHandSpeed":
                    etCatchSpeed.setText(devParam.getParamValue() + "");
                    break;
                case "CatchHandTime":
                    etCatchDelayTime.setText(devParam.getParamValue() + "");
                    break;
                case "CatchHandDistance1":
                    etCatchParam1.setText(devParam.getParamValue() + "");
                    break;
                case "CatchHandDistance2":
                    etCatchParam2.setText(devParam.getParamValue() + "");
                    break;
                case "PressedSpeed":
                    etPressedSpeed.setText(devParam.getParamValue() + "");
                    break;
                case "PressedDistance1":
                    etPressedParam1.setText(devParam.getParamValue() + "");
                    break;
                case "ShadeLightSpeed":
                    etShadeMotorSpeed.setText(devParam.getParamValue() + "");
                    break;
                case "ShadeLightDistance":
                    etShadeMotorParam.setText(devParam.getParamValue() + "");
                    break;
                case "RotateSpeed":
                    etRotateMotorSpeed.setText(devParam.getParamValue() + "");
                    break;
                case "OutPut10mlDistance1":
                    etOutPut10mlStandardParam1.setText(devParam.getParamValue() + "");
                    break;
                case "OutPut10mlDistance2":
                    etOutPut10mlStandardParam2.setText(devParam.getParamValue() + "");
                    break;
                case "OutPut10mlDiameter":
                    etOutPut10mlStandardParamDiameter.setText(devParam.getParamValue() + "");
                    break;
                case "OutPutSpeed":
                    etOutPut10mlStandardSpeed.setText(devParam.getParamValue() + "");

                    break;
                case "OutPutDistance1":
                    etOutPutParam1.setText(devParam.getParamValue() + "");
                    break;
                case "OutPutDistance2":
                    etOutPutParam2.setText(devParam.getParamValue() + "");
                    break;

            }

        }
    }
}
