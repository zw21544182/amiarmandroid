package cn.ml_tech.mx.mlproj.ypfragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.activity.YpjcActivity;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.adapter.StringAdapter;
import cn.ml_tech.mx.mlproj.util.ReceiverUtil;
import cn.ml_tech.mx.mlservice.DAO.DrugContainer;
import cn.ml_tech.mx.mlservice.DAO.DrugParam;
public class YpjqFragment extends BaseFragment {
    private EditText etBottlePara, etShadLocation;
    private TextView tvShadPara, tvColorCoefficient;
    private Spinner spParaType;
    private Button btEntryBottle, btValidate, btresver, btLeaveBottle, btBottlePara, btSave;
    YpjcActivity ypjcActivity;
    private HashMap<String, String> data;
    private boolean isEnter = false;
    private RelativeLayout imageLayout;
    private List<String> paramType;
    private View view;
    private StringAdapter stringAdapter;
    private LinearLayout zheLayout;
    public Map<String, String> getData() {
        return data;
    }
    public void setData(HashMap<String, String> data) {
        this.data = data;
    }
    private ReceiverUtil receiverUtil = null;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btEntryBottle:
                    try {
                        receiverUtil.inRegister();
                        ypjcActivity.mService.enterBottle();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btValidate:
                    if (!isEnter) {
                        Toast.makeText(getActivity(), "尚未进瓶子", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        ypjcActivity.mService.Validate(ypjcActivity.druginfo_id, Integer.parseInt(etShadLocation.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btresver:
                    if (isEnter) {
                        Toast.makeText(getActivity(), "进瓶状态下无法取消", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ypjcActivity.moveToMainFragment();
                    break;
                case R.id.btBottlePara:
                    try {
                        ypjcActivity.mService.bottleTest(Integer.parseInt(etBottlePara.getEditableText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btLeaveBottle:
                    try {
                        ypjcActivity.mService.leaveBottle();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private void initReciver() {
        receiverUtil = new ReceiverUtil(ReceiverUtil.ENTERBOTTLE, getActivity()) {
            @Override
            protected void operate(Context context, Intent intent) {
                String statue = intent.getExtras().getString("state");
                if (statue.equals("sucess")) {
                    isEnter = true;
                    btValidate.setEnabled(true);
                    getActivity().findViewById(R.id.btSave).setEnabled(false);
                    btLeaveBottle.setEnabled(true);
                    btEntryBottle.setEnabled(false);
                    etShadLocation.setEnabled(true);
                } else if (statue.equals("Validate")) {
                   tvColorCoefficient.setText(intent.getExtras().getInt("colornum") + "");
                    spParaType.setSelection((intent.getExtras().getInt("paratype") - 1));
                } else if (statue.equals("leavebottlesucess")) {
                   isEnter = false;
                    getActivity().findViewById(R.id.btSave).setEnabled(true);
                    getActivity().findViewById(R.id.btSave).setEnabled(true);
                }
            }
        };
    }
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_ypxq, null);
        initFindViewById(view);
        return view;
    }
    @Override
    public void initFindViewById(View view) {
        etBottlePara = (EditText) view.findViewById(R.id.etBottlePara);
        etShadLocation = (EditText) view.findViewById(R.id.etShadLocation);
        tvShadPara = (TextView) view.findViewById(R.id.tvShadPara);
        spParaType = (Spinner) view.findViewById(R.id.spParaType);
        tvColorCoefficient = (TextView) view.findViewById(R.id.tvColorCoefficient);
        btEntryBottle = (Button) view.findViewById(R.id.btEntryBottle);
        btValidate = (Button) view.findViewById(R.id.btValidate);
        btBottlePara = (Button) view.findViewById(R.id.btBottlePara);
        btLeaveBottle = (Button) view.findViewById(R.id.btLeaveBottle);
        btresver = (Button) view.findViewById(R.id.btresver);
        btSave = (Button) view.findViewById(R.id.btSave);
        imageLayout = (RelativeLayout) view.findViewById(R.id.imagelayout);
        etBottlePara.addTextChangedListener(new ViewTextWatcher(etBottlePara));
        etShadLocation.addTextChangedListener(new ViewTextWatcher(etShadLocation));
        tvShadPara.addTextChangedListener(new ViewTextWatcher(tvShadPara));
        tvColorCoefficient.addTextChangedListener(new ViewTextWatcher(tvColorCoefficient));
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btSave.setOnClickListener((View.OnClickListener) getActivity());
        btEntryBottle.setOnClickListener(listener);
        btValidate.setOnClickListener(listener);
        btresver.setOnClickListener(listener);
        btBottlePara.setOnClickListener(listener);
        btLeaveBottle.setOnClickListener(listener);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        data = new HashMap<>();
        paramType = new ArrayList<>();
        ypjcActivity = (YpjcActivity) getActivity();
        initReciver();
        paramType.add("参数类型1");
        paramType.add("参数类型2");
        paramType.add("参数类型3");
        paramType.add("参数类型4");
        stringAdapter = new StringAdapter(paramType, getActivity());
        spParaType.setAdapter(stringAdapter);
        setDataToView(ypjcActivity.pos, ypjcActivity.druginfo_id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        receiverUtil.unRefister();
    }

    public void setDataToView(int pos, int drug_id) {
        try {
            DrugContainer drugContainer = ypjcActivity.mService.getDrugContainer().get(pos);
            etBottlePara.setText(drugContainer.getRotatespeed() + "");
            etShadLocation.setText((int) drugContainer.getHeight() + "");
            data.put("rotateSpeed", drugContainer.getRotatespeed() + "");
            data.put("height", drugContainer.getHeight() + "");
            data.put("sendparam", drugContainer.getSendparam() + "");
            data.put("shadeParam", drugContainer.getShadeparam() + "");
            if (drug_id != 0) {
                Log.d("zw", "id!=0");
                List<DrugParam> drugParams = ypjcActivity.getDrugParams();
                for (DrugParam drugParam :
                        drugParams
                        ) {
                    Log.d("zw", drugParam.toString());
                    switch (drugParam.getParamname()) {
                        case "rotateSpeed":
                            etBottlePara.setText(drugParam.getParamvalue() + "");
                            break;
                        case "height":
                            etShadLocation.setText(drugParam.getParamvalue() + "");
                            break;
                        case "shadeParam":
                            tvShadPara.setText(drugParam.getParamvalue() + "");
                            break;
                        case "sendparam":
                            if (tvColorCoefficient != null)
                                tvColorCoefficient.setText(drugParam.getParamvalue() + "");
                            break;

                    }
                }
            }
        } catch (RemoteException e)

        {
            e.printStackTrace();
        }
    }

    private class ViewTextWatcher implements TextWatcher {
        private View view;
        private int id;

        public ViewTextWatcher(View view) {
            this.view = view;
            id = view.getId();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (id) {
                case R.id.etBottlePara:
                    data.put("rotateSpeed", s.toString());
                    break;
                case R.id.etShadLocation:
                    data.put("height", s.toString());
                    try {
                        if (imageLayout != null)
                            imageLayout.removeView(zheLayout);
                        ImageView imageView = new ImageView(getActivity());
                        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                        imageView.setBackground(); 设置图片
                        imageView.setLayoutParams(imageLayoutParams);
                        imageLayout.addView(imageView);
                        tvShadPara.setText(String.valueOf(getShadParaByLocation(Integer.parseInt(s.toString()))));
                        zheLayout = new LinearLayout(getActivity());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Integer.parseInt(s.toString()));
                        zheLayout.setLayoutParams(layoutParams);
                        zheLayout.setBackgroundColor(Color.BLUE);
                        imageLayout.addView(zheLayout);
                    } catch (Exception e) {
                    }
                    break;
                case R.id.tvShadPara:
                    data.put("shadeParam", s.toString());
                    break;
                case R.id.tvColorCoefficient:
                    data.put("sendparam", s.toString());
                    break;
            }
        }

    }

    private double getShadParaByLocation(int location) {
        return location / 3 + 0.234;
    }
}
