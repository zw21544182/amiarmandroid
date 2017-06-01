package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ml on 2017/5/25.
 */

public class DeviceDebugFragment extends Fragment implements View.OnClickListener {
    private Button btnReadTray;
    private Button btnLaserSwitch;
    private Button btnMotorReset;
    private Button btnMotorLockSwitch;
    private Button btnParamSave;
    private Button btnSendMotorStart;
    private Button btnSendMotorWrite;
    private Button btnMachineHandMotorStart;
    private Button btnMachineHandMotorWrite;
    private Button btnCatchCal;
    private Button btnCatchMotorStart;
    private Button btnCatchMotorWrite;
    private Button btnPressedMotorStart;
    private Button btnPressedMotorWrite;
    private Button btnShadeMotorParam;
    private Button btnShadeMotorStart;
    private Button btnShadeMotorWrite;
    private Button btnRotateMotorStart;
    private Button btnRotateMotorStop;
    private Button btnOutPutCal;
    private Button btnOutPutStart;
    private Button btnOutPutWrite;
    private Button btnAutoRun;
    private Button btnAutoSet;
    private EditText etDebugDiameter;
    private EditText etSendMotorSpeed;
    private EditText etSendMotorOffsetLocation;
    private EditText etMachineHandMotorCatchLocation;
    private EditText etMachineHandMotorDetLocation;
    private EditText etMachineHandMotorOutPutLocation;
    private EditText etMachineHandMotorSpeed;
    private EditText etMachineHandMotorWaitLocation;
    private EditText etCatchDelayTime;
    private EditText etCatchMaxDiatance;
    private EditText etCatchOpenDistance;
    private EditText etCatchParam1;
    private EditText etCatchParam2;
    private EditText etCatchPutterDistance;
    private EditText etCatchSpeed;
    private EditText etPressedParam1;
    private EditText etPressedSpeed;
    private EditText etShadeMotorParam;
    private EditText etShadeMotorSpeed;
    private EditText etRotateMotorSpeed;
    private EditText etOutPut10mlStandardParam1;
    private EditText etOutPut10mlStandardParam2;
    private EditText etOutPut10mlStandardParamDiameter;
    private EditText etOutPut10mlStandardSpeed;
    private EditText etOutPutParam1;
    private EditText etOutPutParam2;
    private EditText etAutoCount;
    private TextView txtSendMotorState;
    private TextView txtMachineHandMotorState;
    private TextView txtCatchRunState;
    private TextView txtOutPutRunState;
    private TextView txtPressedRunState;
    private View viewinflate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewinflate = inflater.inflate(R.layout.fragment_devicedebug,container,false);
        initView();
        initButtonClick();
        return viewinflate;
    }
    private  void initView()
    {
        btnReadTray = (Button) viewinflate.findViewById(R.id.btnReadTray);
        btnLaserSwitch = (Button) viewinflate.findViewById(R.id.btnLaserSwitch);
        btnMotorReset = (Button) viewinflate.findViewById(R.id.btnMotorReset);
        btnMotorLockSwitch = (Button) viewinflate.findViewById(R.id.btnMotorLockSwitch);
        btnParamSave = (Button) viewinflate.findViewById(R.id.btnParamSave);
        btnSendMotorStart = (Button) viewinflate.findViewById(R.id.btnSendMotorStart);
        btnSendMotorWrite = (Button) viewinflate.findViewById(R.id.btnSendMotorWrite);
        btnMachineHandMotorStart = (Button) viewinflate.findViewById(R.id.btnMachineHandMotorStart);
        btnMachineHandMotorWrite = (Button) viewinflate.findViewById(R.id.btnMachineHandMotorWrite);
        btnCatchCal = (Button) viewinflate.findViewById(R.id.btnCatchCal);
        btnCatchMotorStart = (Button) viewinflate.findViewById(R.id.btnCatchMotorStart);
        btnCatchMotorWrite = (Button) viewinflate.findViewById(R.id.btnCatchMotorWrite);
        btnPressedMotorStart = (Button) viewinflate.findViewById(R.id.btnPressedMotorStart);
        btnPressedMotorWrite = (Button) viewinflate.findViewById(R.id.btnPressedMotorWrite);
        btnShadeMotorParam = (Button) viewinflate.findViewById(R.id.btnShadeMotorParam);
        btnShadeMotorStart = (Button) viewinflate.findViewById(R.id.btnShadeMotorStart);
        btnShadeMotorWrite = (Button) viewinflate.findViewById(R.id.btnShadeMotorWrite);
        btnRotateMotorStart = (Button) viewinflate.findViewById(R.id.btnRotateMotorStart);
        btnRotateMotorStop = (Button) viewinflate.findViewById(R.id.btnRotateMotorStop);
        btnOutPutCal = (Button) viewinflate.findViewById(R.id.btnOutPutCal);
        btnOutPutStart = (Button) viewinflate.findViewById(R.id.btnOutPutStart);
        btnOutPutWrite = (Button) viewinflate.findViewById(R.id.btnOutPutWrite);
        btnAutoRun = (Button) viewinflate.findViewById(R.id.btnAutoRun);
        btnAutoSet = (Button) viewinflate.findViewById(R.id.btnAutoSet);

        etDebugDiameter = (EditText) viewinflate.findViewById(R.id.etDebugDiameter);
        etSendMotorSpeed = (EditText) viewinflate.findViewById(R.id.etSendMotorSpeed);
        etSendMotorOffsetLocation = (EditText) viewinflate.findViewById(R.id.etSendMotorOffsetLocation);
        etMachineHandMotorCatchLocation = (EditText) viewinflate.findViewById(R.id.etMachineHandMotorCatchLocation);
        etMachineHandMotorDetLocation = (EditText) viewinflate.findViewById(R.id.etMachineHandMotorDetLocation);
        etMachineHandMotorOutPutLocation = (EditText) viewinflate.findViewById(R.id.etMachineHandMotorOutPutLocation);
        etMachineHandMotorSpeed = (EditText) viewinflate.findViewById(R.id.etMachineHandMotorSpeed);
        etMachineHandMotorWaitLocation = (EditText) viewinflate.findViewById(R.id.etMachineHandMotorWaitLocation);
        etCatchDelayTime = (EditText) viewinflate.findViewById(R.id.etCatchDelayTime);
        etCatchMaxDiatance = (EditText) viewinflate.findViewById(R.id.etCatchMaxDiatance);
        etCatchOpenDistance = (EditText) viewinflate.findViewById(R.id.etCatchOpenDistance);
        etCatchParam1 = (EditText) viewinflate.findViewById(R.id.etCatchParam1);
        etCatchParam2 = (EditText) viewinflate.findViewById(R.id.etCatchParam2);
        etCatchPutterDistance = (EditText) viewinflate.findViewById(R.id.etCatchPutterDistance);
        etCatchSpeed = (EditText) viewinflate.findViewById(R.id.etCatchSpeed);
        etPressedParam1 = (EditText) viewinflate.findViewById(R.id.etPressedParam1);
        etPressedSpeed = (EditText) viewinflate.findViewById(R.id.etPressedSpeed);
        etShadeMotorParam = (EditText) viewinflate.findViewById(R.id.etShadeMotorParam);
        etShadeMotorSpeed = (EditText) viewinflate.findViewById(R.id.etShadeMotorSpeed);
        etRotateMotorSpeed = (EditText) viewinflate.findViewById(R.id.etRotateMotorSpeed);
        etOutPut10mlStandardParam1 = (EditText) viewinflate.findViewById(R.id.etOutPut10mlStandardParam1);
        etOutPut10mlStandardParam2 = (EditText) viewinflate.findViewById(R.id.etOutPut10mlStandardParam2);
        etOutPut10mlStandardParamDiameter = (EditText) viewinflate.findViewById(R.id.etOutPut10mlStandardParamDiameter);
        etOutPut10mlStandardSpeed = (EditText) viewinflate.findViewById(R.id.etOutPut10mlStandardSpeed);
        etOutPutParam1 = (EditText) viewinflate.findViewById(R.id.etOutPutParam1);
        etOutPutParam2 = (EditText) viewinflate.findViewById(R.id.etOutPutParam2);
        etAutoCount = (EditText) viewinflate.findViewById(R.id.etAutoCount);

        txtSendMotorState = (TextView) viewinflate.findViewById(R.id.txtSendMotorState);
        txtMachineHandMotorState = (TextView) viewinflate.findViewById(R.id.txtMachineHandMotorState);
        txtCatchRunState = (TextView) viewinflate.findViewById(R.id.txtCatchRunState);
        txtOutPutRunState = (TextView) viewinflate.findViewById(R.id.txtOutPutRunState);
        txtPressedRunState = (TextView) viewinflate.findViewById(R.id.txtPressedRunState);
    }

    private void  initButtonClick()
    {
        btnReadTray.setOnClickListener(this);
        btnLaserSwitch.setOnClickListener(this);
        btnMotorReset.setOnClickListener(this);
        btnMotorLockSwitch.setOnClickListener(this);
        btnParamSave.setOnClickListener(this);
        btnSendMotorStart.setOnClickListener(this);
        btnSendMotorWrite.setOnClickListener(this);
        btnMachineHandMotorStart.setOnClickListener(this);
        btnMachineHandMotorWrite.setOnClickListener(this);
        btnCatchCal.setOnClickListener(this);
        btnCatchMotorStart.setOnClickListener(this);
        btnCatchMotorWrite.setOnClickListener(this);
        btnPressedMotorStart.setOnClickListener(this);
        btnPressedMotorWrite.setOnClickListener(this);
        btnShadeMotorParam.setOnClickListener(this);
        btnShadeMotorStart.setOnClickListener(this);
        btnShadeMotorWrite.setOnClickListener(this);
        btnRotateMotorStart.setOnClickListener(this);
        btnRotateMotorStop.setOnClickListener(this);
        btnOutPutCal.setOnClickListener(this);
        btnOutPutStart.setOnClickListener(this);
        btnOutPutWrite.setOnClickListener(this);
        btnAutoRun.setOnClickListener(this);
        btnAutoSet.setOnClickListener(this);
    }
    private void SaveAllParams()
    {
        String strDebugDiameter=etDebugDiameter.getText().toString();
        String strSendMotorSpeed=etSendMotorSpeed.getText().toString();
        String strSendMotorOffsetLocation=etSendMotorOffsetLocation.getText().toString();
        String strMachineHandMotorSpeed=etMachineHandMotorSpeed.getText().toString();
        String strMachineHandMotorCatchLocation=etMachineHandMotorCatchLocation.getText().toString();
        String strMachineHandMotorDetLocation=etMachineHandMotorDetLocation.getText().toString();
        String strMachineHandMotorOutPutLocation=etMachineHandMotorOutPutLocation.getText().toString();
        String strMachineHandMotorWaitLocation=etMachineHandMotorWaitLocation.getText().toString();
        String strCatchDelayTime=etCatchDelayTime.getText().toString();
        String strCatchMaxDiatance=etCatchMaxDiatance.getText().toString();
        String strCatchOpenDistance=etCatchOpenDistance.getText().toString();
        String strCatchParam1=etCatchParam1.getText().toString();
        String strCatchParam2=etCatchParam2.getText().toString();
        String strCatchPutterDistance=etCatchPutterDistance.getText().toString();
        String strCatchSpeed=etCatchSpeed.getText().toString();
        String strPressedParam1=etPressedParam1.getText().toString();
        String strPressedSpeed=etPressedSpeed.getText().toString();
        String strShadeMotorParam=etShadeMotorParam.getText().toString();
        String strShadeMotorSpeed=etShadeMotorSpeed.getText().toString();
        String strRotateMotorSpeed=etRotateMotorSpeed.getText().toString();
        String strOutPut10mlStandardParam1=etOutPut10mlStandardParam1.getText().toString();
        String strOutPut10mlStandardParam2=etOutPut10mlStandardParam2.getText().toString();
        String strOutPut10mlStandardParamDiameter=etOutPut10mlStandardParamDiameter.getText().toString();
        String strOutPut10mlStandardSpeed=etOutPut10mlStandardSpeed.getText().toString();
        String strOutPutParam1=etOutPutParam1.getText().toString();
        String strOutPutParam2=etOutPutParam2.getText().toString();
        List<String> listParamName= Arrays.asList(getResources().getStringArray(R.array.arrayDebugParam));
        List<String>listParamValue=new ArrayList<String>();
        listParamValue.add(strDebugDiameter);
        listParamValue.add(strSendMotorSpeed);
        listParamValue.add(strSendMotorOffsetLocation);
        listParamValue.add(strMachineHandMotorSpeed);
        listParamValue.add(strMachineHandMotorCatchLocation);
        listParamValue.add(strMachineHandMotorDetLocation);
        listParamValue.add(strMachineHandMotorOutPutLocation);
        listParamValue.add(strMachineHandMotorWaitLocation);
        listParamValue.add(strCatchDelayTime);
        listParamValue.add(strCatchMaxDiatance);
        listParamValue.add(strCatchOpenDistance);
        listParamValue.add(strCatchParam1);
        listParamValue.add(strCatchParam2);
        listParamValue.add(strCatchPutterDistance);
        listParamValue.add(strCatchSpeed);
        listParamValue.add(strPressedParam1);
        listParamValue.add(strPressedSpeed);
        listParamValue.add(strShadeMotorParam);
        listParamValue.add(strShadeMotorSpeed);
        listParamValue.add(strRotateMotorSpeed);
        listParamValue.add(strOutPut10mlStandardParam1);
        listParamValue.add(strOutPut10mlStandardParam2);
        listParamValue.add(strOutPut10mlStandardParamDiameter);
        listParamValue.add(strOutPut10mlStandardSpeed);
        listParamValue.add(strOutPutParam1);
        listParamValue.add(strOutPutParam2);
        Log.d(TAG, "SaveAllParams: "+ String.valueOf(listParamName.size())+" values lengthg "+String.valueOf(listParamValue.size()));
    }
    private void LoadAllParams()
    {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnReadTray:
                Toast.makeText(getActivity(), "btnReadTray", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnLaserSwitch:
                break;
            case R.id.btnMotorReset:
                break;
            case R.id.btnMotorLockSwitch:
                break;
            case  R.id.btnParamSave:
                SaveAllParams();
                Toast.makeText(getActivity(), "btnParamSave", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
