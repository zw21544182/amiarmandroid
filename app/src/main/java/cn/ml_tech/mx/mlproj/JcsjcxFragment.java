package cn.ml_tech.mx.mlproj;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ml_tech.mx.mlproj.DetReport.AdapterDetail;
import cn.ml_tech.mx.mlproj.DetReport.AdapterReport;
import cn.ml_tech.mx.mlproj.Dialog.AmiDialog;
import cn.ml_tech.mx.mlproj.util.PdfUtil;
import cn.ml_tech.mx.mlservice.DAO.DetectionDetail;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
import cn.ml_tech.mx.mlservice.DAO.DevUuid;
import cn.ml_tech.mx.mlservice.DrugControls;
import cn.ml_tech.mx.mlservice.IMlService;


public class JcsjcxFragment extends BaseFragment {
    private RecyclerView recyclerReport;
    private AdapterReport adapterReport;
    private AdapterReport.OnItemClickListener mOnItemClickListener;
    private RecyclerView recyclerDetail;
    private AdapterDetail adapterDetail;
    private List<DetectionDetail> detectionDetailList = null;
    private LinearLayout llDetail;
    private LinearLayout llReport;
    private boolean isReportLayout = true;
    private List<DetectionReport> detectionReports;
    private JcsjcxActivity jcsjcxActivity;
    private AmiApp amiApp;
    private IMlService mService;

    private AmiDialog amiDialog;
    private EditText stopDate;
    private EditText startDate;
    private SimpleDateFormat dateFormat;
    private long staDate = 0, stoDate = 0;
    private EditText etCheckFormat;
    private EditText etDrugName;
    private EditText etDrugFactory;
    private EditText etCheckNum;
    private EditText etRetrieveNum;
    private EditText etStartDate;
    private EditText etStopDate;
    private Button btSearch;
    private Button btResver;
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

    public boolean isReportLayout() {
        return isReportLayout;
    }

