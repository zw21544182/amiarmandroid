package cn.ml_tech.mx.mlproj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

public class LoginFragment extends BaseFragment {
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_login, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

}
