package cn.ml_tech.mx.mlproj.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import java.util.Map;

import cn.ml_tech.mx.mlproj.activity.CsbdActivity;
import cn.ml_tech.mx.mlproj.activity.JcsjcxActivity;
import cn.ml_tech.mx.mlproj.activity.LoginActivity;
import cn.ml_tech.mx.mlproj.activity.XtwhActivity;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.activity.YpjcActivity;
import cn.ml_tech.mx.mlservice.DAO.Permission;

public class OptionFragment extends BaseFragment implements View.OnClickListener {
    private ImageButton ibCsbd;
    private ImageButton ibYpjc;
    private ImageButton ibSjcx;
    private ImageButton ibXtwh;
    private Permission permission;
    private boolean isPermission;
    private Map<String, Boolean> perData;

    @Override
    public View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_option, null);
        initFindViewById(v);
        return v;
    }

    @Override
    public void initFindViewById(View view) {
        ibCsbd = (ImageButton) view.findViewById(R.id.ibCsbd);
        ibYpjc = (ImageButton) view.findViewById(R.id.ibYpjc);
        ibSjcx = (ImageButton) view.findViewById(R.id.ibSjcx);
        ibXtwh = (ImageButton) view.findViewById(R.id.ibXtwh);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        ibCsbd.setOnClickListener(this);
        ibYpjc.setOnClickListener(this);
        ibSjcx.setOnClickListener(this);
        ibXtwh.setOnClickListener(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        permission = ((LoginActivity) getActivity()).getPermission();
        isPermission = ((LoginActivity) getActivity()).isPermission();
        perData = permission.getPermissiondata();
        if (!perData.get(getTitleById(6) + getOperateNameById(1)))
            ibCsbd.setVisibility(View.INVISIBLE);
        else
            ibCsbd.setVisibility(View.VISIBLE);
        if (!perData.get(getTitleById(15) + getOperateNameById(1)))
            ibYpjc.setVisibility(View.INVISIBLE);
        else
            ibYpjc.setVisibility(View.VISIBLE);
        if (!perData.get(getTitleById(19) + getOperateNameById(1)))
            ibSjcx.setVisibility(View.INVISIBLE);
        else
            ibSjcx.setVisibility(View.VISIBLE);
        if (!perData.get(getTitleById(27) + getOperateNameById(1)))
            ibXtwh.setVisibility(View.INVISIBLE);
        else
            ibXtwh.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ibCsbd:
                if (perData.get(getTitleById(6) + getOperateNameById(8))) {
                    intent = new Intent(getActivity(), CsbdActivity.class);
                    startActivity(intent);
                } else {
                    showRefuseTip();
                }
                break;
            case R.id.ibYpjc:
                if (perData.get(getTitleById(15) + getOperateNameById(8))) {
                    intent = new Intent(getActivity(), YpjcActivity.class);
                    startActivity(intent);
                } else {
                    showRefuseTip();
                }
                break;
            case R.id.ibSjcx:
                if (perData.get(getTitleById(19) + getOperateNameById(8))) {
                    intent = new Intent(getActivity(), JcsjcxActivity.class);
                    startActivity(intent);
                } else showRefuseTip();
                break;
            case R.id.ibXtwh:
                if (perData.get(getTitleById(27) + getOperateNameById(8))) {
                    intent = new Intent(getActivity(), XtwhActivity.class);
                    startActivity(intent);
                } else showRefuseTip();
                break;
            default:
        }
    }

    public void restart() {
        initData(null);
        initEvent();
    }
}
