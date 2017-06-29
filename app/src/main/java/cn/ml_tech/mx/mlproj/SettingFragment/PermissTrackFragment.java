package cn.ml_tech.mx.mlproj.SettingFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import cn.ml_tech.mx.mlproj.BaseFragment;
import cn.ml_tech.mx.mlproj.R;

/**
 * 创建时间: 2017/6/29
 * 创建人: zhongwang
 * 功能描述:权限管理
 */

public class PermissTrackFragment extends BaseFragment {
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_camparam, null);
        initFindViewById(view);
        return view; }

    @Override
    public void initFindViewById(View view) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
