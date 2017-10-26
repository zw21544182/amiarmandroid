package cn.ml_tech.mx.mlproj.settingfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.util.CommonUtil;
import cn.ml_tech.mx.mlproj.util.SharedPreferencesUtils;
import cn.ml_tech.mx.mlproj.util.VerSionUtil;

/**
 * 创建时间: 2017/7/27
 * 创建人: zhongwang
 * 功能描述:
 */

public class ProjectUpdateFragment extends BaseFragment {
    private View view;
    private TextView tvVerSionCode;
    private TextView tvVersionName;
    private TextView tvUpdateInfo;
    private Button btCheckUpdate;
    private CheckBox cbIsCheck;
    private boolean isUpdate;

    private VerSionUtil verSionUtil;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_projectupdate, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        tvVerSionCode = (TextView) view.findViewById(R.id.tvVerSionCode);
        tvVersionName = (TextView) view.findViewById(R.id.tvVersionName);
        tvUpdateInfo = (TextView) view.findViewById(R.id.tvUpdateInfo);
        btCheckUpdate = (Button) view.findViewById(R.id.btCheckUpdate);
        cbIsCheck = (CheckBox) view.findViewById(R.id.cbIsCheck);

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        verSionUtil = new VerSionUtil(getActivity());
        setDataToView();
    }

    private void setDataToView() {
        tvVerSionCode.setText(verSionUtil.getVersionCode() + "");
        tvVersionName.setText(verSionUtil.getVersionName());
        tvUpdateInfo.setText(getActivity().getResources().getString(R.string.UPDATEINFO));
        cbIsCheck.setChecked(isUpdate);

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btCheckUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VerSionUtil(getActivity()).updateVersion();
            }
        });
        cbIsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtils.setParam(getActivity(), CommonUtil.ISUPDATE, isChecked);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isUpdate = (boolean) SharedPreferencesUtils.getParam(getActivity(), CommonUtil.ISUPDATE, true);
    }
}
