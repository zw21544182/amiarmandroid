package cn.ml_tech.mx.mlproj.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import java.util.Map;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.activity.CsbdActivity;
import cn.ml_tech.mx.mlproj.activity.JcsjcxActivity;
import cn.ml_tech.mx.mlproj.activity.XtwhActivity;
import cn.ml_tech.mx.mlproj.activity.YpjcActivity;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
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

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ibCsbd:
                    intent = new Intent(getActivity(), CsbdActivity.class);
                    startActivity(intent);
                break;
            case R.id.ibYpjc:
                    intent = new Intent(getActivity(), YpjcActivity.class);
                    startActivity(intent);

                break;
            case R.id.ibSjcx:
                    intent = new Intent(getActivity(), JcsjcxActivity.class);
                    startActivity(intent);
                break;
            case R.id.ibXtwh:
                    intent = new Intent(getActivity(), XtwhActivity.class);
                    startActivity(intent);
                 break;
            default:
        }
    }

    public void restart() {
        initData(null);
        initEvent();
    }
}
