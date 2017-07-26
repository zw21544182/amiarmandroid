package cn.ml_tech.mx.mlproj.SettingFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import cn.ml_tech.mx.mlproj.BaseActivity;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.DevUuid;
/**
 * Created by ml on 2017/5/24.
 */
public class ManchineManagerFragment extends Fragment implements View.OnClickListener {
    private final  String TAG="ManchineManagerFragment";
    private View view;
    private Button btnSaveOrUpdate;
    private EditText etUserAbbreviation;
    private EditText etUserName;
    private EditText etMachineNumber;
    private EditText etMachineType;
    private EditText etMachineName;
    private EditText etMachineFactoryName;
    private EditText etMachineDateOfProduction;
    private BaseActivity mActivity;
    private DevUuid devUuid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_machinemanager,container,false);
        mActivity = (BaseActivity) getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnSaveOrUpdate = (Button) view.findViewById(R.id.btnSave);
        btnSaveOrUpdate.setOnClickListener(this);
        etUserAbbreviation = (EditText) view.findViewById(R.id.etUserAbbreviation);
        etUserName = (EditText) view.findViewById(R.id.etUserName);
        etMachineNumber = (EditText) view.findViewById(R.id.etMachineNumber);
        etMachineType = (EditText) view.findViewById(R.id.etMachineType);
        etMachineName = (EditText) view.findViewById(R.id.etMachineName);
        etMachineFactoryName = (EditText) view.findViewById(R.id.etMachineFactoryName);
        etMachineDateOfProduction = (EditText) view.findViewById(R.id.etMachineDateOfProduction);
        LoadInfo();
    }

    private void LoadInfo()
    {
        devUuid = null;
        try {
            devUuid = mActivity.mService.getDeviceManagerInfo();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(null!= devUuid)
        {
            etUserAbbreviation.setText(devUuid.getUserAbbreviation());
            etUserName.setText(devUuid.getUserName());
            etMachineNumber.setText(devUuid.getDevID());
            etMachineType.setText(devUuid.getDevModel());
            etMachineName.setText(devUuid.getDevName());
            etMachineFactoryName.setText(devUuid.getDevFactory());
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            etMachineDateOfProduction.setText(dateFormat.format(devUuid.getDevDateOfProduction()));
        }

    }
    private void SaveOrUpdateInfo()
    {
        if(null==devUuid){devUuid=new DevUuid(); devUuid.setId(1);}
        devUuid.setUserAbbreviation(etUserAbbreviation.getText().toString());
        devUuid.setUserName(etUserName.getText().toString());
        devUuid.setDevID(etMachineNumber.getText().toString());
        devUuid.setDevModel(etMachineType.getText().toString());
        devUuid.setDevName(etMachineName.getText().toString());
        devUuid.setDevFactory(etMachineFactoryName.getText().toString());
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            devUuid.setDevDateOfProduction(dateFormat.parse(etMachineDateOfProduction.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            mActivity.mService.setDeviceManagerInfo(devUuid);
            devUuid=mActivity.mService.getDeviceManagerInfo();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSave:
                SaveOrUpdateInfo();
                break;
            default:
                break;
        }
    }
}
