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
import android.widget.Spinner;

import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.adapter.UserAdapter;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlservice.DAO.P_SourceOperator;
import cn.ml_tech.mx.mlservice.DAO.PermissionHelper;
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
    private CheckBox cbKsLook;
    private CheckBox cbKsExc;
    private CheckBox cbJyLook;
    private CheckBox cbJyAdd;
    private CheckBox cbJyExc;
    private CheckBox cbYxLook;
    private CheckBox cbYxModify;
    private CheckBox cbYxDelete;
    private CheckBox cbKjLook;
    private CheckBox cbKjExc;
    private CheckBox cbSdLook;
    private CheckBox cbSdExp;
    private CheckBox cbSdExc;
    private CheckBox cbTcLook;
    private CheckBox cbTcAdd;
    private CheckBox cbTcExc;
    private CheckBox cbXcModfy;
    private CheckBox cbTyModify;
    private CheckBox cbYjLook;
    private CheckBox cbYjExc;
    private CheckBox ckLcLook;
    private CheckBox ckLcQue;
    private CheckBox cbLcExc;
    private CheckBox cbLcSel;
    private CheckBox cbCxLook;
    private CheckBox cbCxQue;
    private CheckBox cbCxExc;
    private CheckBox cbCxSelf;
    private CheckBox cbJxLook;
    private CheckBox cbJxDelete;
    private CheckBox cbJxExp;
    private CheckBox cbJxDet;
    private CheckBox cbJsLook;
    private CheckBox cbJsExc;
    private CheckBox cbYgLook;
    private CheckBox cbYgQue;
    private CheckBox cbYgAdd;
    private CheckBox cbYgModify;
    private CheckBox cbYgDelete;
    private CheckBox cbYgExc;
    private CheckBox cbYgSel;
    private CheckBox cbTgLook;
    private CheckBox cbTgQue;
    private CheckBox cbTgAdd;
    private CheckBox cbTgModify;
    private CheckBox cbTgDelete;
    private CheckBox cbTgExc;
    private CheckBox cbXpLook;
    private CheckBox cbXpModify;
    private CheckBox cbXpExc;
    private CheckBox cbSzLook;
    private CheckBox cbSzQue;
    private CheckBox cbSzExc;
    private CheckBox cbSzSel;
    private CheckBox cbQgLook;
    private CheckBox cbQgQue;
    private CheckBox cbQgModify;
    private CheckBox cbQgAut;
    private CheckBox cbQgExc;
    private CheckBox cbQgSel;
    private CheckBox cbCgLook;
    private CheckBox cbCgExc;
    private CheckBox cbLsLook;
    private CheckBox cbLsExc;
    private CheckBox cbXwLook;
    private CheckBox cbXwExc;

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
        initInstrunmentView();
        initDrugCheckView();
        initDataCheckView();
        initSystemParamView();
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
                Log.d("Zw", "permission helper size " + permissionHelpers.size() + " userTypeId " + permissionHelpers.get(1).getUserTypeId());
                setDataToCheckBox();
            }
        };
        initSpinnerData();
    }

    private void setDataToCheckBox() {
        for (PermissionHelper permissionHelper :
                permissionHelpers) {
            P_SourceOperator p_sourceOperator = permissionHelper.getP_sourceOperator();
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 1) {
                cbBdLook.setChecked(permissionHelper.isCanOperate());
                cbBdCheck.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 1) {
                cbBdCheck.setChecked(permissionHelper.isCanOperate());
                cbBdCheck.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            //校验50um粒子
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 2) {
                cbLzLook.setChecked(permissionHelper.isCanOperate());
                cbLzLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 2) {
                cbLzExcute.setChecked(permissionHelper.isCanOperate());
                cbLzExcute.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 3) {
                cbYyLook.setChecked(permissionHelper.isCanOperate());
                cbYyLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 3) {
                cbYyExcute.setChecked(permissionHelper.isCanOperate());
                cbYyExcute.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 4) {
                cbExLook.setChecked(permissionHelper.isCanOperate());
                cbExLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 4) {
                cbExExcute.setChecked(permissionHelper.isCanOperate());
                cbExExcute.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 5) {
                cbResLook.setChecked(permissionHelper.isCanOperate());
                cbResLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 6) {
                cbPaLook.setChecked(permissionHelper.isCanOperate());
                cbPaLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 6) {
                cbPaExcute.setChecked(permissionHelper.isCanOperate());
                cbPaExcute.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 7) {
                cbKsLook.setChecked(permissionHelper.isCanOperate());
                cbKsLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 7) {
                cbKsExc.setChecked(permissionHelper.isCanOperate());
                cbKsExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 8) {
                cbJyLook.setChecked(permissionHelper.isCanOperate());
                cbJyLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 3 && p_sourceOperator.getP_source_id() == 8) {
                cbJyAdd.setChecked(permissionHelper.isCanOperate());
                cbJyAdd.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 8) {
                cbJyExc.setChecked(permissionHelper.isCanOperate());
                cbJyExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }

            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 9) {
                cbYxLook.setChecked(permissionHelper.isCanOperate());
                cbYxLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 4 && p_sourceOperator.getP_source_id() == 9) {
                cbYxModify.setChecked(permissionHelper.isCanOperate());
                cbYxModify.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 5 && p_sourceOperator.getP_source_id() == 9) {
                cbYxDelete.setChecked(permissionHelper.isCanOperate());
                cbYxDelete.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 10) {
                cbKjLook.setChecked(permissionHelper.isCanOperate());
                cbKjLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 10) {
                cbKjExc.setChecked(permissionHelper.isCanOperate());
                cbKjExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }


            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 11) {
                cbSdLook.setChecked(permissionHelper.isCanOperate());
                cbSdLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 6 && p_sourceOperator.getP_source_id() == 11) {
                cbSdExp.setChecked(permissionHelper.isCanOperate());
                cbSdExp.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 11) {
                cbSdExc.setChecked(permissionHelper.isCanOperate());
                cbSdExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 12) {
                cbTcLook.setChecked(permissionHelper.isCanOperate());
                cbTcLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 3 && p_sourceOperator.getP_source_id() == 12) {
                cbTcAdd.setChecked(permissionHelper.isCanOperate());
                cbTcAdd.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 12) {
                cbTcExc.setChecked(permissionHelper.isCanOperate());
                cbTcExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 4 && p_sourceOperator.getP_source_id() == 13) {
                cbXcModfy.setChecked(permissionHelper.isCanOperate());
                cbXcModfy.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 4 && p_sourceOperator.getP_source_id() == 14) {
                cbTyModify.setChecked(permissionHelper.isCanOperate());
                cbTyModify.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 15) {
                cbYjLook.setChecked(permissionHelper.isCanOperate());
                cbYjLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 15) {
                cbYjExc.setChecked(permissionHelper.isCanOperate());
                cbYjExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }


            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 16) {
                ckLcLook.setChecked(permissionHelper.isCanOperate());
                ckLcLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 2 && p_sourceOperator.getP_source_id() == 16) {
                ckLcQue.setChecked(permissionHelper.isCanOperate());
                ckLcQue.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 16) {
                cbLcExc.setChecked(permissionHelper.isCanOperate());
                cbLcExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 9 && p_sourceOperator.getP_source_id() == 16) {
                cbLcSel.setChecked(permissionHelper.isCanOperate());
                cbLcSel.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }


            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 17) {
                cbCxLook.setChecked(permissionHelper.isCanOperate());
                cbCxLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 2 && p_sourceOperator.getP_source_id() == 17) {
                cbCxQue.setChecked(permissionHelper.isCanOperate());
                cbCxQue.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 17) {
                cbCxExc.setChecked(permissionHelper.isCanOperate());
                cbCxExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 9 && p_sourceOperator.getP_source_id() == 17) {
                cbCxSelf.setChecked(permissionHelper.isCanOperate());
                cbCxSelf.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }


            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 18) {
                cbJxLook.setChecked(permissionHelper.isCanOperate());
                cbJxLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 5 && p_sourceOperator.getP_source_id() == 18) {
                cbJxDelete.setChecked(permissionHelper.isCanOperate());
                cbJxDelete.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 6 && p_sourceOperator.getP_source_id() == 18) {
                cbJxExp.setChecked(permissionHelper.isCanOperate());
                cbJxExp.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 10 && p_sourceOperator.getP_source_id() == 18) {
                cbJxDet.setChecked(permissionHelper.isCanOperate());
                cbJxDet.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }

            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 19) {
                cbJsLook.setChecked(permissionHelper.isCanOperate());
                cbJsLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 19) {
                cbJsExc.setChecked(permissionHelper.isCanOperate());
                cbJsExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }


            //用户管理
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 20) {
                cbYgLook.setChecked(permissionHelper.isCanOperate());
                cbYgLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 2 && p_sourceOperator.getP_source_id() == 20) {
                cbYgQue.setChecked(permissionHelper.isCanOperate());
                cbYgQue.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 3 && p_sourceOperator.getP_source_id() == 20) {
                cbYgAdd.setChecked(permissionHelper.isCanOperate());
                cbYgAdd.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 4 && p_sourceOperator.getP_source_id() == 20) {
                cbYgModify.setChecked(permissionHelper.isCanOperate());
                cbYgModify.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 5 && p_sourceOperator.getP_source_id() == 20) {
                cbYgDelete.setChecked(permissionHelper.isCanOperate());
                cbYgDelete.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 20) {
                cbYgExc.setChecked(permissionHelper.isCanOperate());
                cbYgExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 9 && p_sourceOperator.getP_source_id() == 20) {
                cbYgSel.setChecked(permissionHelper.isCanOperate());
                cbYgSel.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
//托环管理
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 21) {
                cbTgLook.setChecked(permissionHelper.isCanOperate());
                cbTgLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 2 && p_sourceOperator.getP_source_id() == 21) {
                cbTgQue.setChecked(permissionHelper.isCanOperate());
                cbTgQue.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 3 && p_sourceOperator.getP_source_id() == 21) {
                cbTgAdd.setChecked(permissionHelper.isCanOperate());
                cbTgAdd.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 4 && p_sourceOperator.getP_source_id() == 21) {
                cbTgModify.setChecked(permissionHelper.isCanOperate());
                cbTgModify.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 5 && p_sourceOperator.getP_source_id() == 21) {
                cbTgDelete.setChecked(permissionHelper.isCanOperate());
                cbTgDelete.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 21) {
                cbTgExc.setChecked(permissionHelper.isCanOperate());
                cbTgExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }


            //系统配置
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 22) {
                cbXpLook.setChecked(permissionHelper.isCanOperate());
                cbXpLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 4 && p_sourceOperator.getP_source_id() == 22) {
                cbXpModify.setChecked(permissionHelper.isCanOperate());
                cbXpModify.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 22) {
                cbXpExc.setChecked(permissionHelper.isCanOperate());
                cbXpExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }


            //审计追踪
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 23) {
                cbSzLook.setChecked(permissionHelper.isCanOperate());
                cbSzLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 2 && p_sourceOperator.getP_source_id() == 23) {
                cbSzQue.setChecked(permissionHelper.isCanOperate());
                cbSzQue.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 23) {
                cbSzExc.setChecked(permissionHelper.isCanOperate());
                cbSzExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 9 && p_sourceOperator.getP_source_id() == 23) {
                cbSzSel.setChecked(permissionHelper.isCanOperate());
                cbSzSel.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }


            //权限管理
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 24) {
                cbQgLook.setChecked(permissionHelper.isCanOperate());
                cbQgLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 2 && p_sourceOperator.getP_source_id() == 24) {
                cbQgQue.setChecked(permissionHelper.isCanOperate());
                cbQgQue.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 4 && p_sourceOperator.getP_source_id() == 24) {
                cbQgModify.setChecked(permissionHelper.isCanOperate());
                cbQgModify.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 7 && p_sourceOperator.getP_source_id() == 24) {
                cbQgAut.setChecked(permissionHelper.isCanOperate());
                cbQgAut.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 24) {
                cbQgExc.setChecked(permissionHelper.isCanOperate());
                cbQgExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 9 && p_sourceOperator.getP_source_id() == 24) {
                cbQgSel.setChecked(permissionHelper.isCanOperate());
                cbQgSel.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }

            //程序更新
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 25) {
                cbCgLook.setChecked(permissionHelper.isCanOperate());
                cbCgLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 25) {
                cbCgExc.setChecked(permissionHelper.isCanOperate());
                cbCgExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }

            //logShow
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 26) {
                cbLsLook.setChecked(permissionHelper.isCanOperate());
                cbLsLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 26) {
                cbLsExc.setChecked(permissionHelper.isCanOperate());
                cbLsExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }

