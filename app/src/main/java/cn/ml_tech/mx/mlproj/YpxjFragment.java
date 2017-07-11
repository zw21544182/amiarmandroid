package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ml_tech.mx.mlservice.DAO.DrugContainer;
import cn.ml_tech.mx.mlservice.DAO.DrugParam;
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
    private EditText etMaxStatTime, etMaxStopTime, etImageDelayTime, etStopDelayTime, threshold40, threshold50, threshold60, threshold70;
    private View v;

    public IMlService getmService() {
        return mService;
    }

    public void setmService(IMlService mService) {
        this.mService = mService;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Map<String, String> data = null;
    private OnFragmentInteractionListener mListener;
    YpjcActivity ypjcActivity;


    public Map<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ypjcActivity = (YpjcActivity) getActivity();
        v = inflater.inflate(R.layout.fragment_ypxj, container, false);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        data = new HashMap<>();
        initView();
        super.onStart();
        ypjcActivity = (YpjcActivity) getActivity();
        getActivity().findViewById(R.id.btnypxjPre).setOnClickListener((View.OnClickListener) getActivity());
        getActivity().findViewById(R.id.btnypxjNext).setOnClickListener((View.OnClickListener) getActivity());
        try {
            factoryControlses = mService.queryFactoryControl();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    private void initView() {
        etMaxStatTime = (EditText) v.findViewById(R.id.etMaxStatTime);
        etMaxStopTime = (EditText) v.findViewById(R.id.etMaxStopTime);
        etImageDelayTime = (EditText) v.findViewById(R.id.etImageDelayTime);
        etStopDelayTime = (EditText) v.findViewById(R.id.etStopDelayTime);
        threshold40 = (EditText) v.findViewById(R.id.threshold40);
        threshold50 = (EditText) v.findViewById(R.id.threshold50);
        threshold60 = (EditText) v.findViewById(R.id.threshold60);
        threshold70 = (EditText) v.findViewById(R.id.threshold70);
        try {

            setDataToViewByPos(ypjcActivity.pos, ypjcActivity.druginfo_id);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        etMaxStatTime.addTextChangedListener(new ViewTextWatcher(etMaxStatTime));
        etMaxStopTime.addTextChangedListener(new ViewTextWatcher(etMaxStopTime));
        threshold40.addTextChangedListener(new ViewTextWatcher(threshold40));
        threshold50.addTextChangedListener(new ViewTextWatcher(threshold50));
        threshold60.addTextChangedListener(new ViewTextWatcher(threshold60));
        threshold70.addTextChangedListener(new ViewTextWatcher(threshold70));
        etImageDelayTime.addTextChangedListener(new ViewTextWatcher(etImageDelayTime));
        etStopDelayTime.addTextChangedListener(new ViewTextWatcher(etStopDelayTime));

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
     * @param pos
     * @param drug_id
     * @throws RemoteException
     */
    public void setDataToViewByPos(int pos, int drug_id) throws RemoteException {
        DrugContainer drugContainer = mService.getDrugContainer().get(pos);
        data.put("srcTime", drugContainer.getSrctime() + "");
        data.put("stpTime", drugContainer.getStptime() + "");
        data.put("imagetime", drugContainer.getImagetime() + "");
        data.put("delaytime", drugContainer.getDelaytime() + "");
        data.put("channelValue1", drugContainer.getChannelvalue1() + "");
        data.put("channelValue2", drugContainer.getChannelvalue2() + "");
        data.put("channelValue3", drugContainer.getChannelvalue3() + "");
        data.put("channelValue4", drugContainer.getChannelvalue4() + "");

        etMaxStatTime.setText(drugContainer.getSrctime() + "");
        etMaxStopTime.setText(drugContainer.getStptime() + "");
        etImageDelayTime.setText(drugContainer.getImagetime() + "");
        etStopDelayTime.setText(drugContainer.getDelaytime() + "");
        threshold40.setText(drugContainer.getChannelvalue1() + "");
        threshold50.setText(drugContainer.getChannelvalue2() + "");
        threshold60.setText(drugContainer.getChannelvalue3() + "");
        threshold70.setText(drugContainer.getChannelvalue4() + "");
        if (drug_id != 0) {
            List<DrugParam> drugParams = mService.getDrugParamById(drug_id);
            for (DrugParam drugParam :
                    drugParams
                    ) {
                switch (drugParam.getParamname()) {
                    case "srcTime":
                        etMaxStatTime.setText(drugParam.getParamvalue() + "");
                        break;
                    case "stpTime":
                        etMaxStopTime.setText(drugParam.getParamvalue() + "");
                        break;
                    case "channelValue1":
                        threshold40.setText(drugParam.getParamvalue() + "");
                        break;
                    case "channelValue2":
                        threshold50.setText(drugParam.getParamvalue() + "");
                        break;
                    case "channelValue3":
                        threshold60.setText(drugParam.getParamvalue() + "");
                        break;
                    case "channelValue4":
                        threshold70.setText(drugParam.getParamvalue() + "");
                        break;
                    case "imagetime":
                        etImageDelayTime.setText(drugParam.getParamvalue() + "");
                        break;
                    case "delaytime":
                        etStopDelayTime.setText(drugParam.getParamvalue() + "");
                        break;

                }
            }
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

    /**
     * 创建时间: 2017/7/4
     * 创建人: zhongwang
     * 功能描述: 可以获取获取View的id值的TextWather
     */

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
            switch (view.getId()) {
                case R.id.etMaxStatTime:
                    Toast.makeText(getActivity(), "etMaxStatTime", Toast.LENGTH_SHORT).show();
                    data.put("srcTime", s.toString());
                    logMap(data);
                    break;
                case R.id.etMaxStopTime:
                    Toast.makeText(getActivity(), "etMaxStopTime", Toast.LENGTH_SHORT).show();
                    data.put("stpTime", s.toString());
                    logMap(data);
                    break;
                case R.id.threshold40:
                    data.put("channelValue1", s.toString());
                    break;
                case R.id.threshold50:
                    data.put("channelValue2", s.toString());
                    break;
                case R.id.threshold60:
                    data.put("channelValue3", s.toString());
                    break;
                case R.id.threshold70:
                    data.put("channelValue4", s.toString());
                    break;
                case R.id.etImageDelayTime:
                    data.put("imagetime", s.toString());
                    break;
                case R.id.etStopDelayTime:
                    data.put("delaytime", s.toString());
                    break;
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public void logMap(Map<String, String> data) {
        for (String key : data.keySet()
                ) {
            Log.v("zw", "val" + data.get(key) + " key " + key);
        }
    }
}
