package cn.ml_tech.mx.mlproj.DetReport;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;

/**
 * Created by ml on 2017/6/15.
 */

public class AdapterReport extends RecyclerView.Adapter<ViewHolderReport> implements View.OnClickListener {
    private List<DetectionReport> detectionReportList = new ArrayList<DetectionReport>();
    private Context mContext;

    public AdapterReport(List<DetectionReport> detectionReportList, Context mContext) {
        this.detectionReportList = detectionReportList;
        this.mContext = mContext;
    }

    public void setDataToView(List<DetectionReport> detectionReports) {
        detectionReportList.clear();
        ;
        detectionReportList.addAll(detectionReports);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

    public void addDataToView(List<DetectionReport> list) {
        detectionReportList.clear();
        detectionReportList.addAll(list);
        notifyDataSetChanged();
    }

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    OnItemClickListener mItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (null != mItemClickListener) {
            mItemClickListener.OnItemClick(v, (Integer) v.getTag());
        }
    }

    @Override
    public ViewHolderReport onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.recylereport, viewGroup, false);
        ViewHolderReport viewHolderReport = new ViewHolderReport(view);
        viewHolderReport.chkBox.setChecked(false);
        if (null != mItemClickListener) {
            viewHolderReport.txtPDF.setOnClickListener(this);
            viewHolderReport.txtDetDetail.setOnClickListener(this);
            viewHolderReport.txtDetDel.setOnClickListener(this);
        }
        return viewHolderReport;
    }

    @Override
    public void onBindViewHolder(ViewHolderReport viewHolder, int i) {
        viewHolder.txtDetSn.setText(detectionReportList.get(i).getDetectionSn());
        viewHolder.txtDetBatch.setText(detectionReportList.get(i).getDetectionBatch());
        viewHolder.txtDetCode.setText(detectionReportList.get(i).getDetectionNumber());
        viewHolder.txtDrugName.setText(detectionReportList.get(i).getDrugName());
        viewHolder.txtFactory.setText(detectionReportList.get(i).getFactoryName());
        viewHolder.txtBottleType.setText(detectionReportList.get(i).getDrugBottleType());
        viewHolder.txtFirstCount.setText(String.valueOf(detectionReportList.get(i).getDetectionFirstCount()));
        viewHolder.txtSecondCount.setText(String.valueOf(detectionReportList.get(i).getDetectionSecondCount()));
        viewHolder.txtDetDate.setText((new SimpleDateFormat("yyyy-MM-dd")).format(detectionReportList.get(i).getDate()));
        viewHolder.txtDetOperator.setText(detectionReportList.get(i).getUserName());
        viewHolder.txtPDF.setText("PDF");
        viewHolder.txtPDF.setBackgroundColor(Color.RED);
        viewHolder.txtDetDetail.setText("详情");
        viewHolder.txtDetDel.setText("删除");
        viewHolder.txtPDF.setTag(i);
        viewHolder.txtDetDetail.setTag(i);
        viewHolder.txtDetDel.setTag(i);
    }

    @Override
    public int getItemCount() {
        Log.d(getClass().getSimpleName(), "getItemCount: " + String.valueOf(detectionReportList.size()));
        return detectionReportList.size();
    }

    public void deteleteItemByPos(int pos) {
        detectionReportList.remove(pos);
        notifyDataSetChanged();
    }
}