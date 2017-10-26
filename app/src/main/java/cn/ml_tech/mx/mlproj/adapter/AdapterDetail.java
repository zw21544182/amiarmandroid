package cn.ml_tech.mx.mlproj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.DetectionDetail;


/**
 * Created by ml on 2017/6/15.
 */
public class AdapterDetail extends RecyclerView.Adapter<AdapterDetail.ViewHolderDetail> implements View.OnClickListener {
    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

    private Context mContex;
    private OnItemClickListener mOnItemClickListener = null;
    private List<DetectionDetail> detectionDetailList = new ArrayList<>();
    private String ResultPositive;
    private String ResultNegative;
    private String VideoText;
    private String NormalText;
    private String AbNormalText;
    private String FirstDet;
    private String SecondDet;

    @Override
    public void onClick(View v) {
        if (null != mOnItemClickListener)
            mOnItemClickListener.OnItemClick(v, (Integer) v.getTag());
    }


    public void UpdateData(List<DetectionDetail> list) {
        this.detectionDetailList = list;
        notifyDataSetChanged();
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public AdapterDetail(Context mContex, List<DetectionDetail> detectionDetailList) {
        this.mContex = mContex;
        this.detectionDetailList = detectionDetailList;
        ResultPositive = this.mContex.getString(R.string.ResultPositive);
        ResultNegative = this.mContex.getString(R.string.ResultNegative);
        VideoText = this.mContex.getString(R.string.DetailVideo);
        AbNormalText = this.mContex.getString(R.string.AbNormalText);
        NormalText = this.mContex.getString(R.string.NormalText);
        FirstDet = this.mContex.getString(R.string.FirstCount);
        SecondDet = this.mContex.getString(R.string.SecondCount);

    }

    @Override
    public ViewHolderDetail onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(this.mContex).inflate(R.layout.recyledetail, viewGroup, false);
        ViewHolderDetail viewHolderDetail = new ViewHolderDetail(view);
        if (null != mOnItemClickListener) {
            viewHolderDetail.txtDetailVideo.setOnClickListener(this);
            viewHolderDetail.txtDetailAllResult.setOnClickListener(this);
        }
        return viewHolderDetail;
    }

    @Override
    public void onBindViewHolder(ViewHolderDetail viewHolderDetail, int i) {
        if (detectionDetailList.get(i).getRepIndex() == 0)
            viewHolderDetail.txtDetailNumber.setText(String.valueOf(detectionDetailList.get(i).getDetIndex()));
        else
            viewHolderDetail.txtDetailNumber.setText(String.valueOf(detectionDetailList.get(i).getRepIndex()));
        viewHolderDetail.txtDetailFloatResult.setText(detectionDetailList.get(i).isFloatPositive() ? ResultPositive : ResultNegative);
        viewHolderDetail.txtDetailGlassResult.setText(String.valueOf(detectionDetailList.get(i).isGlassPositive() ? ResultPositive : ResultNegative));
        viewHolderDetail.txtDetailAnalyzeResult.setText(String.valueOf(detectionDetailList.get(i).isAnalyzePositive() ? ResultPositive : ResultNegative));
        viewHolderDetail.txtDetailNodeResult.setText(String.valueOf(detectionDetailList.get(i).isNodePositive() ? ResultPositive : ResultNegative));
        viewHolderDetail.txtDetailSuperNodeResult.setText(String.valueOf(detectionDetailList.get(i).isSuperPositive() ? ResultPositive : ResultNegative));
        viewHolderDetail.txtDetailIsPositive.setText(String.valueOf(detectionDetailList.get(i).isPositive() ? ResultPositive : ResultNegative));
        viewHolderDetail.txtDetailVideo.setText(VideoText);
        viewHolderDetail.txtDetailIsValid.setText(String.valueOf(detectionDetailList.get(i).isValid() ? NormalText : AbNormalText));
        if (0 == detectionDetailList.get(i).getRepIndex())
            viewHolderDetail.txtDetailDetType.setText(FirstDet);
        else viewHolderDetail.txtDetailDetType.setText(SecondDet);
        viewHolderDetail.txtDetailRotateTime.setText(String.valueOf(detectionDetailList.get(i).getScrTime()));
        viewHolderDetail.txtDetailStopTime.setText(String.valueOf(detectionDetailList.get(i).getStpTime()));
        viewHolderDetail.txtDetailRotateStateText.setText(String.valueOf(detectionDetailList.get(i).getScrTimeText()));
        viewHolderDetail.txtDetailStopStateText.setText(String.valueOf(detectionDetailList.get(i).getStpTimeText()));
        viewHolderDetail.txtDetailColorFactor.setText(String.valueOf(detectionDetailList.get(i).getColorFactor()));
        viewHolderDetail.txtDetailVideo.setTag(i);
        viewHolderDetail.txtDetailAllResult.setTag(i);
        viewHolderDetail.txtDetailVideo.setText(Html.fromHtml("<u>" + "视频" + "</u>"));
        viewHolderDetail.txtDetailAllResult.setText(Html.fromHtml("<u>" + "结果详情" + "</u>"));
    }

    @Override
    public int getItemCount() {
        return null == detectionDetailList ? 0 : detectionDetailList.size();
    }

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
            txtDetailNumber = (TextView) itemView.findViewById(R.id.txtDetailNumber);
            txtDetailFloatResult = (TextView) itemView.findViewById(R.id.txtDetailFloatResult);
            txtDetailGlassResult = (TextView) itemView.findViewById(R.id.txtDetailGlassResult);
            txtDetailAnalyzeResult = (TextView) itemView.findViewById(R.id.txtDetailAnalyzeResult);
            txtDetailNodeResult = (TextView) itemView.findViewById(R.id.txtDetailNodeResult);
            txtDetailSuperNodeResult = (TextView) itemView.findViewById(R.id.txtDetailSuperNodeResult);
            txtDetailAllResult = (TextView) itemView.findViewById(R.id.txtDetailAllResult);
            txtDetailIsPositive = (TextView) itemView.findViewById(R.id.txtDetailIsPositive);
            txtDetailVideo = (TextView) itemView.findViewById(R.id.txtDetailVideo);
            txtDetailIsValid = (TextView) itemView.findViewById(R.id.txtDetailIsValid);
            txtDetailDetType = (TextView) itemView.findViewById(R.id.txtDetailDetType);
            txtDetailRotateTime = (TextView) itemView.findViewById(R.id.txtDetailRotateTime);
            txtDetailStopTime = (TextView) itemView.findViewById(R.id.txtDetailStopTime);
            txtDetailRotateStateText = (TextView) itemView.findViewById(R.id.txtDetailRotateStateText);
            txtDetailStopStateText = (TextView) itemView.findViewById(R.id.txtDetailStopStateText);
            txtDetailColorFactor = (TextView) itemView.findViewById(R.id.txtDetailColorFactor);

        }
    }

}
