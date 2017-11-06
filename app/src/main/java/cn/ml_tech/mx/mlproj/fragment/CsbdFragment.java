package cn.ml_tech.mx.mlproj.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.activity.CsbdActivity;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.customview.DrugStandardCheck.WorkflowitemView;
import cn.ml_tech.mx.mlproj.util.CommonUtil;
import cn.ml_tech.mx.mlproj.util.ReceiverUtil;
import cn.ml_tech.mx.mlservice.DAO.Permission;

public class CsbdFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private Button btBack;
    private TextView tvNotice;
    private Button btStart;
    private Button btApply;
    private Button btExport;
    private TextView txt40um;
    private TextView txt50um;
    private TextView txt60um;
    private TextView txt40umvariance;
    private TextView txt60umvariance;
    private TextView txtrotatetime;
    private TextView txtstoptime;
    private TextView txtcolorfactor;
    private CsbdActivity csbdActivity;
    private Permission permission;
    private LinearLayout layoutworkflow;
    private ReceiverUtil receiverUtil;
    private LinearLayout ltresult;
    private List<WorkflowitemView> workflowitemViewList;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_csbd, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        btBack = (Button) view.findViewById(R.id.btBack);
        layoutworkflow = (LinearLayout) view.findViewById(R.id.layoutworkflow);
        tvNotice = (TextView) view.findViewById(R.id.tvNotice);
        btStart = (Button) view.findViewById(R.id.btStart);
        btApply = (Button) view.findViewById(R.id.btApply);
        btExport = (Button) view.findViewById(R.id.btExport);
        txt40um = (TextView) view.findViewById(R.id.txt40um);
        txt50um = (TextView) view.findViewById(R.id.txt50um);
        txt60um = (TextView) view.findViewById(R.id.txt60um);
        txt40umvariance = (TextView) view.findViewById(R.id.txt40umvariance);
        txt60umvariance = (TextView) view.findViewById(R.id.txt60umvariance);
        txtrotatetime = (TextView) view.findViewById(R.id.txtrotatetime);
        txtstoptime = (TextView) view.findViewById(R.id.txtstoptime);
        txtcolorfactor = (TextView) view.findViewById(R.id.txtcolorfactor);
        ltresult = (LinearLayout) view.findViewById(R.id.resultLayout);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        loadView();
        btBack.setOnClickListener(this);
        btStart.setOnClickListener(this);
        btApply.setOnClickListener(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        csbdActivity = (CsbdActivity) getActivity();
        permission = csbdActivity.permission;
        initReceiver();
    }

    private void loadView() {
        workflowitemViewList = new ArrayList<>();
        List<String> list = new ArrayList<String>();
        list.add("自动进样");
        list.add("机械手取样");
        list.add("固定样品");
        list.add("高速旋瓶");
        list.add("激光扫描");
        list.add("结果显示");
        list.add("自动分拣");
        for (int var = 0; var < list.size(); var++) {
            String[] temp = list.get(var).split("");
            String string = TextUtils.join("\n", temp);
            string = string.substring(1, string.length());
            list.set(var, string);
        }
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int var = 0; var < list.size(); var++) {
            WorkflowitemView w = new WorkflowitemView(getActivity(), null, list.get(var), Color.BLACK, Color.BLUE);
            w.setHeight(200);
            w.setMinWidth(60);
            w.setMaxWidth(80);
            w.getPaint().setFakeBoldText(true);
            w.setGravity(Gravity.CENTER);
            w.setTextSize(22);
            w.setBackgroundColor(Color.BLUE);
            w.setTextcolor(Color.RED);
            llp.setMargins(0, 0, 20, 0);
            w.setLayoutParams(llp);
            workflowitemViewList.add(w);
            layoutworkflow.addView(w);
            if (var < list.size() - 1) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.arrowright));
                llp.setMargins(20, 0, 0, 0);
                imageView.setLayoutParams(llp);
                layoutworkflow.addView(imageView);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btBack:
                getActivity().finish();
                break;
            case R.id.btStart:

                try {
                    mlService.autoDebug(CommonUtil.AUTOEBUG_CALIBRATE, 1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        receiverUtil.unRefister();
    }

    private void initReceiver() {
        receiverUtil = new ReceiverUtil("com.calibration", getActivity()) {
            @Override
            protected void operate(Context context, Intent intent) {
                int state = intent.getExtras().getInt("state");
                for (int i = 0; i < workflowitemViewList.size(); i++) {
                    workflowitemViewList.get(i).setBackgroundColor(Color.BLUE);
                    if (i == state) {
                        workflowitemViewList.get(i).setBackgroundColor(Color.RED);
                        workflowitemViewList.get(i).setTextcolor(Color.GREEN);
                    }

                }
                if (state == 3 || state == 4) {
                    workflowitemViewList.get(3).setBackgroundColor(Color.RED);
                    workflowitemViewList.get(3).setTextcolor(Color.GREEN);
                    workflowitemViewList.get(4).setBackgroundColor(Color.RED);
                    workflowitemViewList.get(4).setTextcolor(Color.GREEN);


                }
                tvNotice.setText("标定参数进行中");
                if (state == 5) {
                    txt40um.setText(intent.getExtras().getString("standard40"));
                    txt50um.setText(intent.getExtras().getString("standard50"));
                    txt60um.setText(intent.getExtras().getString("standard60"));
                    txt40umvariance.setText(intent.getExtras().getString("variance40"));
                    txt60umvariance.setText(intent.getExtras().getString("variance60"));
                    txtrotatetime.setText(intent.getExtras().getString("statime"));
                    txtstoptime.setText(intent.getExtras().getString("stotime"));
                    txtcolorfactor.setText(intent.getExtras().getString("18.29"));
                    intent.putExtra("stpstate", "normal");
                    intent.putExtra("stostate", "normal");
                }
                if (state == 8) {
                    showToast("标定结束");
                }
            }
        };
        receiverUtil.inRegister();
    }
}
