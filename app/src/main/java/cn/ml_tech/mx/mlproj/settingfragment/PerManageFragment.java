package cn.ml_tech.mx.mlproj.settingfragment;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.adapter.StringAdapter;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlservice.DAO.UserType;


public class PerManageFragment extends BaseFragment {
    private Spinner spUser;
    private List<UserType> users;
    private List<String> usernames;
    private long curUserTypeId;
    private CheckBox cbBdLook;
    private CheckBox cbBdCheck;
    private CheckBox cbLzLook;
    private CheckBox cbLzExcute;
    private CheckBox cbYyLook;
    private CheckBox cbYyExcute;
    private CheckBox cbExLook;
    private CheckBox cbExExcute;
    private CheckBox cbResLook;
    private CheckBox cbPaLook;
    private CheckBox cbPaExcute;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_permissmanage, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        spUser = (Spinner) view.findViewById(R.id.spUser);
        initInstrunmentView();
    }


    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initSpinnerData();
    }


    private void initSpinnerData() {
        usernames = new ArrayList<>();
        try {
            users = mActivity.getmService().getAllUserType();
            for (UserType user :
                    users) {
                usernames.add(user.getTypeName());
            }
            spUser.setAdapter(new StringAdapter(usernames, mActivity));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public long getUserTypeId() {
        UserType user = users.get(spUser.getSelectedItemPosition());
        return user.getTypeId();
    }

    private void initInstrunmentView() {
        cbBdLook = (CheckBox) view.findViewById(R.id.cbBdLook);
        cbBdCheck = (CheckBox) view.findViewById(R.id.cbBdCheck);
        cbLzLook = (CheckBox) view.findViewById(R.id.cbLzLook);
        cbLzExcute = (CheckBox) view.findViewById(R.id.cbLzExcute);
        cbYyLook = (CheckBox) view.findViewById(R.id.cbYyLook);
        cbYyExcute = (CheckBox) view.findViewById(R.id.cbYyExcute);
        cbExLook = (CheckBox) view.findViewById(R.id.cbExLook);
        cbExExcute = (CheckBox) view.findViewById(R.id.cbExExcute);
        cbResLook = (CheckBox) view.findViewById(R.id.cbResLook);
        cbPaLook = (CheckBox) view.findViewById(R.id.cbPaLook);
        cbPaExcute = (CheckBox) view.findViewById(R.id.cbPaExcute);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }

    }
}
