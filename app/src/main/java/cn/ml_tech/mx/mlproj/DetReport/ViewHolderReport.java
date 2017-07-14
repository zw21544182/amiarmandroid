package cn.ml_tech.mx.mlproj.DetReport;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import cn.ml_tech.mx.mlproj.R;

/**
 * Created by ml on 2017/6/15.
 */

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
