package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ml_tech.mx.mlproj.DetReport.AdapterDetail;
import cn.ml_tech.mx.mlproj.DetReport.AdapterReport;
import cn.ml_tech.mx.mlservice.DAO.DetectionDetail;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JcsjcxFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JcsjcxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JcsjcxFragment extends BaseFragment
{

    private RecyclerView recyclerReport;
    private AdapterReport adapterReport;
    private AdapterReport.OnItemClickListener mOnItemClickListener;
    private RecyclerView recyclerDetail;
    private AdapterDetail adapterDetail;
    private List<DetectionDetail> detectionDetailList=null;
    private LinearLayout llDetail;
    private LinearLayout llReport;
    private boolean isReportLayout=true;

    public boolean isReportLayout() {
        return isReportLayout;
    }

    public void setReportLayout(boolean reportLayout) {
        isReportLayout = reportLayout;
    }
    @Override
    public View initView(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.fragment_jcsjcx,null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {


        recyclerReport= (RecyclerView) view.findViewById(R.id.recyclerReport);
        recyclerDetail = (RecyclerView) view.findViewById(R.id.recyclerDetail);
        llReport = (LinearLayout) view.findViewById(R.id.llReport);
        llDetail = (LinearLayout) view.findViewById(R.id.llDetail);
        llDetail.setVisibility(View.INVISIBLE);
    }
    private void initRecycleReport()
    {
        List<DetectionReport>list=new ArrayList<DetectionReport>();
        for(int i=0;i<100;i++)
        {
            DetectionReport report=new DetectionReport();
            report.setDetectionSn(String.format("DetectionSn%d",i));
            report.setDetectionBatch(String.format("DetectionBatch%d",i));
            report.setDetectionNumber(String.format("DetectionNumber%d",i));
            report.setDrugName(String.format("DrugName%d",i));
            report.setFactoryName(String.format("FactoryName%d",i));
            report.setDetectionFirstCount(i+1);
            report.setDetectionSecondCount(i+1);
            report.setUserName(String.format("UserName%d",i));
            report.setDate(UtilsHelper.GetDateFromString("2017-06-18","yyyy-MM-dd"));
            list.add(report);
        }
        adapterReport = new AdapterReport( list,mActivity);
        recyclerReport.setAdapter(adapterReport);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerReport.setLayoutManager(linearLayoutManager);

        adapterReport.setmItemClickListener(new AdapterReport.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                switch (view.getId())
                {
                    case R.id.txtPDF:
                        Toast.makeText(mActivity,String.format("txtPDF%d ",(int)view.getTag()),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.txtDetDetail:
                        ShowDetailInfo(String.valueOf((int)view.getTag()));
                        Toast.makeText(mActivity,String.format("txtDetDetail%d ",(int)view.getTag()),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.txtDetDel:
                        Toast.makeText(mActivity,String.format("txtDetDel%d ",(int)view.getTag()),Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    private void ShowDetailInfo(@Nullable String id)
    {
        setReportLayout(false);
        InitDetailData(id);

        llDetail.setVisibility(View.VISIBLE);
        llReport.setVisibility(View.INVISIBLE);
    }
    protected void ShowReport()
    {
        setReportLayout(true);
        llReport.setVisibility(View.VISIBLE);
        llDetail.setVisibility(View.INVISIBLE);
    }
    private void InitDetailData(@Nullable String info)
    {

        for(int i=0;i<100;i++)
        {
            DetectionDetail detail=new DetectionDetail();
            detail.setRepIndex(i%2);
            detail.setDetIndex(i);
            detail.setFiberPositive(i%2==0);
            detail.setFloatPositive(i%2==0);
            detail.setGlassPositive(i%2==0);
            detail.setAnalyzePositive(i%2==0);
            detail.setNodePositive(i%2==0);
            detail.setSuperPositive(i%2==0);
            detail.setNodeInfo("node info");
            detail.setPositive(i%2==0);
            detail.setValid(i%2==0);
            detail.setVideo("Video");
            detail.setVideoMd5("vieo md5");
            detail.setScrTime(i);
            detail.setStpTime(i);
            detail.setScrTimeText(info+"src time text ");
            detail.setStpTimeText(info+"stp time text");
            detail.setColorFactor(i);
            detectionDetailList.add(detail);
        }
        adapterDetail.UpdateData(detectionDetailList);
    }
    private void initRecycleDetail(@Nullable String prefix)
    {

        adapterDetail = new AdapterDetail(mActivity,detectionDetailList);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerDetail.setLayoutManager(linearLayoutManager);
        recyclerDetail.setAdapter(adapterDetail);
        adapterDetail.setmOnItemClickListener(new AdapterDetail.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                switch(view.getId())
                {
                    case R.id.txtDetailVideo:
                        Toast.makeText(mActivity, "Vide Play "+String.valueOf(position), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.txtDetailAllResult:
                        Toast.makeText(mActivity, "All Reult"+String.valueOf(position), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
        adapterDetail.notifyDataSetChanged();
    }
    private void initRecycle()
    {
        initRecycleReport();
        initRecycleDetail("");
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        detectionDetailList = new ArrayList<DetectionDetail>();
        Log.d(JcsjcxFragment.class.getSimpleName(), "initData: ");
        initRecycle();
    }
}