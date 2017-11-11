package cn.ml_tech.mx.mlproj.settingfragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import java.util.List;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.adapter.UserAdapter;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlservice.DAO.P_SourceOperator;
import cn.ml_tech.mx.mlservice.DAO.PermissionHelper;
import cn.ml_tech.mx.mlservice.DAO.UserType;
public class PerManageFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {
    private Spinner spUser;
    private LinearLayout rootLayout;
    private List<UserType> users;
    private long currentUserTypeId;
    private List<PermissionHelper> permissionHelpers;
    private Handler handler;
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_permissmanage, null);
        initFindViewById(view);
        return view;
    }
    @Override
    public void initFindViewById(View view) {
        spUser = (Spinner) view.findViewById(R.id.spUser);
        rootLayout = (LinearLayout) view.findViewById(R.id.rootLayout);
    }
    @Override
    protected void initEvent() {
        super.initEvent();
        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentUserTypeId = users.get(i).getTypeId();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            permissionHelpers = mlService.getPermissionInfoByType((int) currentUserTypeId);
                            handler.sendEmptyMessage(1);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                setCheckOutListen();
            }
        };
        initSpinnerData();
    }
    private void setCheckOutListen() {
        int i = 0;
        for (int index = 0; index < rootLayout.getChildCount(); index++) {
            if (rootLayout.getChildAt(index) instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) rootLayout.getChildAt(index);
                for (int lindex = 0; lindex < layout.getChildCount(); lindex++) {
                    if (layout.getChildAt(lindex) instanceof LinearLayout) {
                        LinearLayout linearLayout = (LinearLayout) layout.getChildAt(lindex);
                        for (int zindex = 0; zindex < linearLayout.getChildCount(); zindex++) {
                            if (linearLayout.getChildAt(zindex) instanceof CheckBox) {
                                CheckBox checkBox = (CheckBox) linearLayout.getChildAt(zindex);
                                PermissionHelper permissionHelper = permissionHelpers.get(i);
                                final P_SourceOperator tag = permissionHelper.getP_sourceOperator();
                                checkBox.setTag(tag);
                                //防止无用操作
                                checkBox.setOnCheckedChangeListener(null);
                                checkBox.setChecked(permissionHelper.isCanOperate());
                                checkBox.setOnCheckedChangeListener(this);
                                i++;
                            }

                        }


                    }
                }
            }
        }

    }
    private void initSpinnerData() {
        try {
            users = mActivity.getmService().getAllUserType();
            spUser.setAdapter(new UserAdapter(users, mActivity));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        CheckBox checkBox = (CheckBox) compoundButton;
        P_SourceOperator tag = (P_SourceOperator) checkBox.getTag();
        Log.d("Zw", "excute check listen");
        try {
            mlService.operatePermission(tag.getP_operator_id(), tag.getP_source_id(), currentUserTypeId, b);
        } catch (RemoteException e) {

        }
    }
}
