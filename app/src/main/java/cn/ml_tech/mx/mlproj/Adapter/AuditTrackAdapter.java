package cn.ml_tech.mx.mlproj.Adapter;

import android.content.Context;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.ml_tech.mx.mlproj.BaseActivity;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.AuditTrail;
import cn.ml_tech.mx.mlservice.DAO.AuditTrailEventType;
import cn.ml_tech.mx.mlservice.DAO.AuditTrailInfoType;

public class AuditTrackAdapter extends RecyclerView.Adapter<AuditTrackAdapter.ViewHolder> {
    private List<AuditTrail> mDrugList;
    private BaseActivity baseActivity;
    private List<AuditTrailInfoType> infoTypes;
    private List<AuditTrailEventType> eventTypes;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvuser;
        private TextView tvevent;
        private TextView tvinfo;
        private TextView tvvalue;
        private TextView tvtime;
        private TextView tvmark;


        public ViewHolder(View itemView) {
            super(itemView);
            tvuser = (TextView) itemView.findViewById(R.id.tvuser);
            tvevent = (TextView) itemView.findViewById(R.id.tvevent);
            tvinfo = (TextView) itemView.findViewById(R.id.tvinfo);
            tvvalue = (TextView) itemView.findViewById(R.id.tvvalue);
            tvtime = (TextView) itemView.findViewById(R.id.tvtime);
            tvmark = (TextView) itemView.findViewById(R.id.tvmark);

        }
    }

    public AuditTrackAdapter(List<AuditTrail> drugList, Context context) {
        mDrugList = drugList;
        this.baseActivity = (BaseActivity) context;
        try {
            infoTypes = baseActivity.mService.getAuditTrailInfoType();
            eventTypes = baseActivity.mService.getAuditTrailEventType();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audittrackdate_itmelayout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvmark.setText(mDrugList.get(position).getMark());
        holder.tvtime.setText(mDrugList.get(position).getTime());
        holder.tvuser.setText(mDrugList.get(position).getUsername());
        holder.tvvalue.setText(mDrugList.get(position).getValue());
        for (AuditTrailEventType eventType :
                eventTypes) {
            if (eventType.getId() == mDrugList.get(position).getEvent_id()) {
                holder.tvevent.setText(eventType.getName());
            }
        }
        for (AuditTrailInfoType infoType :
                infoTypes) {
            if (infoType.getId() == mDrugList.get(position).getInfo_id()) {
                holder.tvinfo.setText(infoType.getName());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDrugList.size();
    }

}
