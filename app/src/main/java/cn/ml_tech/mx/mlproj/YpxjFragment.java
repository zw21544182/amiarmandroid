package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import cn.ml_tech.mx.mlservice.BottlePara;
import cn.ml_tech.mx.mlservice.FactoryControls;
import cn.ml_tech.mx.mlservice.IMlService;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YpxjFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YpxjFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YpxjFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private IMlService mService;
    private List<FactoryControls> factoryControlses;
    List<String> factorydata;

    public IMlService getmService() {
        return mService;
    }

    public void setmService(IMlService mService) {
        this.mService = mService;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public YpxjFragment() {
        // Required empty public constructor
    }


    public static YpxjFragment newInstance(String param1, String param2) {
        YpxjFragment fragment = new YpxjFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    public BottlePara getBottlePara() {
        BottlePara bottlePara = new BottlePara();
        double maxStatTime = Double.parseDouble(((EditText) getActivity().findViewById(R.id.etMaxStatTime)).getEditableText().toString());
        double maxStopTime = Double.parseDouble(((EditText) getActivity().findViewById(R.id.etMaxStopTime)).getEditableText().toString());
        double stopDelayTime = Double.parseDouble(((EditText) getActivity().findViewById(R.id.etStopDelayTime)).getEditableText().toString());
        double imageDelayTime = Double.parseDouble(((EditText) getActivity().findViewById(R.id.etImageDelayTime)).getEditableText().toString());
        double threshold40 = Double.parseDouble(((EditText) getActivity().findViewById(R.id.threshold40)).getEditableText().toString());
        double threshold50 = Double.parseDouble(((EditText) getActivity().findViewById(R.id.threshold50)).getEditableText().toString());
        double threshold60 = Double.parseDouble(((EditText) getActivity().findViewById(R.id.threshold60)).getEditableText().toString());
        double threshold70 = Double.parseDouble(((EditText) getActivity().findViewById(R.id.threshold70)).getEditableText().toString());
        bottlePara.setMaxStatTime(maxStatTime);
        bottlePara.setMaxStopTime(maxStopTime);
        bottlePara.setStopDelayTime(stopDelayTime);
        bottlePara.setImageDelayTime(imageDelayTime);
        bottlePara.setThreshold40(threshold40);
        bottlePara.setThreshold50(threshold50);
        bottlePara.setThreshold60(threshold60);
        bottlePara.setThreshold70(threshold70);

        return bottlePara;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ypxj, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.btnypxjPre).setOnClickListener((View.OnClickListener) getActivity());
        getActivity().findViewById(R.id.btnypxjNext).setOnClickListener((View.OnClickListener) getActivity());
        try {
            factoryControlses = mService.queryFactoryControl();
        } catch (RemoteException e) {
            e.printStackTrace();
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
