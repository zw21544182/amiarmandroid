package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cn.ml_tech.mx.mlservice.Bean.TrayHelper;
import cn.ml_tech.mx.mlservice.DAO.Tray;
import cn.ml_tech.mx.mlservice.IMlService;

/**
 * Created by ml on 2017/5/24.
 */

public class TrayManagerFragment extends BaseFragment implements View.OnClickListener{

    private EditText etTrayIcId;
    private EditText etTrayDisplayNumber;
    private EditText etContainerDiameter;
    private EditText etTrayInnerDiameter;
    private EditText etTrayExternalDiameter;
    private EditText etMark;
    private Button btnReadTray;
    private Button btnSaveTray;
    private Button btnResetTray;
    private Tray mTray;
    private TrayHelper mTrayHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View initView(LayoutInflater inflater) {
        view= inflater.inflate(R.layout.fragment_traymanager,null);
        initFindViewById(view);


        return view;
    }

    @Override
    public void initFindViewById(View view) {
        etTrayIcId = (EditText) view.findViewById(R.id.etTrayIcId);
        etTrayDisplayNumber = (EditText) view.findViewById(R.id.etTrayDisplayNumber);
        etContainerDiameter = (EditText) view.findViewById(R.id.etContainerDiameter);
        etTrayInnerDiameter = (EditText) view.findViewById(R.id.etTrayInnerDiameter);
        etTrayExternalDiameter = (EditText) view.findViewById(R.id.etTrayExternalDiameter);
        etMark = (EditText) view.findViewById(R.id.etMark);
        btnReadTray = (Button) view.findViewById(R.id.btnReadTray);
        btnSaveTray = (Button) view.findViewById(R.id.btnSaveTray);
        btnResetTray = (Button) view.findViewById(R.id.btnResetTray);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTray=new Tray();
        mTrayHelper=new TrayHelper(mActivity);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnReadTray.setOnClickListener(this);
        btnSaveTray.setOnClickListener(this);
        btnResetTray.setOnClickListener(this);
    }
    private String  ReadTrayIcId()
    {
        String icid=mTrayHelper.getTrayIcId();
        this.mTray.setIcId(icid);

        if(TextUtils.isEmpty(icid))return "";
        else return icid;
    }
    private void SaveTray(Tray tray)
    {
        this.mTray.setIcId(etTrayIcId.getText().toString().trim());
        this.mTray.setDisplayId(UtilsHelper.String2Int(etTrayDisplayNumber.getText().toString().trim()));
        this.mTray.setInnerDiameter(UtilsHelper.String2Double(etTrayInnerDiameter.getText().toString().trim()));
        this.mTray.setExternalDiameter(UtilsHelper.String2Double(etTrayExternalDiameter.getText().toString().trim()));
        this.mTray.setDiameter(UtilsHelper.String2Double(etContainerDiameter.getText().toString().trim()));
        this.mTray.setMark(etMark.getText().toString().trim());

        mTrayHelper.saveOrUpdateTray(this.mTray);

    }
    private void ResetTray()
    {
        this.mTrayHelper.resetTray();
        this.etContainerDiameter.setText("");
        this.etMark.setText("");
        this.etTrayDisplayNumber.setText("");
        this.etTrayInnerDiameter.setText("");
        this.etTrayIcId.setText("");
        this.etTrayExternalDiameter.setText("");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnReadTray:
                this.etTrayIcId.setText( ReadTrayIcId());
                break;
            case R.id.btnSaveTray:
                SaveTray(mTray);
                break;
            case R.id.btnResetTray:
                ResetTray();
                break;
            default:
                break;
        }
    }
}
