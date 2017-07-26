package cn.ml_tech.mx.mlproj.SettingFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.ml_tech.mx.mlproj.BaseFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.XtwhActivity;
import cn.ml_tech.mx.mlproj.XtwhFragment;
import cn.ml_tech.mx.mlservice.DAO.P_Operator;
import cn.ml_tech.mx.mlservice.DAO.P_Source;
import cn.ml_tech.mx.mlservice.DAO.PermissionHelper;

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
    private List<P_Source> p_sources;
    private List<LinearLayout> sourceLayouts;
    private List<LinearLayout> rootSourceLayouts;
    private List<P_Source> rootP_sources;
    private List<LinearLayout> layoutList;
    private List<String> sourceoperateId;
    private boolean isCheckd;
    private XtwhFragment xtwhFragment;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            switch (msg.what) {
                case CHECKSUCESS:
                    if (isCheckd)
                        showToast("可以使用的类型名");
                    else
                        showToast("重名,请重新输入");
                    break;
                case CHECKFAILURE:
                    isCheckd = false;
                    break;
                case SAVESUCESS:
                    showToast("保存成功");
                    break;
                case SAVEFAILURE:
                    showToast("保存失败");
                    break;
            }
        }
    };
    private ArrayList<LinearLayout> horLayout;
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
                isCheckd = false;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mlService = mActivity.getmService();
        sourceoperateId = new ArrayList<>();
        xtwhFragment = ((XtwhActivity) getActivity()).getXtwhFragment();
        init();
        initRootSource();
        initSource();
        initOperate();
    }
    private void initOperate() {
        int layoutIndex = 0;
        for (int i = 0; i < rootP_sources.size(); i++) {
            P_Source p_source = rootP_sources.get(i);
            try {
                p_sources = mActivity.getmService().getP_SourceByUrl(p_source.getUrl());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            for (int z = 0; z < p_sources.size(); z++) {
                P_Source source = p_sources.get(z);
                LinearLayout horlayout = horLayout.get(layoutIndex);
                layoutIndex++;
                for (int c = 0; c < horlayout.getChildCount(); c++) {
                    View child = horlayout.getChildAt(c);
                    if (child instanceof CheckBox) {
                        horlayout.removeView(child);
                        c--;
                    }
                }
                PermissionHelper permissionHelper = null;
                try {
                    permissionHelper = mActivity.mService.getP_OperatorBySourceId(source.getId());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Map<Long, P_Operator> pOperatorMap = permissionHelper.getP_operatorMap();
                for (Map.Entry<Long, P_Operator> entry2 : pOperatorMap.entrySet()) {
                    final long sourceoperateid = entry2.getKey();
                    P_Operator p_operator = entry2.getValue();
                    final CheckBox checkBox = new CheckBox(getActivity());
                    checkBox.setText(p_operator.getTitle());
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                sourceoperateId.add(sourceoperateid + "");
                            } else {
                                sourceoperateId.remove(sourceoperateid + "");
                            }
                        }
                    });
                    horlayout.addView(checkBox);
                }

            }
        }
    }

    private void init() {
        if (layoutList == null)
            layoutList = new ArrayList<>();
        layoutList.clear();
    }

    private void initRootSource() {
        try {
            rootSourceLayouts = new ArrayList<>();
            rootP_sources = mActivity.getmService().getRootP_Source();
            for (P_Source p_source :
                    rootP_sources) {
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams rootSourceParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout.setLayoutParams(rootSourceParams);
                TextView tvRootSource = new TextView(getActivity());
                tvRootSource.setGravity(Gravity.END);
                tvRootSource.setTextAppearance(getActivity(), R.style.TextViewStyle);//设置控件的style
                tvRootSource.setText(p_source.getTitle());
                linearLayout.addView(tvRootSource);
                rootSourceLayouts.add(linearLayout);
                rootLayout.addView(linearLayout);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void initSource() {
        sourceLayouts = new ArrayList<>();
        horLayout = new ArrayList<>();
        p_sources = new ArrayList<>();
        for (int i = 0; i < rootP_sources.size(); i++) {
            try {
                P_Source p_source = rootP_sources.get(i);
                LinearLayout linearLayout = rootSourceLayouts.get(i);
                p_sources = mActivity.getmService().getP_SourceByUrl(p_source.getUrl());
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layout.setLayoutParams(layoutParams);
                for (final P_Source source :
                        p_sources) {
                    LinearLayout horlayout = new LinearLayout(getActivity());
                    horlayout.setOrientation(LinearLayout.HORIZONTAL);
                    horlayout.setLayoutParams(layoutParams);
                    TextView tvSource = new TextView(getActivity());
                    tvSource.setTextAppearance(getActivity(), R.style.TextViewStyle);//设置控件的style
                    tvSource.setText(source.getTitle());
                    horlayout.addView(tvSource);
                    horLayout.add(horlayout);
                    Log.d("zw", "horLayout Size " + horLayout.size());
                    layout.addView(horlayout);
                }
                linearLayout.addView(layout);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btCheckType:
                final String typeName = etTypeName.getEditableText().toString();
                if (progressDialog == null)
                    progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("检查中...");
                progressDialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            isCheckd = mlService.canAddType(typeName);
                            handler.sendEmptyMessage(CHECKSUCESS);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(CHECKFAILURE);

                        }

                    }
                }.start();
                break;
            case R.id.btSave:
                if (isCheckd) {
                    if (progressDialog == null)
                        progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("保存中...");
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                mlService.addUserType(etTypeName.getEditableText().toString(), sourceoperateId);
                                handler.sendEmptyMessage(SAVESUCESS);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                handler.sendEmptyMessage(SAVEFAILURE);

                            }
                        }
                    }.start();
                } else {
                    showToast("请先检查用户合法性");
                }
                break;
            case R.id.btBack:
                xtwhFragment.moveToUserType();
                break;
        }
    }
}
