package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

public class XtwhFragment extends BaseFragment {
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
    }

    private void initStartFragment() {
        if (userManagerFragment == null) ;
        userManagerFragment = new UserManagerFragment();
        switchFragment(userManagerFragment);
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    @Override
    public void onStart() {
        super.onStart();
        initStartFragment();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
