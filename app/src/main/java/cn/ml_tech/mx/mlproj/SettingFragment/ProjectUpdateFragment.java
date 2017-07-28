package cn.ml_tech.mx.mlproj.SettingFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import cn.ml_tech.mx.mlproj.BaseFragment;
import cn.ml_tech.mx.mlproj.R;

/**
 * 创建时间: 2017/7/27
 * 创建人: zhongwang
 * 功能描述:
 */

public class ProjectUpdateFragment extends BaseFragment {
    private View view;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_projectupdate, null);
        return view;
    }

    @Override
    public void initFindViewById(View view) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
