package cn.ml_tech.mx.mlproj;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public abstract class BaseFragment extends Fragment {
    public Activity mActivity;
    public View view;
    public Context ct;

    public BaseFragment() {
        // Required empty public constructor
    }
    //返回一个需要展示的View 
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ct = getActivity();
    }


    //当Activity初始化之后可以在这里进行一些数据的初始化操作
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity=getActivity();
        view = initView(inflater);
        return view;
    }
    public View getRootView()
    {
        return view;
    }
    //子类复写此方法初始化事件
    protected void initEvent()
    {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
        initEvent();
    }

    /*
        子类实现此方法返回View展示
         */
    public abstract View initView(LayoutInflater inflater);
    //初始化控件
    public abstract void initFindViewById(View view);
    //子类在此方法中实现数据的初始化
    public abstract void initData(@Nullable Bundle savedInstanceState);

}
