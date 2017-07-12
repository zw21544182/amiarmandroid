package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.util.ReceiverUtil;
import cn.ml_tech.mx.mlservice.DAO.DetectionDetail;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
import cn.ml_tech.mx.mlservice.DAO.DrugParam;
import cn.ml_tech.mx.mlservice.DAO.ResultModule;
import cn.ml_tech.mx.mlservice.DrugControls;


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
    private RecyclerView rvreslut;
    private ReceiverUtil receiverUtil;
    private ResultAdapter resultAdapter;
    private TextView tvPiaoFuNum, tvPiaoFuRes, tvSuJianPer, tvSuJianRes;
    private JSONObject jsonObject;
    private CheckBox cbFirstCheck, cbSecondCheck;
    private String state = "";
    private String detectionSn = "";
    private DetectionReport report;

    public void setState(String state) {
        this.state = state;
    }

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
                        if (state.equals("")) {
                            ypjcActivity.mService.startCheck(ypjcActivity.druginfo_id, ypjcActivity.detectionReport.getDetectionCount(), Integer.parseInt(rotate), ypjcActivity.detectionReport.getDetectionNumber(), ypjcActivity.detectionReport.getDetectionBatch(), cbFirstCheck.isChecked(), "");
                        } else {
                            Log.d("zw", detectionSn + "detectionSn");
                            ypjcActivity.mService.startCheck((int) report.getDruginfo_id(), report.getDetectionCount(), Integer.parseInt(rotate), report.getDetectionNumber(), report.getDetectionBatch(), cbFirstCheck.isChecked(), detectionSn);
                        }
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
        if (state.equals("")) {
            setDataToView(ypjcActivity.detectionReport);
        } else {
            report = null;
            try {
                report = ypjcActivity.mService.getLastReport();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            setPreDataToView(report);
        }
        initReceiver();
    }
    private void setPreDataToView(DetectionReport report) {
        detectionSn = report.getDetectionSn();
        ((TextView) getActivity().findViewById(R.id.tvDruginfoId)).setText(report.getDruginfo_id() + "");
        ((TextView) getActivity().findViewById(R.id.tvDetectionCount)).setText(report.getDetectionCount() + "");
        try {
            Log.d("zw", "normal" + report.getDetectionCount() + " first" + report.getDetectionFirstCount());
            if (report.getDetectionFirstCount() == report.getDetectionCount() && report.getDetectionSecondCount() == 0) {
                cbFirstCheck.setChecked(false);
                cbFirstCheck.setEnabled(false);
                cbSecondCheck.setEnabled(true);
            } else if (report.getDetectionFirstCount() < report.getDetectionCount()) {
                cbFirstCheck.setChecked(true);
            }
            DrugControls drugControls = ypjcActivity.mService.queryDrugControlsById(report.getDruginfo_id());
            List<DetectionDetail> detectionDetails = ypjcActivity.mService.queryDetectionDetailByReportId(report.getId());
            for (DetectionDetail detectionDetail :
                    detectionDetails) {
                if (detectionDetail.isPositive()) {
                    resultAdapter.addDataToView("阳性");
                } else {
                    resultAdapter.addDataToView("阴性");
                }
            }
            tvDrugName.setText(drugControls.getDrugName());
            tvEnName.setText(drugControls.getEnname());
            tvFactionName.setText(drugControls.getDrugFactory());
            tvDetectionBatch.setText(report.getDetectionBatch());
            tvDetectionNumber.setText(report.getDetectionNumber());
            tvDetectionSn.setText(report.getDetectionSn());
            drugParamList = ypjcActivity.mService.getDrugParamById((int) report.getDruginfo_id());
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
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void initReceiver() {
        receiverUtil = new ReceiverUtil("com.checkfinsh", getActivity()) {
            @Override
            protected void operate(Context context, Intent intent) {
                DetectionDetail detectionDetail = null;
                try {
                    detectionDetail = ypjcActivity.mService.getLastDetail();
                    setReceivedData(detectionDetail);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                String state = intent.getExtras().getString("state");
                if (state.equals("process")) {

                } else if (state.equals("finish")) {
                    cbFirstCheck.setEnabled(false);
                    cbFirstCheck.setChecked(false);
                    cbSecondCheck.setEnabled(true);
                } else if (state.equals("secondfinish")) {
                    btStartCheck.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "已完成复检", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };
        receiverUtil.inRegister();
    }

    private void setReceivedData(DetectionDetail detectionDetail) {
        ResultModule resultModule = null;
        JSONObject value = null;
        try {
            jsonObject = new JSONObject(detectionDetail.getNodeInfo());
            value = jsonObject.getJSONObject("floatdta");
            tvPiaoFuNum.setText(value.getDouble("data") + "");
            tvPiaoFuRes.setText(value.getString("result"));
            value = jsonObject.getJSONObject("glassprecent");
            tvSuJianRes.setText(value.getString("result"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (detectionDetail.isPositive()) {
            resultAdapter.addDataToView("阳性");
        } else {
            resultAdapter.addDataToView("阴性");
        }
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
        cbSecondCheck = (CheckBox) view.findViewById(R.id.cbSecondCheck);
        cbFirstCheck = (CheckBox) view.findViewById(R.id.cbFirstCheck);
        cbFirstCheck.setChecked(true);
        tvDetectionNumber = (TextView) view.findViewById(R.id.tvDetectionNumber);
        tvShapePara = (TextView) view.findViewById(R.id.tvShapePara);
        rvreslut = (RecyclerView) view.findViewById(R.id.rvreslut);
        tvSuJianPer = (TextView) view.findViewById(R.id.tvSuJianPer);
        tvSuJianRes = (TextView) view.findViewById(R.id.tvSuJianRes);
        tvPiaoFuRes = (TextView) view.findViewById(R.id.tvPiaoFuRes);
        tvPiaoFuNum = (TextView) view.findViewById(R.id.tvPiaoFuNum);
        rvreslut.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        resultAdapter = new ResultAdapter(new ArrayList<String>(), getActivity());
        rvreslut.setAdapter(resultAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setDataToView(DetectionReport report) {
        cbFirstCheck.setChecked(true);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        receiverUtil.unRefister();
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

    private class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHold> {
        List<String> data;
        Context context;

        public ResultAdapter(List<String> data, Context context) {
            this.data = data;
            this.context = context;
        }

        public void addDataToView(String content) {
            data.add(content);
            notifyDataSetChanged();
        }

        @Override
        public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {

            return new ViewHold(LayoutInflater.from(getActivity()).inflate(R.layout.result_itmeitme, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHold holder, int position) {
            holder.num.setText((position + 1) + "");
            holder.result.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHold extends RecyclerView.ViewHolder {
            TextView num;
            TextView result;

            public ViewHold(View itemView) {
                super(itemView);
                num = (TextView) itemView.findViewById(R.id.num);
                result = (TextView) itemView.findViewById(R.id.result);
            }
        }
    }
}
