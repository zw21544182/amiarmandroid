package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.Adapter.DrugAdapter;
import cn.ml_tech.mx.mlservice.DrugControls;
import cn.ml_tech.mx.mlservice.IMlService;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YpkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YpkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YpkFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<DrugControls> drugList = new ArrayList<>();
    private DrugControls drugControls = null;

    public IMlService getmService() {
        return mService;
    }

    public void setmService(IMlService mService) {
        this.mService = mService;
    }

    RecyclerView recyclerView;
    private IMlService mService;

    private OnFragmentInteractionListener mListener;
    private DrugAdapter.OperateToData operateToData = new DrugAdapter.OperateToData() {
        @Override
        public boolean delete(long id) {
            Log.d("zw", id + "id");
            try {
                getmService().deleteDrugInfoById((int) id);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public void operateToPre(boolean isNext) {
            getActivity().findViewById(R.id.btnypxNext).setEnabled(isNext);
        }

        @Override
        public void update(DrugControls drugControls) {

        }
    };

    public YpkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YpkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YpkFragment newInstance(String param1, String param2) {
        YpkFragment fragment = new YpkFragment();
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

    private void initDrugs() {
        try {
            drugList = mService.queryDrugControl();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initDrugs();
        getActivity().findViewById(R.id.btnypxNext).setEnabled(false);
        getActivity().findViewById(R.id.query).setOnClickListener((View.OnClickListener) getActivity());

        getActivity().findViewById(R.id.btnypxNext).setOnClickListener((View.OnClickListener) getActivity());

        getActivity().findViewById(R.id.addphonetic).setOnClickListener((View.OnClickListener) getActivity());
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        DrugAdapter adapter = new DrugAdapter(drugList, getActivity(), operateToData);
        recyclerView.setAdapter(adapter);
    }

    public void setDataToView(List<DrugControls> drugList) {
        DrugAdapter adapter = new DrugAdapter(drugList, getActivity(), operateToData);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ypk, container, false);
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

    public String getDrugName() {
        return ((EditText) getActivity().findViewById(R.id.me_name)).getEditableText().toString();
    }

    public String getPinyin() {
        return ((EditText) getActivity().findViewById(R.id.me_phonetic)).getEditableText().toString();
    }

    public String getEnName() {
        return ((EditText) getActivity().findViewById(R.id.me_enname)).getEditableText().toString();
    }
}
