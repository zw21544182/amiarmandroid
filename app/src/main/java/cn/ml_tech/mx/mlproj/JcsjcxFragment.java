package cn.ml_tech.mx.mlproj;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.DetReport.AdapterDetail;
import cn.ml_tech.mx.mlproj.DetReport.AdapterReport;
import cn.ml_tech.mx.mlservice.DAO.DetectionDetail;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
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
    private Button btSearch;

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
    }

    private void initRecycleReport() {
        detectionReports = new ArrayList<DetectionReport>();
        try {
            if (mService != null)
                detectionReports = mService.queryDetectionReport();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        adapterReport = new AdapterReport(detectionReports, mActivity);
        recyclerReport.setAdapter(adapterReport);
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
                            Toast.makeText(getActivity(), detectionDetails.size() + "ss", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mActivity, "Vide Play " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.txtDetailAllResult:
                        Toast.makeText(mActivity, "All Reult" + String.valueOf(position), Toast.LENGTH_SHORT).show();
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
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    detectionReports = mService.queryDetectionReport();
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
        if (mService != null) {
            Toast.makeText(getActivity(), "abc", Toast.LENGTH_SHORT).show();
        }
    }
}