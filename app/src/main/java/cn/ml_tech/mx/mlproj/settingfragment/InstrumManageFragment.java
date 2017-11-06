package cn.ml_tech.mx.mlproj.settingfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.util.CommonUtil;
import cn.ml_tech.mx.mlservice.DAO.DevParam;

import static java.lang.Double.parseDouble;

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
    private Button btnShadeMotorReset;
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
    private Button btnMachineHandMotorReset;
    private Button btnCatchMotorReset;
    private TextView txtOutPutRunState;
    private Button btnOutPutStart;
    private Button btnOutPutWrite;
    private Button btnAutoRun;
    private Button btnPressedMotorReset;
    private EditText etAutoCount;
    private Button btnAutoSet;
    private List<DevParam> devParams;
    private List<DevParam> params;
    private EditText etSpeDiameter;
    private DevParam devParam;
    private int machineState = 0;
    private int catchState = 0;
    private static final int SHADEPARAM = 123;
    private static final int PRESSEDPARAM = 124;
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
                case SHADEPARAM:

                    btnShadeMotorStart.setEnabled(true);
                    txtShadeRunState.setText("遮光板运行完毕");
                    break;
                case PRESSEDPARAM:
                    txtPressedRunState.setText("压瓶调试完毕");
                    btnPressedMotorStart.setEnabled(true);
                    break;

            }
        }


    };
    private MyReceiver myReceiver;


    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_instrummanage, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        btnShadeMotorReset = (Button) view.findViewById(R.id.btnShadeMotorReset);
        btnCatchMotorReset = (Button) view.findViewById(R.id.btnCatchMotorReset);
        btnPressedMotorReset = (Button) view.findViewById(R.id.btnPressedMotorReset);
        btnMachineHandMotorReset = (Button) view.findViewById(R.id.btnMachineHandMotorReset);
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
        etSpeDiameter = (EditText) view.findViewById(R.id.etSpeDiameter);
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
        btnShadeMotorStart.setOnClickListener(this);
        btnPressedMotorStart.setOnClickListener(this);
        btnMachineHandMotorReset.setOnClickListener(this);
        btnOutPutStart.setOnClickListener(this);
        btnCatchMotorReset.setOnClickListener(this);
        btnPressedMotorReset.setOnClickListener(this);
        btnShadeMotorReset.setOnClickListener(this);
        btnAutoRun.setOnClickListener(this);
        btnCatchMotorStart.setOnClickListener(this);
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
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.trayradio");
        getActivity().registerReceiver(myReceiver, filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReadTray:
                try {
                    mlService.getTrayIcId(CommonUtil.TRAYID_RADIO);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnLaserSwitch:
                try {
                    mlService.operateLight(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnReset:
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
                    mlService.operateMlMotor(CommonUtil.Device_MachineHand, 1, parseDouble(sendMotorSpeed), (int) parseDouble(sendMotorOffsetLocation));
                } catch (NumberFormatException e) {
                    showToast("请输入数字");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnSendMotorWrite:
                // TODO: 2017/8/28 写入抓瓶数据
                params.clear();
                params.add(new DevParam("SendSpeed", parseDouble(etSendMotorSpeed.getEditableText().toString())));
                params.add(new DevParam("SendDistance", parseDouble(etSendMotorOffsetLocation.getEditableText().toString())));

                try {
                    mlService.saveAllDevParam(params);
                    initData(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnMachineHandMotorStart:
                // TODO: 2017/8/28 启动机械手
                int distance = 0;
                int dir = 1;
                //电机速度
                double machineHandMotorSpeed = parseDouble(etMachineHandMotorSpeed.getEditableText().toString().trim());
                //抓瓶位置
                double machineHandMotorCatchLocation = parseDouble(etMachineHandMotorCatchLocation.getEditableText().toString().trim());
                //检测位置
                double machineHandMotorDetLocation = parseDouble(etMachineHandMotorDetLocation.getEditableText().toString().trim());
                //等待位置
                double machineHandMotorWaitLocation = parseDouble(etMachineHandMotorWaitLocation.getEditableText().toString().trim());
                double machineHandMotorOutPutLocation = parseDouble(etMachineHandMotorOutPutLocation.getEditableText().toString());
                // TODO: 2017/8/28 判断机械手状态
                switch (machineState) {
                    case 0:
                        //机械手已经初始状态
                        dir = 1;
                        distance = (int) machineHandMotorCatchLocation;
                        machineState++;
                        break;
                    case 1:
                        //机械手已经等待状态
                        dir = 0;
                        distance = (int) (machineHandMotorDetLocation);
                        machineState++;
                        break;
                    case 2:
                        //机械手已经检测状态
                        dir = 0;
                        distance = (int) machineHandMotorWaitLocation;
                        machineState++;
                        break;
                    case 3:
                        //机械手已经抓瓶状态
                        dir = 1;
                        distance = (int) machineHandMotorWaitLocation;

                        machineState++;
                        break;
                    case 4:
                        //机械手已经检测状态
                        dir = 0;
                        distance = (int) (machineHandMotorOutPutLocation);
                        machineState++;
                        break;

                    case 5:
                        //机械手已经等待状态

                        try {
                            mlService.motorReset(CommonUtil.Device_MachineHand);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        machineState = 0;
                        return;


                }
                try {
                    mlService.operateMlMotor(CommonUtil.Device_MachineHand, dir, machineHandMotorSpeed, distance);
                } catch (NumberFormatException e) {
                    showToast("请输入中文数字");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnMachineHandMotorWrite:
                // TODO: 2017/8/28 写入机械手数据
                params.clear();
                params.add(new DevParam("MachineHandSpeed", parseDouble(etMachineHandMotorSpeed.getEditableText().toString().trim())));
                params.add(new DevParam("MachineHandDistance1", parseDouble(etMachineHandMotorCatchLocation.getEditableText().toString())));
                params.add(new DevParam("MachineHandDistance2", parseDouble(etMachineHandMotorDetLocation.getEditableText().toString())));
                params.add(new DevParam("MachineHandDistance3", parseDouble(etMachineHandMotorWaitLocation.getEditableText().toString())));
                params.add(new DevParam("MachineHandDistance4", parseDouble(etMachineHandMotorOutPutLocation.getEditableText().toString())));
                try {
                    mlService.saveAllDevParam(params);
                    initData(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnCatchMotorWrite:
                params.clear();
                params.add(new DevParam("CatchHandMaxDistance", parseDouble(etCatchMaxDiatance.getEditableText().toString().trim())));
                params.add(new DevParam("CatchHandStchDistance", parseDouble(etCatchPutterDistance.getEditableText().toString())));
                params.add(new DevParam("CatchHandOpenDistance", parseDouble(etCatchOpenDistance.getEditableText().toString())));
                params.add(new DevParam("CatchHandSpeed", parseDouble(etCatchSpeed.getEditableText().toString())));
                params.add(new DevParam("CatchHandDistance1", parseDouble(etCatchParam1.getEditableText().toString())));
                params.add(new DevParam("CatchHandDistance2", parseDouble(etCatchParam2.getEditableText().toString())));
                params.add(new DevParam("CatchHandTime", parseDouble(etCatchDelayTime.getEditableText().toString())));

                try {
                    mlService.saveAllDevParam(params);
                    initData(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btnPressedMotorWrite:
                params.clear();
                params.add(new DevParam("PressedSpeed", parseDouble(etPressedSpeed.getEditableText().toString().trim())));
                params.add(new DevParam("PressedDistance1", parseDouble(etPressedParam1.getEditableText().toString())));
                try {
                    mlService.saveAllDevParam(params);
                    initData(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btnCatchMotorStart:
                double speed = parseDouble(etCatchSpeed.getEditableText().toString().trim());
                double catchParam1 = parseDouble(etCatchParam1.getEditableText().toString().trim());
                double catchParam2 = parseDouble(etCatchParam2.getEditableText().toString().trim());
                double dirs = 0;
                double dis = 0;
                switch (catchState) {
                    case 0:
                        dirs = 1;
                        dis = catchParam2;
                        catchState++;
                        break;
                    case 1:
                        dirs = 0;
                        dis = catchParam1;
                        catchState++;
                        break;
                    case 2:
                        dirs = 0;
                        dis = catchParam2;
                        catchState++;
                        break;
                    case 3:
                        dirs = 1;
                        dis = catchParam2 - catchParam1;
                        catchState++;
                        break;
                    case 4:
                        dirs = 1;
                        dis = catchParam1;
                        catchState++;
                        break;
                    case 5:
                        dirs = 0;
                        dis = catchParam1;
                        catchState++;
                        break;
                    case 6:
                        dirs = 0;
                        dis = catchParam2;
                        catchState = 0;
                        break;
                }
                try {

                    mlService.operateMlMotor(CommonUtil.Device_CatchHand, (int) dirs, speed, (int) dis);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnShadeMotorWrite:
                params.clear();
                params.add(new DevParam("ShadeLightSpeed", parseDouble(etShadeMotorSpeed.getEditableText().toString().trim())));
                params.add(new DevParam("ShadeLightDistance", parseDouble(etShadeMotorParam.getEditableText().toString())));
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
                try {
                    mlService.rotaleBottle(rotateSpeed);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnRotateMotorStop:
                // TODO: 2017/8/31 停止旋瓶
                try {
                    mlService.rotaleBottle(0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnShadeMotorStart:
                final double shadeSpeed = Double.parseDouble(etShadeMotorSpeed.getEditableText().toString().trim());
                final double shadeParam = Double.parseDouble(etShadeMotorParam.getEditableText().toString().trim());
                btnShadeMotorStart.setEnabled(false);
                if (machineState == 3) {
                    showToast("机械手处于抓瓶状态无法使用此功能");
                    return;
                }
                txtShadeRunState.setText("遮光板运行中");
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
            case R.id.btnPressedMotorStart:
                final double pressedSpeed = Double.parseDouble(etPressedSpeed.getEditableText().toString().trim());
                final double pressedParam1 = Double.parseDouble(etPressedParam1.getEditableText().toString().trim());
                txtPressedRunState.setText("压瓶调试中");
                try {
                    mlService.operateMlMotor(CommonUtil.Device_Pressed, 1, pressedSpeed, (int) pressedParam1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnOutPutStart:
                final double outputSpeed = Double.parseDouble(etOutPut10mlStandardSpeed.getEditableText().toString());
                final double outputParam1 = Double.parseDouble(etOutPutParam1.getEditableText().toString());
                double outputParam2 = Double.parseDouble(etOutPutParam2.getEditableText().toString());
                try {
                    mlService.operateMlMotor(CommonUtil.Device_OutPut, 1, outputSpeed, (int) outputParam1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnMachineHandMotorReset:
                machineState = 0;
                try {
                    mlService.motorReset(CommonUtil.Device_MachineHand);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnPressedMotorReset:
                try {
                    mlService.motorReset(CommonUtil.Device_Pressed);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnCatchMotorReset:
                catchState = 0;
                try {
                    mlService.motorReset(CommonUtil.Device_CatchHand);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnShadeMotorReset:

                try {
                    mlService.motorReset(CommonUtil.Device_ShadeLight);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnAutoRun:
                Log.d("zw", "autoDebug");
                machineState = 0;
                String content = etAutoCount.getEditableText().toString();
                try {
                    int num = Integer.parseInt(content);
                    mlService.autoDebug(CommonUtil.AUTOEBUG_DEBUG, num);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    showToast("输入类型不合法");
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myReceiver);
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

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("xwl", "on receive");
            String info = intent.getExtras().getString("info");

            etSpeDiameter.setText(info);

        }

    }
}