    public void setReportLayout(boolean reportLayout) {
        isReportLayout = reportLayout;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        Log.d("zw", "initView");
        view = inflater.inflate(R.layout.fragment_jcsjcx, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("zw", "oncreate");
    }

    @Override
    public void initFindViewById(View view) {
        Log.d("zw", "findid");
        btSearch = (Button) view.findViewById(R.id.btSearch);
        recyclerReport = (RecyclerView) view.findViewById(R.id.recyclerReport);
        recyclerDetail = (RecyclerView) view.findViewById(R.id.recyclerDetail);
        llReport = (LinearLayout) view.findViewById(R.id.llReport);
        llDetail = (LinearLayout) view.findViewById(R.id.llDetail);
        llDetail.setVisibility(View.INVISIBLE);
        stopDate = (EditText) view.findViewById(R.id.etStopDate);
        startDate = (EditText) view.findViewById(R.id.etStartDate);
        etCheckFormat = (EditText) view.findViewById(R.id.etCheckFormat);
        etDrugName = (EditText) view.findViewById(R.id.etDrugName);
        etDrugFactory = (EditText) view.findViewById(R.id.etDrugFactory);
        etCheckNum = (EditText) view.findViewById(R.id.etCheckNum);
        etRetrieveNum = (EditText) view.findViewById(R.id.etRetrieveNum);
        etStartDate = (EditText) view.findViewById(R.id.etStartDate);
        etStopDate = (EditText) view.findViewById(R.id.etStopDate);
        btSearch = (Button) view.findViewById(R.id.btSearch);
        btResver = (Button) view.findViewById(R.id.btResver);
        event();
    }

    private void event() {
        setDateToEdit(stopDate);
        setDateToEdit(startDate);
    }

    private void initRecycleReport() {
        detectionReports = new ArrayList<DetectionReport>();
        try {
            if (mService != null)
                detectionReports = mService.queryDetectionReport(etRetrieveNum.getEditableText().toString().trim(),
                        etDrugName.getEditableText().toString().trim(), etDrugFactory.getEditableText().toString().trim(),
                        etCheckFormat.getEditableText().toString().trim(), etCheckNum.getEditableText().toString().trim(),
                        etStartDate.getEditableText().toString().trim(), etStopDate.getEditableText().toString().trim(), -1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        adapterReport = new AdapterReport(detectionReports, mActivity);
        if (recyclerReport != null) {
            recyclerReport.setAdapter(adapterReport);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerReport.setLayoutManager(linearLayoutManager);

        adapterReport.setmItemClickListener(new AdapterReport.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.txtPDF:
                        int i = (int) view.getTag();
                        try {
                            List<DetectionDetail> detectionDetails = mActivity.mService.queryDetectionDetailByReportId(detectionReports.get(i).getId());
                            outPutPdf(detectionDetails, detectionReports.get(i));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.txtDetDetail:
                        ShowDetailInfo(String.valueOf((int) view.getTag()));
                        Toast.makeText(mActivity, String.format("txtDetDetail%d ", (int) view.getTag()), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.txtDetDel:
                        Toast.makeText(mActivity, String.format("txtDetDel%d ", (int) view.getTag()), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void outPutPdf(List<DetectionDetail> detectionDetails, DetectionReport detectionReport) {
        try {
            DevUuid devUuid = mService.getDevUuidInfo();
            DrugControls drugControls = mService.queryDrugControlsById(detectionReport.getDruginfo_id());
            new PdfUtil(getActivity(), devUuid, handler, drugControls, detectionReport, detectionDetails).createPdf();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void ShowDetailInfo(@Nullable String id) {
        Log.d("zw", "showDetailInfo");
        setReportLayout(false);
        InitDetailData(id);

        llDetail.setVisibility(View.VISIBLE);
        llReport.setVisibility(View.INVISIBLE);
    }

    protected void ShowReport() {
        setReportLayout(true);
        llReport.setVisibility(View.VISIBLE);
        llDetail.setVisibility(View.INVISIBLE);
    }

    private void InitDetailData(@Nullable String info) {

        try {
            detectionDetailList = mService.queryDetectionDetailByReportId(detectionReports.get(Integer.parseInt(info)).getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        adapterDetail.UpdateData(detectionDetailList);
    }

    private void initRecycleDetail(@Nullable String prefix) {
        Log.d("zw", "  ");
        adapterDetail = new AdapterDetail(mActivity, detectionDetailList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerDetail.setLayoutManager(linearLayoutManager);
        recyclerDetail.setAdapter(adapterDetail);
        adapterDetail.setmOnItemClickListener(new AdapterDetail.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.txtDetailVideo:
                        Toast.makeText(getActivity(), detectionDetailList.get(position).getVideo(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.txtDetailAllResult:
                        amiDialog = new AmiDialog(getActivity(), R.layout.dialog_node, new int[]{R.id.nodelayout});
                        try {
                            amiDialog.setJsonObject(new JSONObject(detectionDetailList.get(position).getNodeInfo()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        amiDialog.setOnCenterItemClickListener(new AmiDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(AmiDialog dialog, View view) {
                                switch (view.getId()) {
                                    case R.id.nodelayout:
                                        break;
                                }
                            }
                        });
                        amiDialog.show();
                        break;
                    default:
                        break;
                }
            }
        });
        adapterDetail.notifyDataSetChanged();
    }

    private void initRecycle() {
        initRecycleReport();
        initRecycleDetail("");
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    detectionReports = mService.queryDetectionReport(etRetrieveNum.getEditableText().toString().trim(),
                            etDrugName.getEditableText().toString().trim(), etDrugFactory.getEditableText().toString().trim(),
                            etCheckFormat.getEditableText().toString().trim(), etCheckNum.getEditableText().toString().trim(),
                            etStartDate.getEditableText().toString().trim(), etStopDate.getEditableText().toString().trim(), -1);
                    adapterReport.addDataToView(detectionReports);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        detectionDetailList = new ArrayList<DetectionDetail>();
        Log.d(JcsjcxFragment.class.getSimpleName(), "initData: ");
        initRecycle();
    }

    @Override
    public void onStart() {
        super.onStart();
        amiApp = (AmiApp) getActivity().getApplication();
        mService = amiApp.getmMLService();


    }

    private void setDateToEdit(final EditText dateView) {
        dateView.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDatePickDlg(dateView);
                    return true;
                }
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void showDatePickDlg(final EditText dateView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String times = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                Date current = new Date();
                Date time = null;
                try {
                    Log.d("ZW", times);
                    time = dateFormat.parse(times);
                    if (time.getTime() > (current.getTime())) {
                        Log.d("ZW", "选择框时间" + time.getTime() + " 当前时间" + current.getTime());
                        Toast.makeText(getActivity(), "不能大于当前时间", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    switch (dateView.getId()) {
                        case R.id.etStartDate:
                            staDate = time.getTime();
                            if (stoDate != 0) {
                                if (staDate < stoDate) {
                                    Toast.makeText(getActivity(), "开始时间不能小于结束时间", Toast.LENGTH_SHORT).show();
                                }
                            }
                            Log.d("ZW", "开始时间" + staDate);
                            dateView.setText(times);
                            stopDate.setEnabled(true);
                            break;
                        case R.id.etStopDate:
                            stoDate = time.getTime();
                            Log.d("ZW", "开始时间" + staDate + " 结束时间" + stoDate);

                            if (stoDate < staDate) {
                                Toast.makeText(getActivity(), "结束时间不能大于开始时间", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            dateView.setText(times);

                            break;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

}