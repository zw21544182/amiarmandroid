package cn.ml_tech.mx.mlproj;

import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.ml_tech.mx.mlservice.DrugControls;

/**
 * Created by mx on 2017/4/22.
 */

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.ViewHolder> {
    private List<DrugControls> mDrugList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView drugName;
        TextView drugBottleType;
        TextView drugFactory;
        public ViewHolder(View itemView) {
            super(itemView);
            drugName = (TextView)itemView.findViewById(R.id.tvDrugName);
            drugBottleType = (TextView)itemView.findViewById(R.id.tvDrugBottleType);
            drugFactory = (TextView)itemView.findViewById(R.id.tvDrugFactory);
        }
    }
    public DrugAdapter(List<DrugControls> drugList) {
        mDrugList = drugList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drug_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DrugControls drug = mDrugList.get(position);
        holder.drugName.setText(drug.getDrugName());
        holder.drugBottleType.setText(drug.getDrugBottleType());
        holder.drugFactory.setText(drug.getDrugFactory());
    }

    @Override
    public int getItemCount() {
        return mDrugList.size();
    }

/*    public static class Drug {
        public Drug(String name, String type, String factory) {
            drugName = name;
            drugBottleType = type;
            drugFactory = factory;
        }
        public String getDrugName() {
            return drugName;
        }

        public void setDrugName(String drugName) {
            this.drugName = drugName;
        }

        public String getDrugBottleType() {
            return drugBottleType;
        }

        public void setDrugBottleType(String drugBottleType) {
            this.drugBottleType = drugBottleType;
        }

        public String getDrugFactory() {
            return drugFactory;
        }

        public void setDrugFactory(String drugFactory) {
            this.drugFactory = drugFactory;
        }

        private String drugName;
        private String drugBottleType;
        private String drugFactory;
    }*/

}
