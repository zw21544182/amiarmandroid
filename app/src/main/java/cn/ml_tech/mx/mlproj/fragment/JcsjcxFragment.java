package cn.ml_tech.mx.mlproj.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.activity.JcsjcxActivity;
import cn.ml_tech.mx.mlproj.adapter.AdapterDetail;
import cn.ml_tech.mx.mlproj.adapter.AdapterReport;
import cn.ml_tech.mx.mlproj.adapter.StringAdapter;
import cn.ml_tech.mx.mlproj.base.AmiApp;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.dialog.NodeInfoDialog;
import cn.ml_tech.mx.mlproj.util.CommonUtil;
import cn.ml_tech.mx.mlservice.DAO.DetectionDetail;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
import cn.ml_tech.mx.mlservice.DAO.DrugContainer;

public class JcsjcxFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView recyclerReport;
    private AdapterReport adapterReport;
    private RecyclerView recyclerDetail;
    private AdapterDetail adapterDetail;
    private List<DetectionDetail> detectionDetailList = null;
    private LinearLayout llDetail;
    private LinearLayout llReport;
    private boolean isReportLayout = true;
    private List<DetectionReport> detectionReports;
    private JcsjcxActivity jcsjcxActivity;
    private AmiApp amiApp;
    private NodeInfoDialog amiDialog;
    private EditText stopDate;
    private EditText startDate;
    private SimpleDateFormat dateFormat;
    private long staDate = 0, stoDate = 0;
    private Spinner etCheckFormat;
    private EditText etDrugName;
    private EditText etDrugFactory;
    private EditText etCheckNum;
    private EditText etRetrieveNum;
    private EditText etStartDate;
    private EditText etStopDate;
    private Button btSearch;
    private Button btResver;
    private Button btDelete;
    private Button btOutput;
    private ProgressDialog progressDialog;
    private CheckBox chkBox;
    private int cuurentPage = 1, lastPage;
    private List<String> ids;
    private static final int DATALOADSUESS = 553;
    private static final int DATALOADFALSE = 554;


    public boolean isReportLayout() {
        return isReportLayout;
    }

    public void setReportLayout(boolean reportLayout) {
        isReportLayout = reportLayout;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_jcsjcx, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        recyclerReport = (RecyclerView) view.findViewById(R.id.recyclerReport);
        recyclerDetail = (RecyclerView) view.findViewById(R.id.recyclerDetail);
        llReport = (LinearLayout) view.findViewById(R.id.llReport);
        llDetail = (LinearLayout) view.findViewById(R.id.llDetail);
        llDetail.setVisibility(View.INVISIBLE);
        stopDate = (EditText) view.findViewById(R.id.etStopDate);
        startDate = (EditText) view.findViewById(R.id.etStartDate);
        etCheckFormat = (Spinner) view.findViewById(R.id.etCheckFormat);
        etDrugName = (EditText) view.findViewById(R.id.etDrugName);
        etDrugFactory = (EditText) view.findViewById(R.id.etDrugFactory);
        etCheckNum = (EditText) view.findViewById(R.id.etCheckNum);
        etRetrieveNum = (EditText) view.findViewById(R.id.etRetrieveNum);
        etStartDate = (EditText) view.findViewById(R.id.etStartDate);
        etStopDate = (EditText) view.findViewById(R.id.etStopDate);
        btSearch = (Button) view.findViewById(R.id.btSearch);
        btResver = (Button) view.findViewById(R.id.btResver);
        chkBox = (CheckBox) view.findViewById(R.id.chkBox);
        btDelete = (Button) view.findViewById(R.id.btDelete);
        btOutput = (Button) view.findViewById(R.id.btOutput);

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        setDateToEdit(stopDate);
        setDateToEdit(startDate);
        view.findViewById(R.id.ibPre).setOnClickListener(this);
        view.findViewById(R.id.ibNext).setOnClickListener(this);
        view.findViewById(R.id.ibSearch).setOnClickListener(this);
        btDelete.setOnClickListener(this);
        btOutput.setOnClickListener(this);
        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapterReport.operateAll(isChecked);
            }
        });
        btResver.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        recyclerReport.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if (adapterReport != null && progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    public void initRecycleReport() {
        chkBox.setChecked(false);
        detectionReports = new ArrayList<>();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("数据加载中");
        }
        new Thread() {
            public void run() {
                super.run();
                try {
                    if (mlService != null) {
                        detectionReports = mlService.getAllDetectionReports();
                        handler.sendEmptyMessage(DATALOADSUESS);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(DATALOADFALSE);
                }
            }
        }.start();

    }


    private void ShowDetailInfo(@Nullable String id) {
        setReportLayout(false);
        InitDetailData(id);
        llDetail.setVisibility(View.VISIBLE);
        llReport.setVisibility(View.INVISIBLE);
    }

    public void ShowReport() {
        setReportLayout(true);
        llReport.setVisibility(View.VISIBLE);
        llDetail.setVisibility(View.INVISIBLE);
    }

    private void InitDetailData(@Nullable String info) {
        try {
            detectionDetailList = mlService.queryDetectionDetailByReportId(detectionReports.get(Integer.parseInt(info)).getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        adapterDetail.UpdateData(detectionDetailList);
    }

    @Override
    protected void handleMsg(Message message) {
        super.handleMsg(message);
        switch (message.what) {
            case DATALOADSUESS:
                setDataToView(detectionReports, true);
                break;
            case DATALOADFALSE:

                break;
        }
    }

    private void initRecycleDetail(@Nullable String prefix) {
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
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse("file://" + getActivity().getFilesDir().getAbsolutePath() + "/" + detectionDetailList.get(position).getVideo()), "video/mp4");
                        startActivity(intent);
                        break;
                    case R.id.txtDetailAllResult:
                        amiDialog = new NodeInfoDialog(getActivity(), R.layout.dialog_node, new int[]{R.id.nodelayout});
                        try {
                            amiDialog.setJsonObject(new JSONObject(detectionDetailList.get(position).getNodeInfo()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        amiDialog.setOnCenterItemClickListener(new NodeInfoDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(NodeInfoDialog dialog, View view) {
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
        jcsjcxActivity = (JcsjcxActivity) getActivity();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        detectionDetailList = new ArrayList<DetectionDetail>();
        try {
            List<String> strings = new ArrayList<>();
            List<DrugContainer> drugContainers = mlService.getDrugContainer();
            for (DrugContainer drugContainer :
                    drugContainers) {
                strings.add(drugContainer.getName());
            }
            etCheckFormat.setAdapter(new StringAdapter(strings, getActivity()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        initRecycle();

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

    public void setPreDataToView() throws RemoteException {
        if (cuurentPage == 1) {
            Toast.makeText(getActivity(), "已经是第一页了", Toast.LENGTH_SHORT).show();
            return;
        }
        cuurentPage--;
        adapterReport.setDataToView(mlService.queryDetectionReport(etRetrieveNum.getEditableText().toString().trim(),
                etDrugName.getEditableText().toString().trim(), etDrugFactory.getEditableText().toString().trim(),
                etCheckFormat.getSelectedItem().toString().trim(), etCheckNum.getEditableText().toString().trim(),
                etStartDate.getEditableText().toString().trim(), etStopDate.getEditableText().toString().trim(), cuurentPage));
        ((TextView) getActivity().findViewById(R.id.tvCurrentPage)).setText(cuurentPage + "/");
    }

    public void setNexTDataToView() throws RemoteException {
        if (cuurentPage == lastPage) {
            Toast.makeText(getActivity(), "已经是最后一页了", Toast.LENGTH_SHORT).show();
            return;
        }
        cuurentPage++;
        adapterReport.setDataToView(mlService.queryDetectionReport(etRetrieveNum.getEditableText().toString().trim(),
                etDrugName.getEditableText().toString().trim(), etDrugFactory.getEditableText().toString().trim(),
                etCheckFormat.getSelectedItem().toString().trim(), etCheckNum.getEditableText().toString().trim(),
                etStartDate.getEditableText().toString().trim(), etStopDate.getEditableText().toString().trim(), cuurentPage));
        ((TextView) getActivity().findViewById(R.id.tvCurrentPage)).setText(cuurentPage + "/");
    }

    public void setSearchDataToView() throws RemoteException {
        String content = ((EditText) getActivity().findViewById(R.id.etPage)).getEditableText().toString();
        if (content.trim().equals("")) {
            Toast.makeText(getActivity(), "请输入页码再进行查询", Toast.LENGTH_SHORT).show();
            return;
        }
        cuurentPage = Integer.parseInt(content);
        if (cuurentPage > lastPage) {
            Toast.makeText(getActivity(), "已超过最大页", Toast.LENGTH_SHORT).show();
            return;
        }
        adapterReport.setDataToView(mlService.queryDetectionReport(etRetrieveNum.getEditableText().toString().trim(),
                etDrugName.getEditableText().toString().trim(), etDrugFactory.getEditableText().toString().trim(),
                etCheckFormat.getSelectedItem().toString().trim(), etCheckNum.getEditableText().toString().trim(),
                etStartDate.getEditableText().toString().trim(), etStopDate.getEditableText().toString().trim(), cuurentPage));
        ((TextView) getActivity().findViewById(R.id.tvCurrentPage)).setText(cuurentPage + "/");

    }

    public void setDataToView(final List<DetectionReport> reportList, boolean isReseting) {
        lastPage = ((int) Math.floor(reportList.size() / 20)) + 1;
        ((TextView) getActivity().findViewById(R.id.tvAllPage)).setText(lastPage + "");
        if (isReseting) {
            cuurentPage = 1;
            ((TextView) getActivity().findViewById(R.id.tvCurrentPage)).setText(cuurentPage + "/");
            for (int i = 20; i < reportList.size(); i++) {
                reportList.remove(i);
                i--;
            }
        }
        adapterReport = new AdapterReport(reportList, mActivity);
        if (recyclerReport != null) {
            recyclerReport.setAdapter(adapterReport);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerReport.setLayoutManager(linearLayoutManager);
        adapterReport.setmItemClickListener(new AdapterReport.OnItemClickListener() {
            @Override
            public void OnItemClick(final View view, int position) {
                switch (view.getId()) {
                    case R.id.txtPDF:
                        int i = (int) view.getTag();
                        Log.d("zw", "id = " + i);
                        try {
                            if (ids == null)
                                ids = new ArrayList<>();
                            ids.clear();
                            ids.add(reportList.get(i).getId() + "");
                            mlService.OperateReportInfo(ids, CommonUtil.OPERATEREPORT_OUTPUT);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.txtDetDetail:
                        ShowDetailInfo(String.valueOf(position));
                        break;
                    case R.id.txtDetDel:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                .setTitle("是否删除这条数据")
                                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            int s = (int) view.getTag();
                                            if (!detectionReports.get(s).ispdfdown()) {
                                                showToast("数据尚未导出,无法删除");
                                                return;
                                            }
                                            mlService.deteleDetectionInfoById(detectionReports.get(s).getId());
                                            adapterReport.deteleteItemByPos(s);
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getActivity(), "删除失败，请重试", Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builder.create().show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btResver:
                etDrugName.setText("");
                etDrugFactory.setText("");
                etCheckNum.setText("");
                etRetrieveNum.setText("");
                etStartDate.setText("");
                etStopDate.setText("");
                try {
                    List<DetectionReport> detectionReports = mlService.getAllDetectionReports();
                    Log.d("zw", "detection size " + detectionReports.size());
                    setDataToView(detectionReports, true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btSearch:
                try {
                    detectionReports = mlService.queryDetectionReport(etRetrieveNum.getEditableText().toString().trim(),
                            etDrugName.getEditableText().toString().trim(), etDrugFactory.getEditableText().toString().trim(),
                            etCheckFormat.getSelectedItem().toString().trim(), etCheckNum.getEditableText().toString().trim(),
                            etStartDate.getEditableText().toString().trim(), etStopDate.getEditableText().toString().trim(), -1);
                    setDataToView(detectionReports, true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ibPre:
                try {
                    setPreDataToView();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ibNext:
                try {
                    setNexTDataToView();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ibSearch:
                try {
                    setSearchDataToView();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btDelete:
                ids = adapterReport.getIds();
                if (ids.size() == 0) {
                    showToast("尚未选中");
                    return;
                }
                showToast("后台操作中");
                new Thread() {
                    public void run() {
                        super.run();
                        try {
                            mlService.OperateReportInfo(ids, CommonUtil.OPERATEREPORT_DELETE);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.btOutput:
                ids = adapterReport.getIds();
                if (ids.size() == 0) {
                    showToast("尚未选中");
                    return;
                }
                showToast("后台操作中");
                new Thread() {
                    public void run() {
                        super.run();
                        try {
                            mlService.OperateReportInfo(ids, CommonUtil.OPERATEREPORT_OUTPUT);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
        }
    }
}