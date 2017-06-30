package cn.ml_tech.mx.mlproj.SettingFragment;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.Adapter.StringAdapter;
import cn.ml_tech.mx.mlproj.BaseFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.Bean.User;

/**
 * 创建时间: 2017/6/30
 * 创建人: zhongwang
 * 功能描述: 权限管理Fragment
 */

public class PerManageFragment extends BaseFragment {
    private Spinner spUser;
    private List<User> users;
    private List<String> usernames;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_permissmanage, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        spUser = (Spinner) view.findViewById(R.id.spUser);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        usernames = new ArrayList<>();
        try {
            users = mActivity.getmService().getUserList();
            for (User user :
                    users) {
                usernames.add(user.getUserName());
            }
            spUser.setAdapter(new StringAdapter(usernames, mActivity));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
