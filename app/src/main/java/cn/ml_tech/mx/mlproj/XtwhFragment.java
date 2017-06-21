package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cn.ml_tech.mx.CustomView.SystemSetUp.MenuItemView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link XtwhFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XtwhFragment extends Fragment {


    private String[]arrayItemMenu;

    private String[]arrayFragmentTag;
    private OnFragmentInteractionListener mListener;
    private FragmentManager mChildFragmentManager;
    private FragmentTransaction mfragmentTransaction;

    public XtwhFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayItemMenu=new String[]{
                getString(R.string.MenuSystemMain),
            getString(R.string.MenuUser),
                    getString(R.string.MenuDeviceDebug),
                    getString(R.string.MenuTray),
                    getString(R.string.MenuInformation),
                    getString(R.string.MenuDrugParam),
                    getString(R.string.MenuData),
                    getString(R.string.MenuSysConfig),
                    getString(R.string.MenuCaptureConfig),
                    getString(R.string.MenuAuditTrail),
                    getString(R.string.MenuRightManager),
                    getString(R.string.MenuProgramUpdate),
                    getString(R.string.MenuLogShow),
        };
        arrayFragmentTag=getResources().getStringArray(R.array.menufragment);
        mChildFragmentManager = getChildFragmentManager();

    }
    void initChildFragments()
    {
        mfragmentTransaction = mChildFragmentManager.beginTransaction();
        SystemSetUpMainFragment mainFragment=new SystemSetUpMainFragment();
//        UserManagerFragment fragmentuser=new UserManagerFragment();
//        ManchineManagerFragment fragmentmanchine=new ManchineManagerFragment();
//        TrayManagerFragment trayManagerFragment=new TrayManagerFragment();
//        DeviceDebugFragment deviceDeubgFragment=new DeviceDebugFragment();
//        SysConfigFragment sysConfigFragment=new  SysConfigFragment();
        mfragmentTransaction.add(R.id.llSystemFragmentParent,mainFragment);
//        mfragmentTransaction.add(R.id.llSystemFragmentParent,fragmentuser).commit();
//        mfragmentTransaction.add(R.id.llSystemFragmentParent,fragmentmanchine).commit();
//        mfragmentTransaction.add(R.id.llSystemFragmentParent,trayManagerFragment).commit();
//        mfragmentTransaction.add(R.id.llSystemFragmentParent,deviceDeubgFragment).commit();
//        mfragmentTransaction.add(R.id.llSystemFragmentParent,sysConfigFragment).commit();
        mfragmentTransaction.attach(mainFragment);
        mfragmentTransaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_systemsetup, container, false);
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
        initChildFragments();
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
