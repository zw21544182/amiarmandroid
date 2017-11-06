package cn.ml_tech.mx.mlproj.settingfragment;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.activity.XtwhActivity;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.fragment.XtwhFragment;

/**
 * 创建时间: 2017/7/21
 * 创建人: zhongwang
 * 功能描述:
 */

public class AddTypeFragment extends BaseFragment implements View.OnClickListener {
    private static final int CHECKSUCESS = 11;
    private static final int CHECKFAILURE = 22;
    private static final int SAVESUCESS = 33;
    private static final int SAVEFAILURE = 44;

    private EditText etTypeName;
    private Button btCheckType;
    private LinearLayout rootLayout;
    private Button btSave;
    private Button btBack;

    private List<LinearLayout> layoutList;
    private XtwhFragment xtwhFragment;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_addtype, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        etTypeName = (EditText) view.findViewById(R.id.etTypeName);
        btCheckType = (Button) view.findViewById(R.id.btCheckType);
        rootLayout = (LinearLayout) view.findViewById(R.id.rootLayout);
        btSave = (Button) view.findViewById(R.id.btSave);
        btBack = (Button) view.findViewById(R.id.btBack);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btBack.setOnClickListener(this);
        btSave.setOnClickListener(this);
        btCheckType.setOnClickListener(this);
        etTypeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mlService = mActivity.getmService();
        xtwhFragment = ((XtwhActivity) getActivity()).getXtwhFragment();
        init();
        initRootSource();
        initSource();
        try {
            initOperate();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void initOperate() throws RemoteException {

    }

    private void init() {
        if (layoutList == null)
            layoutList = new ArrayList<>();
        layoutList.clear();
    }

    private void initRootSource() {

    }

    private void initSource() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            try {
                initOperate();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onClick(View v) {

    }
}
