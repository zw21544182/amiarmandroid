package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ml_tech.mx.mlservice.DAO.DrugContainer;
import cn.ml_tech.mx.mlservice.DAO.DrugParam;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YpjqFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YpjqFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YpjqFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText etBottlePara, etShadLocation;
    private TextView tvShadPara, tvColorCoefficient;
    private Spinner spParaType;
    private OnFragmentInteractionListener mListener;
    YpjcActivity ypjcActivity;
    private HashMap<String, String> data;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public YpjqFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YpjcFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YpjqFragment newInstance(String param1, String param2) {
        YpjqFragment fragment = new YpjqFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ypjcActivity = (YpjcActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_ypxq, container, false);
        etBottlePara = (EditText) view.findViewById(R.id.etBottlePara);
        etShadLocation = (EditText) view.findViewById(R.id.etShadLocation);
        tvShadPara = (TextView) view.findViewById(R.id.tvShadPara);
        spParaType = (Spinner) view.findViewById(R.id.spParaType);
        setDataToView(ypjcActivity.pos, ypjcActivity.druginfo_id);
        tvColorCoefficient = (TextView) view.findViewById(R.id.tvColorCoefficient);
        etBottlePara.addTextChangedListener(new ViewTextWatcher(etBottlePara));
        etShadLocation.addTextChangedListener(new ViewTextWatcher(etShadLocation));
        tvShadPara.addTextChangedListener(new ViewTextWatcher(tvShadPara));
        tvColorCoefficient.addTextChangedListener(new ViewTextWatcher(tvColorCoefficient));
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
    public void onStart() {
        super.onStart();
        data = new HashMap<>();
        getActivity().findViewById(R.id.btSave).setOnClickListener((View.OnClickListener) getActivity());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setDataToView(int pos, int drug_id) {
        try {
            DrugContainer drugContainer = ypjcActivity.mService.getDrugContainer().get(pos);
            etBottlePara.setText(drugContainer.getRotatespeed() + "");
            etShadLocation.setText(drugContainer.getHeight() + "");
            if (drug_id != 0) {
                List<DrugParam> drugParams = ypjcActivity.mService.getDrugParamById(drug_id);
                for (DrugParam drugParam :
                        drugParams
                        ) {
                    switch (drugParam.getParamname()) {
                        case "rotateSpeed":
                            etBottlePara.setText(drugParam.getParamvalue() + "");
                            break;
                        case "height":
                            etShadLocation.setText(drugParam.getParamvalue() + "");
                            break;
                        case "shadeParam":
                            tvShadPara.setText(drugParam.getParamvalue() + "");
                            break;
                        case "sendparam":
                            tvColorCoefficient.setText(drugParam.getParamvalue() + "");
                            break;

                    }
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
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

    private class ViewTextWatcher implements TextWatcher {
        private View view;
        private int id;

        public ViewTextWatcher(View view) {
            this.view = view;
            id = view.getId();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (id) {
                case R.id.etBottlePara:
                    data.put("rotateSpeed", s.toString());
                    break;
                case R.id.etShadLocation:
                    data.put("height", s.toString());
                    break;
                case R.id.tvShadPara:
                    data.put("shadeParam", s.toString());
                    break;
                case R.id.tvColorCoefficient:
                    data.put("sendparam", s.toString());
                    break;
            }
        }
    }
}
