package cn.ml_tech.mx.mlproj.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.R;

public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private TextView tvOpenCv;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_login, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        tvOpenCv = (TextView) view.findViewById(R.id.tvOpenCv);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        tvOpenCv.setOnClickListener(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {

    }
}
