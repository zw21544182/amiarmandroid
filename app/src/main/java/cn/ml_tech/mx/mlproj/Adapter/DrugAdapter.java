package cn.ml_tech.mx.mlproj.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DrugControls;

/**
 * Created by mx on 2017/4/22.
 */

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.ViewHolder> {
    private List<DrugControls> mDrugList;
    private Context context;

    enum TYPE {
        TOP, NOR
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView drugName;
        TextView drugBottleType;
        TextView drugFactory;
        TextView drugPinYin;
        TextView drugEnName;
        TextView update;
        TextView delete;
        LinearLayout rootlayout;

        public ViewHolder(View itemView) {
            super(itemView);
            rootlayout = (LinearLayout) itemView.findViewById(R.id.toplayout);
            drugName = (TextView) itemView.findViewById(R.id.tvDrugName);
            drugBottleType = (TextView) itemView.findViewById(R.id.tvDrugBottleType);
            drugFactory = (TextView) itemView.findViewById(R.id.tvDrugFactory);
            drugPinYin = (TextView) itemView.findViewById(R.id.tvChinese);
            drugEnName = (TextView) itemView.findViewById(R.id.tvEnglish);
            update = (TextView) itemView.findViewById(R.id.update);
            delete = (TextView) itemView.findViewById(R.id.delete);
        }
    }

    public DrugAdapter(List<DrugControls> drugList, Context context) {
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
        if (viewType == TYPE.TOP.ordinal()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drug_topitme, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drug_item, parent, false);
        }
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE.TOP.ordinal();
        } else {
            return TYPE.NOR.ordinal();
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
        } else {
            DrugControls drug = mDrugList.get(position - 1);
            holder.drugName.setText(drug.getDrugName());
            holder.drugBottleType.setText(drug.getDrugBottleType());
            holder.drugFactory.setText(drug.getDrugFactory());
            holder.drugPinYin.setText(drug.getPinyin());
            holder.drugEnName.setText(drug.getEnname());
            holder.update.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            holder.delete.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return mDrugList.size() + 1;
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
