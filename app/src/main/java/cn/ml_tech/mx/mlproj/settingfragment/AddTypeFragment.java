package cn.ml_tech.mx.mlproj.settingfragment;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.activity.XtwhActivity;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.fragment.XtwhFragment;
import cn.ml_tech.mx.mlservice.DAO.PermissionHelper;

import static cn.ml_tech.mx.mlproj.util.CommonUtil.FAILURE;
import static cn.ml_tech.mx.mlproj.util.CommonUtil.SUCESS;

/**
 * 创建时间: 2017/7/21
 * 创建人: zhongwang
 * 功能描述:
 */

public class AddTypeFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private EditText etTypeName;
    private LinearLayout rootLayout;
    private Button btSave;
    private Button btBack;
    private TextView tvShowResult;
    private XtwhFragment xtwhFragment;
    private List<PermissionHelper> permissionHelpers;


    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_addtype, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        etTypeName = (EditText) view.findViewById(R.id.etTypeName);
        rootLayout = (LinearLayout) view.findViewById(R.id.rootLayout);
        tvShowResult = (TextView) view.findViewById(R.id.tvShowResult);
        btSave = (Button) view.findViewById(R.id.btSave);
        btBack = (Button) view.findViewById(R.id.btBack);
        btSave.setEnabled(false);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btBack.setOnClickListener(this);
        btSave.setOnClickListener(this);
        etTypeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().equals("")) {
                    tvShowResult.setText("输入不能为空");
                    btSave.setEnabled(false);
                    return;
                }
                try {
                    if (mlService.isRename(s.toString())) {

                        tvShowResult.setText("重名请重新输入");
                        btSave.setEnabled(false);
                    } else {
                        tvShowResult.setText("可增加");
                        btSave.setEnabled(true);
                    }


                } catch (RemoteException e) {
                    e.printStackTrace();
                    tvShowResult.setText("重名请重新输入");
                    btSave.setEnabled(false);

                }
            }
        });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        xtwhFragment = ((XtwhActivity) getActivity()).getXtwhFragment();
        new Thread() {
            public void run() {
                super.run();
                try {
                    permissionHelpers = mlService.getPermissionInfo();
                    handler.sendEmptyMessage(SUCESS);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(FAILURE);
                }
            }
        }.start();
    }

    @Override
    protected void handleMsg(Message message) {
        super.handleMsg(message);
        switch (message.what) {
            case SUCESS:
                setCheckOutListen();
                break;
            case FAILURE:
                btSave.setEnabled(false);
                break;
        }
    }

    private void setCheckOutListen() {
        int i = 0;
        for (int index = 0; index < rootLayout.getChildCount(); index++) {
            if (rootLayout.getChildAt(index) instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) rootLayout.getChildAt(index);
                for (int lindex = 0; lindex < layout.getChildCount(); lindex++) {
                    if (layout.getChildAt(lindex) instanceof LinearLayout) {
                        LinearLayout linearLayout = (LinearLayout) layout.getChildAt(lindex);
                        for (int zindex = 0; zindex < linearLayout.getChildCount(); zindex++) {
                            if (linearLayout.getChildAt(zindex) instanceof CheckBox) {
                                CheckBox checkBox = (CheckBox) linearLayout.getChildAt(zindex);
                                PermissionHelper permissionHelper = permissionHelpers.get(i);
                                permissionHelper.setExtra(i);
                                checkBox.setTag(permissionHelper);
                                checkBox.setOnCheckedChangeListener(this);
                                i++;
                            }

                        }


                    }
                }
            }
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            initData(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSave:
                new Thread() {
                    public void run() {
                        super.run();
                        try {
                            mlService.addNewUserType(etTypeName.getText().toString(), permissionHelpers);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.btBack:
                xtwhFragment.moveToUserType();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        PermissionHelper permissionHelper = (PermissionHelper) compoundButton.getTag();
        permissionHelper.setCanOperate(b);
        permissionHelpers.set(permissionHelper.getExtra(), permissionHelper);
    }
}