//系统参数维护
            if (p_sourceOperator.getP_operator_id() == 1 && p_sourceOperator.getP_source_id() == 27) {
                cbXwLook.setChecked(permissionHelper.isCanOperate());
                cbXwLook.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
            }
            if (p_sourceOperator.getP_operator_id() == 8 && p_sourceOperator.getP_source_id() == 27) {
                cbXwExc.setChecked(permissionHelper.isCanOperate());
                cbXwExc.setTag(new long[]{p_sourceOperator.getP_operator_id(), p_sourceOperator.getP_source_id()});
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

    private void initDrugCheckView() {
        cbKsLook = (CheckBox) view.findViewById(R.id.cbKsLook);
        cbKsExc = (CheckBox) view.findViewById(R.id.cbKsExc);
        cbJyLook = (CheckBox) view.findViewById(R.id.cbJyLook);
        cbJyAdd = (CheckBox) view.findViewById(R.id.cbJyAdd);
        cbJyExc = (CheckBox) view.findViewById(R.id.cbJyExc);
        cbYxLook = (CheckBox) view.findViewById(R.id.cbYxLook);
        cbYxModify = (CheckBox) view.findViewById(R.id.cbYxModify);
        cbYxDelete = (CheckBox) view.findViewById(R.id.cbYxDelete);
        cbKjLook = (CheckBox) view.findViewById(R.id.cbKjLook);
        cbKjExc = (CheckBox) view.findViewById(R.id.cbKjExc);
        cbSdLook = (CheckBox) view.findViewById(R.id.cbSdLook);
        cbSdExp = (CheckBox) view.findViewById(R.id.cbSdExp);
        cbSdExc = (CheckBox) view.findViewById(R.id.cbSdExc);
        cbTcLook = (CheckBox) view.findViewById(R.id.cbTcLook);
        cbTcAdd = (CheckBox) view.findViewById(R.id.cbTcAdd);
        cbTcExc = (CheckBox) view.findViewById(R.id.cbTcExc);
        cbXcModfy = (CheckBox) view.findViewById(R.id.cbXcModfy);
        cbTyModify = (CheckBox) view.findViewById(R.id.cbTyModify);
        cbYjLook = (CheckBox) view.findViewById(R.id.cbYjLook);
        cbYjExc = (CheckBox) view.findViewById(R.id.cbYjExc);

    }

    private void initDataCheckView() {
        ckLcLook = (CheckBox) view.findViewById(R.id.ckLcLook);
        ckLcQue = (CheckBox) view.findViewById(R.id.ckLcQue);
        cbLcExc = (CheckBox) view.findViewById(R.id.cbLcExc);
        cbLcSel = (CheckBox) view.findViewById(R.id.cbLcSel);
        cbCxLook = (CheckBox) view.findViewById(R.id.cbCxLook);
        cbCxQue = (CheckBox) view.findViewById(R.id.cbCxQue);
        cbCxExc = (CheckBox) view.findViewById(R.id.cbCxExc);
        cbCxSelf = (CheckBox) view.findViewById(R.id.cbCxSelf);
        cbJxLook = (CheckBox) view.findViewById(R.id.cbJxLook);
        cbJxDelete = (CheckBox) view.findViewById(R.id.cbJxDelete);
        cbJxExp = (CheckBox) view.findViewById(R.id.cbJxExp);
        cbJxDet = (CheckBox) view.findViewById(R.id.cbJxDet);
        cbJsLook = (CheckBox) view.findViewById(R.id.cbJsLook);
        cbJsExc = (CheckBox) view.findViewById(R.id.cbJsExc);

    }

    private void initSystemParamView() {

        cbYgLook = (CheckBox) view.findViewById(R.id.cbYgLook);
        cbYgQue = (CheckBox) view.findViewById(R.id.cbYgQue);
        cbYgAdd = (CheckBox) view.findViewById(R.id.cbYgAdd);
        cbYgModify = (CheckBox) view.findViewById(R.id.cbYgModify);
        cbYgDelete = (CheckBox) view.findViewById(R.id.cbYgDelete);
        cbYgExc = (CheckBox) view.findViewById(R.id.cbYgExc);
        cbYgSel = (CheckBox) view.findViewById(R.id.cbYgSel);
        cbTgLook = (CheckBox) view.findViewById(R.id.cbTgLook);
        cbTgQue = (CheckBox) view.findViewById(R.id.cbTgQue);
        cbTgAdd = (CheckBox) view.findViewById(R.id.cbTgAdd);
        cbTgModify = (CheckBox) view.findViewById(R.id.cbTgModify);
        cbTgDelete = (CheckBox) view.findViewById(R.id.cbTgDelete);
        cbTgExc = (CheckBox) view.findViewById(R.id.cbTgExc);
        cbXpLook = (CheckBox) view.findViewById(R.id.cbXpLook);
        cbXpModify = (CheckBox) view.findViewById(R.id.cbXpModify);
        cbXpExc = (CheckBox) view.findViewById(R.id.cbXpExc);
        cbSzLook = (CheckBox) view.findViewById(R.id.cbSzLook);
        cbSzQue = (CheckBox) view.findViewById(R.id.cbSzQue);
        cbSzExc = (CheckBox) view.findViewById(R.id.cbSzExc);
        cbSzSel = (CheckBox) view.findViewById(R.id.cbSzSel);
        cbQgLook = (CheckBox) view.findViewById(R.id.cbQgLook);
        cbQgQue = (CheckBox) view.findViewById(R.id.cbQgQue);
        cbQgModify = (CheckBox) view.findViewById(R.id.cbQgModify);
        cbQgAut = (CheckBox) view.findViewById(R.id.cbQgAut);
        cbQgExc = (CheckBox) view.findViewById(R.id.cbQgExc);
        cbQgSel = (CheckBox) view.findViewById(R.id.cbQgSel);
        cbCgLook = (CheckBox) view.findViewById(R.id.cbCgLook);
        cbCgExc = (CheckBox) view.findViewById(R.id.cbCgExc);
        cbLsLook = (CheckBox) view.findViewById(R.id.cbLsLook);
        cbLsExc = (CheckBox) view.findViewById(R.id.cbLsExc);
        cbXwLook = (CheckBox) view.findViewById(R.id.cbXwLook);
        cbXwExc = (CheckBox) view.findViewById(R.id.cbXwExc);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }

    }
}
