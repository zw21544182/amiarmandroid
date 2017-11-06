package cn.ml_tech.mx.mlproj.base;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.ml_tech.mx.mlservice.IMlService;

public abstract class BaseFragment extends Fragment {
    public BaseActivity mActivity;
    public View view;
    public IMlService mlService;
    public AmiApp amiApp;
    protected Handler handler;

    public BaseFragment() {
    }

    public void showToast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = initView(inflater);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleMsg(msg);
            }
        };
    }

    public View getRootView() {
        return view;
    }

    //子类复写此方法初始化事件
    protected void initEvent() {
    }

    protected void handleMsg(Message message) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        amiApp = (AmiApp) mActivity.getApplication();
        mlService = amiApp.getmMLService();
        initData(savedInstanceState);
        initEvent();
    }


    public abstract View initView(LayoutInflater inflater);

    public abstract void initFindViewById(View view);

    public abstract void initData(@Nullable Bundle savedInstanceState);
}
