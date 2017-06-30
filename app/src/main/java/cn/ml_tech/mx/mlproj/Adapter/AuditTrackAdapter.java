package cn.ml_tech.mx.mlproj.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.AuditTrail;

public class AuditTrackAdapter extends RecyclerView.Adapter<AuditTrackAdapter.ViewHolder> {
    private List<AuditTrail> mDrugList;
    private Context context;


    static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    public AuditTrackAdapter(List<AuditTrail> drugList, Context context) {
        mDrugList = drugList;
        this.context = context;
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


    }

    @Override
    public int getItemCount() {
        return mDrugList.size();
    }

}
