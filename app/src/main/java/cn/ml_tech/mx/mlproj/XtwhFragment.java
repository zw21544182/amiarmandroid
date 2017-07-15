package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.ArrayList;

import cn.ml_tech.mx.mlproj.SettingFragment.AuditTrackFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.CamParamsFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.DataManageFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.InstrumManageFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.LogShowFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.ManchineManagerFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.ParmanageFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.PerManageFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.SysConfigFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.SystemSetUpMainFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.TrayManagerFragment;
import cn.ml_tech.mx.mlproj.SettingFragment.UserManagerFragment;

import static android.os.Build.VERSION_CODES.M;

public class XtwhFragment extends Fragment {
    private String[] arrayItemMenu;
    private String[] arrayFragmentTag;
    private OnFragmentInteractionListener mListener;
    private FragmentManager mChildFragmentManager;
    private FragmentTransaction mfragmentTransaction;
    private UserManagerFragment userManagerFragment = null;
    private SystemSetUpMainFragment systemSetUpMainFragment;
    private ManchineManagerFragment manchineManagerFragment;
    private int currentIndex = 0;//控制当前需要显示第几个Fragment
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();//用List来存储Fragment,List的初始化没有写
    private Fragment mCurrentFrgment;//显示当前Fragment

    public XtwhFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        initFragment();
        changeTab(0);

//        initChildFragments();
        getActivity().findViewById(R.id.bt_back).setOnClickListener((View.OnClickListener) getActivity());
        ((RadioGroup) getActivity().findViewById(R.id.rootgroup)).setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) getActivity());

    }

    private void initFragment() {
        fragmentArrayList.add(new UserManagerFragment());
        fragmentArrayList.add(new InstrumManageFragment());
        fragmentArrayList.add(new TrayManagerFragment());
        fragmentArrayList.add(new ManchineManagerFragment());
        fragmentArrayList.add(new ParmanageFragment());
        fragmentArrayList.add(new DataManageFragment());
        fragmentArrayList.add(new SysConfigFragment());
        fragmentArrayList.add(new CamParamsFragment());
        fragmentArrayList.add(new LogShowFragment());
        fragmentArrayList.add(new AuditTrackFragment());
        fragmentArrayList.add(new PerManageFragment());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        arrayItemMenu=new String[]{
//                getString(R.string.MenuSystemMain),
//            getString(R.string.MenuUser),
//                    getString(R.string.MenuDeviceDebug),
//                    getString(R.string.MenuTray),
//                    getString(R.string.MenuInformation),
//                    getString(R.string.MenuDrugParam),
//                    getString(R.string.MenuData),
//                    getString(R.string.MenuSysConfig),
//                    getString(R.string.MenuCaptureConfig),
//                    getString(R.string.MenuAuditTrail),
//                    getString(R.string.MenuRightManager),
//                    getString(R.string.MenuProgramUpdate),
//                    getString(R.string.MenuLogShow),
//        };
//        arrayFragmentTag=getResources().getStringArray(R.array.menufragment);
        mChildFragmentManager = getFragmentManager();

    }

//    void initChildFragments() {
//        mfragmentTransaction = mChildFragmentManager.beginTransaction();
//        systemSetUpMainFragment = new SystemSetUpMainFragment();
////        UserManagerFragment fragmentuser=new UserManagerFragment();
////        ManchineManagerFragment fragmentmanchine=new ManchineManagerFragment();
////        TrayManagerFragment trayManagerFragment=new TrayManagerFragment();
////        DeviceDebugFragment deviceDeubgFragment=new DeviceDebugFragment();
////        SysConfigFragment sysConfigFragment=new  SysConfigFragment();
//        mfragmentTransaction.add(R.id.settingFragment, systemSetUpMainFragment);
////        mfragmentTransaction.add(R.id.llSystemFragmentParent,fragmentuser).commit();
////        mfragmentTransaction.add(R.id.llSystemFragmentParent,fragmentmanchine).commit();
////        mfragmentTransaction.add(R.id.llSystemFragmentParent,trayManagerFragment).commit();
////        mfragmentTransaction.add(R.id.llSystemFragmentParent,deviceDeubgFragment).commit();
////        mfragmentTransaction.add(R.id.llSystemFragmentParent,sysConfigFragment).commit();
//        mfragmentTransaction.attach(systemSetUpMainFragment);
//        mfragmentTransaction.commit();
//    }

    @RequiresApi(api = M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_xtwhactivity, container, false);
        /*
        LinearLayout llRoot= (LinearLayout) view.findViewById(R.id.llSystemRoot);
        Log.d(getContext().getPackageName(), "onCreateView: "+String.valueOf(arrayFragmentTag.length)+" "+String.valueOf(arrayItemMenu.length));
        for (int i=0;i<arrayItemMenu.length;i++)
        {
            MenuItemView itemView=new MenuItemView(getContext());
            itemView.setFragmentTag(arrayFragmentTag[i]);
            itemView.setTitle(arrayItemMenu[i]);
            Log.d("debug", "onCreateView: "+arrayItemMenu[i] +" "+arrayFragmentTag[i]);
            llRoot.addView(itemView);
        }
        return view;*/
        return view;
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

    private void changeTab(int index) {
        currentIndex = index;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        //判断当前的Fragment是否为空，不为空则隐藏
        if (null != mCurrentFrgment) {
            ft.hide(mCurrentFrgment);
        }
        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
        Fragment fragment = getFragmentManager().findFragmentByTag(fragmentArrayList.get(currentIndex).getClass().getName());
        Log.d("ZW", fragmentArrayList.get(currentIndex).getClass().getName());
        if (null == fragment) {
            //如fragment为空，则之前未添加此Fragment。便从集合中取出
            fragment = fragmentArrayList.get(index);
        }
        mCurrentFrgment = fragment;

        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!fragment.isAdded()) {
            ft.add(R.id.settingFragment, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }

    public void changeFragmentById(int id) {
        switch (id) {
            case R.id.rbUserManage:
                changeTab(0);
                break;
            case R.id.rbInstrumManage:
                changeTab(1);
                break;
            case R.id.rbTrayManage:
                changeTab(2);
                break;
            case R.id.rbInfoManage:
                changeTab(3);
                break;
            case R.id.rbParaManage:
                changeTab(4);
                break;
            case R.id.rbDataManage:
                changeTab(5);
                break;
            case R.id.rbSystemConfig:
                changeTab(6);
                break;
            case R.id.rbCameraConfig:
                changeTab(7);
                break;
            case R.id.rbLogShow:
                changeTab(8);
                break;
            case R.id.rbAuditTrack:
                changeTab(9);
                break;
            case R.id.rbpermissTrack:
                changeTab(10);

            default:
                break;
        }

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
