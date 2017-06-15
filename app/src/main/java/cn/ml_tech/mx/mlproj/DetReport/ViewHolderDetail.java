package cn.ml_tech.mx.mlproj.DetReport;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cn.ml_tech.mx.mlproj.R;

/**
 * Created by ml on 2017/6/15.
 */

public class ViewHolderDetail extends RecyclerView.ViewHolder {
   TextView txtDetailNumber;
   TextView txtDetailFloatResult;
   TextView txtDetailGlassResult;
   TextView txtDetailAnalyzeResult;
   TextView txtDetailNodeResult;
   TextView txtDetailSuperNodeResult;
   TextView txtDetailAllResult;
   TextView txtDetailIsPositive;
   TextView txtDetailVideo;
   TextView txtDetailIsValid;
   TextView txtDetailDetType;
   TextView txtDetailRotateTime;
   TextView txtDetailStopTime;
   TextView txtDetailRotateStateText;
   TextView txtDetailStopStateText;
   TextView txtDetailColorFactor;


    public ViewHolderDetail(View itemView) {
        super(itemView);
        txtDetailNumber= (TextView) itemView.findViewById(R.id.txtDetailNumber);
        txtDetailFloatResult= (TextView) itemView.findViewById(R.id.txtDetailFloatResult);
        txtDetailGlassResult= (TextView) itemView.findViewById(R.id.txtDetailGlassResult);
        txtDetailAnalyzeResult= (TextView) itemView.findViewById(R.id.txtDetailAnalyzeResult);
        txtDetailNodeResult= (TextView) itemView.findViewById(R.id.txtDetailNodeResult);
        txtDetailSuperNodeResult= (TextView) itemView.findViewById(R.id.txtDetailSuperNodeResult);
        txtDetailAllResult= (TextView) itemView.findViewById(R.id.txtDetailAllResult);
        txtDetailIsPositive= (TextView) itemView.findViewById(R.id.txtDetailIsPositive);
        txtDetailVideo= (TextView) itemView.findViewById(R.id.txtDetailVideo);
        txtDetailIsValid= (TextView) itemView.findViewById(R.id.txtDetailIsValid);
        txtDetailDetType= (TextView) itemView.findViewById(R.id.txtDetailDetType);
        txtDetailRotateTime= (TextView) itemView.findViewById(R.id.txtDetailRotateTime);
        txtDetailStopTime= (TextView) itemView.findViewById(R.id.txtDetailStopTime);
        txtDetailRotateStateText= (TextView) itemView.findViewById(R.id.txtDetailRotateStateText);
        txtDetailStopStateText= (TextView) itemView.findViewById(R.id.txtDetailStopStateText);
        txtDetailColorFactor= (TextView) itemView.findViewById(R.id.txtDetailColorFactor);

    }
}
