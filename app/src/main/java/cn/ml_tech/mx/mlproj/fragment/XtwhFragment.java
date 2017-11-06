package cn.ml_tech.mx.mlproj.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.base.AmiApp;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.settingfragment.AddTypeFragment;
import cn.ml_tech.mx.mlproj.settingfragment.AuditTrackFragment;
import cn.ml_tech.mx.mlproj.settingfragment.CamParamsFragment;
import cn.ml_tech.mx.mlproj.settingfragment.DataManageFragment;
import cn.ml_tech.mx.mlproj.settingfragment.InstrumManageFragment;
import cn.ml_tech.mx.mlproj.settingfragment.LogShowFragment;
import cn.ml_tech.mx.mlproj.settingfragment.ManchineManagerFragment;
import cn.ml_tech.mx.mlproj.settingfragment.ParmanageFragment;
import cn.ml_tech.mx.mlproj.settingfragment.PerManageFragment;
import cn.ml_tech.mx.mlproj.settingfragment.ProjectUpdateFragment;
import cn.ml_tech.mx.mlproj.settingfragment.SysConfigFragment;
import cn.ml_tech.mx.mlproj.settingfragment.TrayManagerFragment;
import cn.ml_tech.mx.mlproj.settingfragment.UserManagerFragment;
import cn.ml_tech.mx.mlproj.util.CommonUtil;
import cn.ml_tech.mx.mlservice.DAO.Permission;

public class XtwhFragment extends BaseFragment {
    private static final int PERSUCESS = 300;
    private static final int PERFAILURE = 400;
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
    private ProjectUpdateFragment projectUpdateFragment;
    private Fragment mCurrentFrgment;//显示当前Fragment
    private Button btBack;
    private RadioGroup rootgroup;
    private Permission permission;
    private AmiApp amiApp;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PERSUCESS:
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
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_xtwhactivity, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        btBack = (Button) view.findViewById(R.id.bt_back);
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
        ((RadioButton
                ) view.findViewById(R.id.rbInstrumManage)).setChecked(true);

        rootgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbUserManage:
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    mlService.addAudittrail(5, 1, "", CommonUtil.ENTERMENUBTNUSER);
                                } catch (Exception e) {
                                }
                            }
                        }.start();
                        if (userManagerFragment == null)
                            userManagerFragment = new UserManagerFragment();
                        switchFragment(userManagerFragment);
                        break;
                    case R.id.rbAuditTrack:
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    mlService.addAudittrail(5, 1, "", CommonUtil.ENTERMENUBTNAUDITRAIL);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
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
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    mlService.addAudittrail(5, 1, "", CommonUtil.ENTERMENUBTNLOGSHOW);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        if (logShowFragment == null)
                            logShowFragment = new LogShowFragment();
                        switchFragment(logShowFragment);
                        break;
                    case R.id.rbCameraConfig:
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    mlService.addAudittrail(5, 1, "", CommonUtil.ENTERCAPTURECONFIG);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        if (camParamsFragment == null)
                            camParamsFragment = new CamParamsFragment();
                        switchFragment(camParamsFragment);
                        break;
                    case R.id.rbSystemConfig:
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    mlService.addAudittrail(5, 1, "", CommonUtil.ENTERMENUBTNSYSCONFIG);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        if (sysConfigFragment == null)
                            sysConfigFragment = new SysConfigFragment();
                        switchFragment(sysConfigFragment);
                        break;
                    case R.id.rbTrayManage:
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    mlService.addAudittrail(5, 1, "", CommonUtil.ENTERMENUBTNTRAY);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        if (trayManagerFragment == null)
                            trayManagerFragment = new TrayManagerFragment();
                        switchFragment(trayManagerFragment);
                        break;
                    case R.id.rbInfoManage:
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    mlService.addAudittrail(5, 1, "", CommonUtil.ENTERDEVICEDEBUG);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        if (manchineManagerFragment == null)
                            manchineManagerFragment = new ManchineManagerFragment();
                        switchFragment(manchineManagerFragment);
                        break;
                    case R.id.rbParaManage:
                        if (parmanageFragment == null)
                            parmanageFragment = new ParmanageFragment();
                        switchFragment(parmanageFragment);
                        break;
                    case R.id.rbProgramUpdate:
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    mlService.addAudittrail(5, 1, "", CommonUtil.ENTERMENUBTNPROGRAMUPDAT);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        if (projectUpdateFragment == null)
                            projectUpdateFragment = new ProjectUpdateFragment();
                        switchFragment(projectUpdateFragment);
                        break;
                }
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            mlService.addAudittrail(5, 5, "", CommonUtil.EXITSYSTEMSETUP);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        amiApp = (AmiApp) getActivity().getApplication();
    }

    private void initStartFragment() {
        ((RadioButton) view.findViewById(R.id.rbUserManage)).setChecked(true);
        if (userManagerFragment == null) ;
        userManagerFragment = new UserManagerFragment();
        switchFragment(userManagerFragment);
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
}
