package cn.ml_tech.mx.mlproj;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import cn.ml_tech.mx.mlservice.DAO.P_Operator;
import cn.ml_tech.mx.mlservice.DAO.P_Source;
import cn.ml_tech.mx.mlservice.IMlService;


public abstract class BaseFragment extends Fragment {
    public BaseActivity mActivity;
    public View view;
    public IMlService mlService;
    public AmiApp amiApp;
    public List<P_Operator> p_operators;
    public List<P_Source> p_sources;

    public BaseFragment() {
        // Required empty public constructor
    }

    //返回一个需要展示的View 
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void showToast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    //当Activity初始化之后可以在这里进行一些数据的初始化操作
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = initView(inflater);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public View getRootView() {
        return view;
    }

    //子类复写此方法初始化事件
    protected void initEvent() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        amiApp = (AmiApp) mActivity.getApplication();
        mlService = amiApp.getmMLService();
        p_operators = amiApp.getP_operators();
        p_sources = amiApp.getP_sources();
        initData(savedInstanceState);
        initEvent();
    }

    public void showRefuseTip() {
        showToast("拒绝访问");
    }

    public String getTitleById(long id) {
        String result = "";
        for (P_Source p_source :
                p_sources
                ) {
            if (p_source.getId() == id) {
                result = p_source.getTitle();
                break;
            }

        }
        return result;
    }

    public String getOperateNameById(long id) {
        String result = "";
        for (P_Operator p_operator :
                p_operators) {
            if (p_operator.getId() == id) {
                result = p_operator.getTitle();
                break;
            }
        }
        return result;
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
