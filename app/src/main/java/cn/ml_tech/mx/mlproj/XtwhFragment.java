package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import cn.ml_tech.mx.mlproj.SettingFragment.AddTypeFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.AuditTrackFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.CamParamsFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.DataManageFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.InstrumManageFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.LogShowFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.ManchineManagerFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.ParmanageFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.PerManageFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.SysConfigFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.TrayManagerFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.UserManagerFragment;
import cn.ml_tech.mx.mlservice.DAO.P_Source;
import cn.ml_tech.mx.mlservice.DAO.Permission;

public class XtwhFragment extends BaseFragment {
    private static final int PERSUCESS = 300;
    private static final int PERFAILURE = 400;
    private OnFragmentInteractionListener mListener;
    private FragmentManager mChildFragmentManager;
    private FragmentTransaction mfragmentTransaction;
    private UserManagerFragment userManagerFragment;
    private AuditTrackFragment auditTrackFragment;
    private DataManageFragment dataManageFragment;
    private PerManageFragment perManageFragment;
    private InstrumManageFragment instrumManageFragment;
    private LogShowFragment logShowFragment;
    private CamParamsFragment camParamsFragment;
    private TrayManagerFragment trayManagerFragment;
    private SysConfigFragment sysConfigFragment;
    private ParmanageFragment parmanageFragment;
    private ManchineManagerFragment manchineManagerFragment;
    private AddTypeFragment addTypeFragment;
    private Fragment mCurrentFrgment;//显示当前Fragment
    private ImageButton btBack;
    private RadioGroup rootgroup;
    private Permission permission;
    private AmiApp amiApp;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PERSUCESS:
                    initStartFragment();
                    initPermission();
                    break;
                case PERFAILURE:
                    break;
            }
        }


    };

    /**
     * 根据权限设置子模块是否可见
     */
    private void initPermission() {
        view.findViewById(R.id.rbUserManage).setVisibility(permission.getPermissiondata().get(getTitleById(20) + getOperateNameById(1)) == true ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.rbTrayManage).setVisibility(permission.getPermissiondata().get(getTitleById(21) + getOperateNameById(1)) == true ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.rbSystemConfig).setVisibility(permission.getPermissiondata().get(getTitleById(22) + getOperateNameById(1)) == true ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.rbAuditTrack).setVisibility(permission.getPermissiondata().get(getTitleById(23) + getOperateNameById(1)) == true ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.rbpermissTrack).setVisibility(permission.getPermissiondata().get(getTitleById(24) + getOperateNameById(1)) == true ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.rbProgramUpdate).setVisibility(permission.getPermissiondata().get(getTitleById(25) + getOperateNameById(1)) == true ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.rbLogShow).setVisibility(permission.getPermissiondata().get(getTitleById(26) + getOperateNameById(1)) == true ? View.VISIBLE : View.GONE);
    }


    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_xtwhactivity, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        btBack = (ImageButton) view.findViewById(R.id.bt_back);
        rootgroup = (RadioGroup) view.findViewById(R.id.rootgroup);
    }

    public void moveToAddType() {
        if (addTypeFragment == null) addTypeFragment = new AddTypeFragment();
        switchFragment(addTypeFragment);
    }

    public void moveToUserType() {
        if (userManagerFragment == null)
            userManagerFragment = new UserManagerFragment();
        switchFragment(userManagerFragment);
    }

    @Override
    protected void initEvent() {
        rootgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbUserManage:
                        if (userManagerFragment == null)
                            userManagerFragment = new UserManagerFragment();
                        switchFragment(userManagerFragment);
                        break;
                    case R.id.rbAuditTrack:
                        if (auditTrackFragment == null)
                            auditTrackFragment = new AuditTrackFragment();
                        switchFragment(auditTrackFragment);
                        break;
                    case R.id.rbDataManage:
                        if (dataManageFragment == null)
                            dataManageFragment = new DataManageFragment();
                        switchFragment(dataManageFragment);
                        break;
                    case R.id.rbpermissTrack:
                        if (perManageFragment == null)
                            perManageFragment = new PerManageFragment();
                        switchFragment(perManageFragment);
                        break;
                    case R.id.rbInstrumManage:
                        if (instrumManageFragment == null)
                            instrumManageFragment = new InstrumManageFragment();
                        switchFragment(instrumManageFragment);
                        break;
                    case R.id.rbLogShow:
                        if (logShowFragment == null)
                            logShowFragment = new LogShowFragment();
                        switchFragment(logShowFragment);
                        break;
                    case R.id.rbCameraConfig:
                        if (camParamsFragment == null)
                            camParamsFragment = new CamParamsFragment();
                        switchFragment(camParamsFragment);
                        break;
                    case R.id.rbSystemConfig:
                        if (sysConfigFragment == null)
                            sysConfigFragment = new SysConfigFragment();
                        switchFragment(sysConfigFragment);
                        break;
                    case R.id.rbTrayManage:
                        if (trayManagerFragment == null)
                            trayManagerFragment = new TrayManagerFragment();
                        switchFragment(trayManagerFragment);
                        break;
                    case R.id.rbInfoManage:
                        if (manchineManagerFragment == null)
                            manchineManagerFragment = new ManchineManagerFragment();
                        switchFragment(manchineManagerFragment);
                        break;
                    case R.id.rbParaManage:
                        if (parmanageFragment == null)
                            parmanageFragment = new ParmanageFragment();
                        switchFragment(parmanageFragment);
                        break;

                }
            }
        });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        amiApp = (AmiApp) getActivity().getApplication();
        new Thread() {
            @Override
            public void run() {
                super.run();
                String url = "";
                for (P_Source p_source :
                        amiApp.getP_sources()) {
                    if (p_source.getId() == 27) {
                        url = p_source.getUrl();
                        break;
                    }
                }
                try {
                    permission = mlService.getPermissonByUrl(url, false);
                    handler.sendEmptyMessage(PERSUCESS);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(PERFAILURE);

                }
            }
        }.start();
    }

    private void initStartFragment() {
        if (permission.getPermissiondata().get(getTitleById(20) + getOperateNameById(1))) {
            if (userManagerFragment == null) ;
            userManagerFragment = new UserManagerFragment();
            switchFragment(userManagerFragment);
        } else {
            if (instrumManageFragment == null)
                instrumManageFragment = new InstrumManageFragment();
            switchFragment(instrumManageFragment);
        }

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    private void switchFragment(Fragment fragment) {
        mfragmentTransaction = getFragmentManager().beginTransaction();
        if (null != mCurrentFrgment) {
            mfragmentTransaction.hide(mCurrentFrgment);
        }
        if (!fragment.isAdded()) {
            mfragmentTransaction.add(R.id.settingFragment, fragment, fragment.getClass().getName());
        } else {
            mfragmentTransaction.show(fragment);
        }
        mfragmentTransaction.commit();


        mCurrentFrgment = fragment;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
