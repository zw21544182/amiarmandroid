package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
import cn.ml_tech.mx.mlservice.DAO.DrugParam;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YpjccFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YpjccFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YpjccFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private YpjcActivity ypjcActivity;
    private OnFragmentInteractionListener mListener;
    private View view;
    private LinearLayout ltDrugPara;
    private CheckBox cbShowDrugParam;
    private TextView tvDrugName, tvFactionName, tvDetectionBatch, tvColorCoefficient, tvEnName, tvDetectionSn, tvDetectionNumber, tvShapePara;
    private List<DrugParam> drugParamList = null;
    private Button btStartCheck;
    private EditText etRotateNum;

    public YpjccFragment() {
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btStartCheck:
                    String rotate = etRotateNum.getEditableText().toString().trim();
                    if (rotate.equals("")) {
                        Toast.makeText(getActivity(), "旋转次数为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        ypjcActivity.mService.startCheck(ypjcActivity.druginfo_id, ypjcActivity.detectionReport.getDetectionCount(), Integer.parseInt(rotate));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public static YpjccFragment newInstance(String param1, String param2) {
        YpjccFragment fragment = new YpjccFragment();
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
        view = inflater.inflate(R.layout.fragment_ypjcc, container, false);
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
        initView();
        event();
        setDataToView(ypjcActivity.detectionReport);

//        getActivity().findViewById(R.id.btSave).setOnClickListener((View.OnClickListener) getActivity());

    }

    private void event() {
        btStartCheck.setOnClickListener(listener);
        cbShowDrugParam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ltDrugPara.setVisibility(View.VISIBLE);
                } else {
                    ltDrugPara.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initView() {
        ypjcActivity = (YpjcActivity) getActivity();
        etRotateNum = (EditText) view.findViewById(R.id.etRotateNum);
        btStartCheck = (Button) view.findViewById(R.id.btStartCheck);
        ltDrugPara = (LinearLayout) view.findViewById(R.id.ltDrugPara);
        cbShowDrugParam = (CheckBox) view.findViewById(R.id.cbShowDrugParam);
        tvDrugName = (TextView) view.findViewById(R.id.tvDrugName);
        tvFactionName = (TextView) view.findViewById(R.id.tvFactionName);
        tvDetectionBatch = (TextView) view.findViewById(R.id.tvDetectionBatch);
        tvColorCoefficient = (TextView) view.findViewById(R.id.tvColorCoefficient);
        tvEnName = (TextView) view.findViewById(R.id.tvEnName);
        tvDetectionSn = (TextView) view.findViewById(R.id.tvDetectionSn);
        tvDetectionNumber = (TextView) view.findViewById(R.id.tvDetectionNumber);
        tvShapePara = (TextView) view.findViewById(R.id.tvShapePara);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setDataToView(DetectionReport report) {
        tvDrugName.setText(ypjcActivity.drugControl.getDrugName());
        tvEnName.setText(ypjcActivity.drugControl.getEnname());
        tvFactionName.setText(ypjcActivity.drugControl.getDrugFactory());
        tvDetectionBatch.setText(ypjcActivity.detectionReport.getDetectionBatch());
        tvDetectionNumber.setText(ypjcActivity.detectionReport.getDetectionNumber());
        try {
            tvDetectionSn.setText(ypjcActivity.mService.getDetectionSn());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            drugParamList = ypjcActivity.getDrugParams();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (DrugParam drugParam : drugParamList
                ) {
            switch (drugParam.getParamname()) {
                case "shadeParam":
                    tvShapePara.setText(drugParam.getParamvalue() + "");
                    break;
                case "sendparam":
                    Toast.makeText(getActivity(), "abc", Toast.LENGTH_SHORT).show();
                    if (tvColorCoefficient != null)
                        tvColorCoefficient.setText(drugParam.getParamvalue() + "");
                    break;


            }
        }
        ((TextView) getActivity().findViewById(R.id.tvDruginfoId)).setText(report.getDruginfo_id() + "");
        ((TextView) getActivity().findViewById(R.id.tvDetectionCount)).setText(report.getDetectionCount() + "");

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
