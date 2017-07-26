package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ml_tech.mx.mlproj.util.ReceiverUtil;
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
    private Button btEntryBottle, btValidate, btresver, btLeaveBottle, btBottlePara;
    private OnFragmentInteractionListener mListener;
    YpjcActivity ypjcActivity;
    private HashMap<String, String> data;
    private boolean isEnter = false;
    private View view;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public YpjqFragment() {
        // Required empty public constructor
    }

    private ReceiverUtil receiverUtil = null;

    public static YpjqFragment newInstance(String param1, String param2) {
        YpjqFragment fragment = new YpjqFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btEntryBottle:
                    try {
                        receiverUtil.inRegister();
                        ypjcActivity.mService.enterBottle();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btValidate:
                    if (!isEnter) {
                        Toast.makeText(getActivity(), "尚未进瓶子", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        ypjcActivity.mService.Validate(ypjcActivity.druginfo_id, Integer.parseInt(etShadLocation.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btresver:
                    if (isEnter) {
                        Toast.makeText(getActivity(), "进瓶状态下无法取消", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ypjcActivity.moveToMainFragment();
                    break;
                case R.id.btBottlePara:
                    try {
                        ypjcActivity.mService.bottleTest(Integer.parseInt(etBottlePara.getEditableText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btLeaveBottle:
                    try {
                        ypjcActivity.mService.leaveBottle();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

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
        initReciver();
        view = inflater.inflate(R.layout.fragment_ypxq, container, false);
        Log.d("zw", "onCreateView");

        return view;
    }

    private void initReciver() {
        receiverUtil = new ReceiverUtil(ReceiverUtil.ENTERBOTTLE, getActivity()) {

            /**
             * @param context
             * @param intent
             */
            @Override
            protected void operate(Context context, Intent intent) {
                String statue = intent.getExtras().getString("state");
                if (statue.equals("sucess")) {
                    //进瓶成功
                    isEnter = true;
                    btValidate.setEnabled(true);
                    getActivity().findViewById(R.id.btSave).setEnabled(false);
                    btLeaveBottle.setEnabled(true);
                    btEntryBottle.setEnabled(false);
                    etShadLocation.setEnabled(true);
                } else if (statue.equals("Validate")) {
                    //验证成功
                    tvColorCoefficient.setText(intent.getExtras().getInt("colornum") + "");
                    spParaType.setSelection((intent.getExtras().getInt("paratype") - 1));
                } else if (statue.equals("leavebottlesucess")) {
                    //出瓶成功
                    isEnter = false;
                    getActivity().findViewById(R.id.btSave).setEnabled(true);
                    getActivity().findViewById(R.id.btSave).setEnabled(true);
                }
            }
        }

        ;
    }

    private void event() {
        btEntryBottle.setOnClickListener(listener);
        btValidate.setOnClickListener(listener);
        btresver.setOnClickListener(listener);
        btBottlePara.setOnClickListener(listener);
        btLeaveBottle.setOnClickListener(listener);
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
        Log.d("zw", "onstart");
        data = new HashMap<>();
        getActivity().findViewById(R.id.btSave).setOnClickListener((View.OnClickListener) getActivity());
        etBottlePara = (EditText) view.findViewById(R.id.etBottlePara);
        etShadLocation = (EditText) view.findViewById(R.id.etShadLocation);
        tvShadPara = (TextView) view.findViewById(R.id.tvShadPara);
        spParaType = (Spinner) view.findViewById(R.id.spParaType);
        tvColorCoefficient = (TextView) view.findViewById(R.id.tvColorCoefficient);
        setDataToView(ypjcActivity.pos, ypjcActivity.druginfo_id);
        btEntryBottle = (Button) view.findViewById(R.id.btEntryBottle);
        btValidate = (Button) view.findViewById(R.id.btValidate);
        btBottlePara = (Button) view.findViewById(R.id.btBottlePara);
        btLeaveBottle = (Button) view.findViewById(R.id.btLeaveBottle);
        btresver = (Button) view.findViewById(R.id.btresver);
        etBottlePara.addTextChangedListener(new ViewTextWatcher(etBottlePara));
        etShadLocation.addTextChangedListener(new ViewTextWatcher(etShadLocation));
        tvShadPara.addTextChangedListener(new ViewTextWatcher(tvShadPara));
        tvColorCoefficient.addTextChangedListener(new ViewTextWatcher(tvColorCoefficient));
        event();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        receiverUtil.unRefister();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setDataToView(int pos, int drug_id) {
        Log.d("zw", "drugid" + drug_id);
        try {
            DrugContainer drugContainer = ypjcActivity.mService.getDrugContainer().get(pos);
            etBottlePara.setText(drugContainer.getRotatespeed() + "");
            etShadLocation.setText(drugContainer.getHeight() + "");
            data.put("rotateSpeed", drugContainer.getRotatespeed() + "");
            data.put("height", drugContainer.getHeight() + "");
            data.put("sendparam", drugContainer.getSendparam() + "");
            data.put("shadeParam", drugContainer.getShadeparam() + "");
            if (drug_id != 0) {
                Log.d("zw", "id!=0");
                List<DrugParam> drugParams = ypjcActivity.getDrugParams();
                for (DrugParam drugParam :
                        drugParams
                        ) {
                    Log.d("zw", drugParam.toString());
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
                            if (tvColorCoefficient != null)
                                tvColorCoefficient.setText(drugParam.getParamvalue() + "");
                            break;

                    }
                }
            }
        } catch (RemoteException e)

        {
            e.printStackTrace();
            Log.d("zw", "exception");
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
                    try {
                        tvShadPara.setText(String.valueOf(getShadParaByLocation(Integer.parseInt(s.toString()))));
                    } catch (Exception e) {

                    }
                    break;
                case R.id.tvShadPara:
                    data.put("shadeParam", s.toString());
                    break;
                case R.id.tvColorCoefficient:
                    data.put("sendparam", s.toString());
                    Toast.makeText(getActivity(), "tvColorCoefficient", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    /**
     * 算法 通过遮光位置获得遮光参数
     *
     * @param location 遮光位置
     * @return 遮光参数
     */
    private double getShadParaByLocation(int location) {
        return location / 3 + 0.234;
    }
}
