package cn.ml_tech.mx.mlproj.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DrugControls;

/**
 * Created by mx on 2017/4/22.
 */

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.ViewHolder> {
    private List<DrugControls> mDrugList;
    private Context context;
    private List<Boolean> isClicks = new ArrayList<>();//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色
    private int itmepostion;
    private OperateToData operateToData;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            switch (v.getId()) {
                case R.id.toplayout:
                    for (int i = 0; i < isClicks.size(); i++) {
                        isClicks.set(i, false);
                    }
                    isClicks.set(position, true);

                    itmepostion = position;

                    operateToData.operateToPre(true);
                    notifyDataSetChanged();
                    break;
                case R.id.delete:
                    operateToData.delete(mDrugList.get(position).getId());
                    break;
                case R.id.update:
                    operateToData.update(mDrugList.get(position));
                    break;
            }
        }
    };

    public interface OperateToData {
        boolean delete(long id);

        void operateToPre(boolean isNext);

        void update(DrugControls drugControls);
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

    public DrugAdapter(List<DrugControls> drugList, Context context, OperateToData operateToData) {
        mDrugList = drugList;
        this.context = context;
        this.operateToData = operateToData;
        for (int i = 0; i < mDrugList.size(); i++) {
            isClicks.add(false);
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

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drug_item, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DrugControls drug = mDrugList.get(position);
        holder.drugName.setText(drug.getDrugName());
        holder.drugBottleType.setText(drug.getDrugBottleType());
        holder.drugFactory.setText(drug.getDrugFactory());
        holder.drugPinYin.setText(drug.getPinyin());
        holder.drugEnName.setText(drug.getEnname());
        holder.update.setText(Html.fromHtml("<u>" + "修改" + "</u>"));
        holder.delete.setText(Html.fromHtml("<u>" + "删除" + "</u>"));
        if (isClicks.get(position)) {
            holder.rootlayout.setBackgroundColor(Color.parseColor("#D6D7D7"));
        } else {
            holder.rootlayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.delete.setTag(position);
        holder.update.setTag(position);
        holder.rootlayout.setTag(position);
        holder.delete.setOnClickListener(listener);
        holder.update.setOnClickListener(listener);
        holder.rootlayout.setOnClickListener(listener);

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
