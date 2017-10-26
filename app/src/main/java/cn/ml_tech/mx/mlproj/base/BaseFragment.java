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

import java.util.List;

import cn.ml_tech.mx.mlservice.DAO.P_Operator;
import cn.ml_tech.mx.mlservice.DAO.P_Source;
import cn.ml_tech.mx.mlservice.DAO.Permission;
import cn.ml_tech.mx.mlservice.IMlService;


/**
 *
 */
public abstract class BaseFragment extends Fragment {
    public BaseActivity mActivity;
    public View view;
    public IMlService mlService;
    public AmiApp amiApp;
    public List<P_Operator> p_operators;
    public List<P_Source> p_sources;
    private Permission permission;
    protected Handler handler;

    public BaseFragment() {
        // Required empty public constructor
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

    ;

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

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    /**
     * 在调用次方法之前必须先通过set方法传递Permissoin
     *
     * @param sourceId
     * @param operateId
     * @return
     */
    public boolean getPermissionById(long sourceId, long operateId) {
        return permission.getPermissiondata().get(getPermissionTextById(sourceId, operateId));
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

    public String getPermissionTextById(long sourceId, long operateId) {
        return getTitleById(sourceId) + getOperateNameById(operateId);
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
