package cn.ml_tech.mx.mlproj.SettingFragment;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.ml_tech.mx.mlproj.Adapter.StringAdapter;
import cn.ml_tech.mx.mlproj.BaseFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.P_Operator;
import cn.ml_tech.mx.mlservice.DAO.P_Source;
import cn.ml_tech.mx.mlservice.DAO.PermissionHelper;
import cn.ml_tech.mx.mlservice.DAO.UserType;

/**
 * 创建时间: 2017/6/30
 * 创建人: zhongwang
 * 功能描述: 权限管理Fragment
 */

public class PerManageFragment extends BaseFragment {
    private Spinner spUser;
    private LinearLayout rootLayout;
    private List<UserType> users;
    private List<String> usernames;
    private List<P_Source> rootP_sources;
    private List<P_Source> p_sources;
    private List<LinearLayout> sourceLayouts;
    private List<LinearLayout> rootSourceLayouts;
    private List<LinearLayout> layoutList;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_permissmanage, null);
        initFindViewById(view);
        return view;
    }


    @Override
    public void initFindViewById(View view) {
        spUser = (Spinner) view.findViewById(R.id.spUser);
        rootLayout = (LinearLayout) view.findViewById(R.id.rootLayout);

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initSource();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initSpinnerData();
        init();
        initRootSource();
        initSource();
    }

    private void initSpinnerData() {
        usernames = new ArrayList<>();
        try {
            users = mActivity.getmService().getAllUserType();
            for (UserType user :
                    users) {
                usernames.add(user.getTypeName());
            }
            spUser.setAdapter(new StringAdapter(usernames, mActivity));
            spUser.setSelection(0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public long getUserTypeId() {
        UserType user = users.get(spUser.getSelectedItemPosition());
        return user.getTypeId();
    }

    private void init() {
        if (layoutList == null)
            layoutList = new ArrayList<>();
        layoutList.clear();
    }

    private void initSource() {
        final long typeId = getUserTypeId();
        sourceLayouts = new ArrayList<>();
        p_sources = new ArrayList<>();
        for (int i = 0; i < rootP_sources.size(); i++) {
            try {
                P_Source p_source = rootP_sources.get(i);
                LinearLayout linearLayout = rootSourceLayouts.get(i);
                linearLayout.removeAllViews();
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
                    PermissionHelper permissionHelper = mActivity.mService.getP_OperatorBySourceId(source.getId());
                    Map<Long, P_Operator> pOperatorMap = permissionHelper.getP_operatorMap();
                    for (Map.Entry<Long, P_Operator> entry2 : pOperatorMap.entrySet()) {
                        final long sourceoperateid = entry2.getKey();
                        P_Operator p_operator = entry2.getValue();
                        final CheckBox checkBox = new CheckBox(getActivity());
                        checkBox.setText(p_operator.getTitle());
                        boolean result = mActivity.getmService().isOperate(sourceoperateid, typeId);
                        checkBox.setChecked(result);
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                try {
                                    if (isChecked) {
                                        mActivity.getmService().addPermission(sourceoperateid, typeId);
                                    } else {
                                        mActivity.getmService().deletePermission(sourceoperateid, typeId);
                                    }

                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        horlayout.addView(checkBox);
                    }
                    layout.addView(horlayout);
                }
                linearLayout.addView(layout);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            initData(null);
    }
}
