package cn.ml_tech.mx.mlproj.ypfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
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

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.activity.YpjcActivity;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.util.PdfUtil;
import cn.ml_tech.mx.mlproj.util.ReceiverUtil;
import cn.ml_tech.mx.mlservice.DAO.DetectionDetail;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
import cn.ml_tech.mx.mlservice.DAO.DevUuid;
import cn.ml_tech.mx.mlservice.DAO.DrugControls;
import cn.ml_tech.mx.mlservice.DAO.DrugParam;
import cn.ml_tech.mx.mlservice.DAO.ResultModule;

public class YpjccFragment extends BaseFragment implements View.OnClickListener {
    private YpjcActivity ypjcActivity;
    private View view;
    private LinearLayout ltDrugPara;
    private CheckBox cbShowDrugParam;
    private TextView tvDrugBottleType, tvDrugName, tvFactionName, tvDetectionBatch, tvColorCoefficient, tvEnName, tvDetectionSn, tvDetectionNumber, tvShapePara;
    private List<DrugParam> drugParamList = null;
    private Button btStartCheck;
    private EditText etRotateNum;
    private RecyclerView rvreslut;
    private ReceiverUtil receiverUtil;
    private ResultAdapter resultAdapter;
    private TextView tvPiaoFuNum, tvPiaoFuRes, tvSuJianPer, tvSuJianRes;
    private JSONObject jsonObject;
    private CheckBox cbFirstCheck, cbSecondCheck;
    private TextView tvYi40;
    private TextView tvYi50;
    private TextView tvYi60;
    private TextView tvYi70;
    private TextView tvNum40;
    private TextView tvNum50;
    private TextView tvNum70;
    private TextView tvYiRes;
    private TextView tvNumRes;
    private TextView tvMaxRes;
    private TextView tvsuJianTime;
    private String state = "";
    private String detectionSn = "";
    private DetectionReport report;
    private Button btExportData;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PdfUtil.SUCESS:
                    Toast.makeText(getActivity(), "导出pdf成功", Toast.LENGTH_SHORT).show();
                    break;
                case PdfUtil.FAILURE:
                    Toast.makeText(getActivity(), "导出pdf失败", Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_ypjcc, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        ypjcActivity = (YpjcActivity) getActivity();
        tvDrugBottleType = (TextView) view.findViewById(R.id.tvDrugBottleType);
        etRotateNum = (EditText) view.findViewById(R.id.etRotateNum);
        btStartCheck = (Button) view.findViewById(R.id.btStartCheck);
        tvsuJianTime = (TextView) view.findViewById(R.id.tvsuJianTime);
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
        tvYi40 = (TextView) view.findViewById(R.id.tvYi40);
        tvYi50 = (TextView) view.findViewById(R.id.tvYi50);
        tvYi60 = (TextView) view.findViewById(R.id.tvYi60);
        tvYi70 = (TextView) view.findViewById(R.id.tvYi70);
        tvNum40 = (TextView) view.findViewById(R.id.tvNum40);
        tvNum50 = (TextView) view.findViewById(R.id.tvNum50);
        tvNum70 = (TextView) view.findViewById(R.id.tvNum70);
        tvYiRes = (TextView) view.findViewById(R.id.tvYiRes);
        tvNumRes = (TextView) view.findViewById(R.id.tvNumRes);
        tvMaxRes = (TextView) view.findViewById(R.id.tvMaxRes);
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
        btExportData = (Button) view.findViewById(R.id.btExportData);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setPermission(((YpjcActivity) getActivity()).permission);
        btExportData.setVisibility(getPermissionById(11, 1) == true ? View.VISIBLE : View.INVISIBLE);
        etRotateNum.setEnabled(getPermissionById(13, 4));
        if (state.equals("")) {
            // TODO: 2017/9/1 新的检测 之后要做的操作 
            setDataToView(ypjcActivity.detectionReport);
            btExportData.setEnabled(false);
        } else {
            // TODO: 2017/9/1 继续上一次检测要做的操作 
            report = null;
            try {
                report = mlService.getLastReport();
                List<DetectionDetail> detectionDetails = mlService.queryDetectionDetailByReportId(report.getId());
                setReceivedData(detectionDetails.get(detectionDetails.size() - 1));
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
            if (report.getDetectionFirstCount() == report.getDetectionCount()) {
                cbFirstCheck.setChecked(false);
                cbFirstCheck.setEnabled(false);
                cbSecondCheck.setEnabled(true);
                cbSecondCheck.setChecked(true);
            } else if (report.getDetectionFirstCount() < report.getDetectionCount()) {
                cbFirstCheck.setChecked(true);
            }
            DrugControls drugControls = ypjcActivity.mService.queryDrugControlsById(report.getDruginfo_id());
            List<DetectionDetail> detectionDetails = ypjcActivity.mService.queryDetectionDetailByReportId(report.getId());
            resultAdapter.clearData();
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
            tvDrugBottleType.setText(report.getDrugBottleType());
            drugParamList = ypjcActivity.mService.getDrugParamById((int) report.getDruginfo_id());
            for (DrugParam drugParam : drugParamList
                    ) {
                switch (drugParam.getParamname()) {
                    case "shadeParam":
                        tvShapePara.setText(drugParam.getParamvalue() + "");
                        break;
                    case "sendparam":
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
                // TODO: 2017/9/1 检测完药品收到数据之后要做的操作 
                DetectionDetail detectionDetail = null;
                btExportData.setEnabled(true);
                try {
                    detectionDetail = ypjcActivity.mService.getLastDetail();
                    setReceivedData(detectionDetail);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                String state = intent.getExtras().getString("state");
                if (state.equals("process")) {

                } else if (state.equals("finish")) {
                    btStartCheck.setEnabled(true);
                    cbFirstCheck.setEnabled(false);
                    cbFirstCheck.setChecked(false);
                    cbSecondCheck.setEnabled(true);
                    cbSecondCheck.setChecked(true);
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
            tvSuJianPer.setText(value.getDouble("data") + "");
            tvsuJianTime.setText(jsonObject.getJSONObject("glasstime").getDouble("data") + "");
            tvYi40.setText(jsonObject.getJSONObject("statistics40").getDouble("data") + "");
            tvYi50.setText(jsonObject.getJSONObject("statistics50").getDouble("data") + "");
            tvYi60.setText(jsonObject.getJSONObject("statistics60").getDouble("data") + "");
            tvYi70.setText(jsonObject.getJSONObject("statistics70").getDouble("data") + "");
            tvNum40.setText(jsonObject.getJSONObject("min").getDouble("data") + "");
            tvNum50.setText(jsonObject.getJSONObject("max").getDouble("data") + "");
            tvNum70.setText(jsonObject.getJSONObject("super").getDouble("data") + "");
            tvYiRes.setText(jsonObject.getJSONObject("statistics40").getString("result") + "");
            tvNumRes.setText(jsonObject.getJSONObject("min").getString("result") + "");
            tvMaxRes.setText(jsonObject.getJSONObject("max").getString("result") + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (detectionDetail.isPositive()) {
            resultAdapter.addDataToView("阳性");
        } else {
            resultAdapter.addDataToView("阴性");
        }
    }

    @Override
    protected void initEvent() {
        btStartCheck.setOnClickListener(this);
        btExportData.setOnClickListener(this);
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

    private void setDataToView(DetectionReport report) {
        cbFirstCheck.setChecked(true);
        tvDrugName.setText(ypjcActivity.drugControl.getDrugName());
        tvEnName.setText(ypjcActivity.drugControl.getEnname());
        tvFactionName.setText(ypjcActivity.drugControl.getDrugFactory());
        tvDetectionBatch.setText(ypjcActivity.detectionReport.getDetectionBatch());
        tvDetectionNumber.setText(ypjcActivity.detectionReport.getDetectionNumber());
        tvDrugBottleType.setText(ypjcActivity.drugControl.getDrugBottleType());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btExportData:
                if (getPermissionById(11, 8)) {
                    exportData();
                } else {
                    showRefuseTip();
                }
                break;
            case R.id.btStartCheck:
                btStartCheck.setEnabled(false);
                String rotate = etRotateNum.getEditableText().toString().trim();
                if (rotate.equals("")) {
                    Toast.makeText(getActivity(), "旋转次数为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    if (state.equals("")) {
                        ypjcActivity.mService.startCheck(ypjcActivity.druginfo_id, ypjcActivity.detectionReport.getDetectionCount(), Integer.parseInt(rotate), ypjcActivity.detectionReport.getDetectionNumber(), ypjcActivity.detectionReport.getDetectionBatch(), cbFirstCheck.isChecked(), "");
                    } else {
                        if (report.getDetectionSecondCount() == report.getDetectionCount()) {
                            Toast.makeText(getActivity(), "已完成复检", Toast.LENGTH_SHORT).show();
                        }
                        ypjcActivity.mService.startCheck((int) report.getDruginfo_id(), report.getDetectionCount(), Integer.parseInt(rotate), report.getDetectionNumber(), report.getDetectionBatch(), cbFirstCheck.isChecked(), detectionSn);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void exportData() {
        try {
            DetectionReport detectionReport = mlService.getLastReport();
            if (detectionReport == null) {
                showToast("请先检查药品");
                return;
            }

            outPutPdf(mlService.queryDetectionDetailByReportId(detectionReport.getId()), detectionReport);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHold> {
        List<String> data;
        Context context;

        public ResultAdapter(List<String> data, Context context) {
            this.data = data;
            this.context = context;
        }

        public void clearData() {
            data.clear();
            notifyDataSetChanged();
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

    private void outPutPdf(List<DetectionDetail> detectionDetails, DetectionReport detectionReport) {
        try {
            DevUuid devUuid = mlService.getDevUuidInfo();
            DrugControls drugControls = mlService.queryDrugControlsById(detectionReport.getDruginfo_id());
            new PdfUtil(getActivity(), devUuid, handler, drugControls, detectionReport, detectionDetails).createPdf();
            detectionReport.setIspdfdown(true);
            mlService.saveDetectionReport(detectionReport);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
