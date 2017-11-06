package cn.ml_tech.mx.mlproj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;


public class AdapterReport extends RecyclerView.Adapter<AdapterReport.ViewHolderReport> implements View.OnClickListener {
    private List<DetectionReport> detectionReportList = new ArrayList<DetectionReport>();
    private Context mContext;
    private List<Boolean> checks;
    private List<String> ids;
    private List<String> pos;

    public AdapterReport(List<DetectionReport> detectionReportList, Context mContext) {
        this.detectionReportList = detectionReportList;
        this.mContext = mContext;
        ids = new ArrayList<>();
        checks = new ArrayList<>();
        pos = new ArrayList<>();
        for (DetectionReport detectionReport :
                detectionReportList) {
            checks.add(false);
        }
    }

    public List<String> getIds() {
        return ids;
    }

    public void operateAll(boolean b) {
        ids.clear();
        pos.clear();
        for (int i = 0; i < detectionReportList.size(); i++) {
            checks.set(i, b);
            if (b) {
                ids.add(detectionReportList.get(i).getId() + "");
                pos.add(i + "");
            }
        }
        notifyDataSetChanged();
        Log.d("zw", "operate ids size" + ids.size());
    }

    public void setDataToView(List<DetectionReport> detectionReports) {
        detectionReportList.clear();
        checks.clear();
        ids.clear();
        pos.clear();
        int i = 0;
        detectionReportList.addAll(detectionReports);
        for (DetectionReport detectionReport :
                detectionReportList) {
            checks.add(false);
            ids.add(detectionReport.getId() + "");
            pos.add((i + 1) + "");
        }

        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }


    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    OnItemClickListener mItemClickListener = null;

    public List<DetectionReport> getDetectionReportList() {
        return detectionReportList;
    }

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
    public void onBindViewHolder(ViewHolderReport viewHolder, final int i) {
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
        viewHolder.txtPDF.setText(Html.fromHtml("<u>" + "PDF" + "</u>"));
        viewHolder.txtDetDetail.setText(Html.fromHtml("<u>" + "详情" + "</u>"));
        viewHolder.txtDetDel.setText(Html.fromHtml("<u>" + "删除" + "</u>"));
        viewHolder.txtPDF.setTag(i);
        viewHolder.txtDetDetail.setTag(i);
        viewHolder.txtDetDel.setTag(i);
        viewHolder.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checks.set(i, isChecked);
                if (isChecked) {
                    if (!ids.contains(detectionReportList.get(i).getId() + "")) {
                        ids.add(detectionReportList.get(i).getId() + "");
                    }
                    if (!pos.contains(i + "")) {
                        pos.add(i + "");
                    }
                    Log.d("zw", " ids Size " + ids.size());
                } else {
                    if (ids.contains(detectionReportList.get(i).getId() + "")) {
                        ids.remove(detectionReportList.get(i).getId() + "");
                    }
                    Log.d("zw", " ids Size " + ids.size());
                    if (!pos.contains(i + "")) {

                        pos.remove(i + "");
                    }
                }
                Log.d("zw", "pos size " + pos.size());
            }
        });
        viewHolder.chkBox.setChecked(checks.get(i));

    }

    public List<String> getPos() {
        return pos;
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

    public class ViewHolderReport extends RecyclerView.ViewHolder {
        CheckBox chkBox;
        TextView txtDetSn;
        TextView txtDetBatch;
        TextView txtDetCode;
        TextView txtDrugName;
        TextView txtFactory;
        TextView txtFirstCount;
        TextView txtSecondCount;
        TextView txtDetDate;
        TextView txtDetOperator;
        TextView txtPDF;
        TextView txtDetDetail;
        TextView txtDetDel;
        TextView txtBottleType;

        public ViewHolderReport(View itemView) {
            super(itemView);
            chkBox = (CheckBox) itemView.findViewById(R.id.chkBox);
            txtDetSn = (TextView) itemView.findViewById(R.id.txtDetSn);
            txtDetBatch = (TextView) itemView.findViewById(R.id.txtDetBatch);
            txtDetCode = (TextView) itemView.findViewById(R.id.txtDetCode);
            txtDrugName = (TextView) itemView.findViewById(R.id.txtDrugName);
            txtFactory = (TextView) itemView.findViewById(R.id.txtFactory);
            txtFirstCount = (TextView) itemView.findViewById(R.id.txtFirstCount);
            txtSecondCount = (TextView) itemView.findViewById(R.id.txtSecondCount);
            txtDetDate = (TextView) itemView.findViewById(R.id.txtDetDate);
            txtDetOperator = (TextView) itemView.findViewById(R.id.txtDetOperator);
            txtPDF = (TextView) itemView.findViewById(R.id.txtPDF);
            txtDetDetail = (TextView) itemView.findViewById(R.id.txtDetDetail);
            txtDetDel = (TextView) itemView.findViewById(R.id.txtDetDel);
            txtBottleType = (TextView) itemView.findViewById(R.id.txtBottleType);

        }


    }

}