package cn.ml_tech.mx.mlproj.SettingFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.Adapter.StringAdapter;
import cn.ml_tech.mx.mlproj.BaseFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.DevParam;

/**
 * 创建时间: 2017/6/30
 * 创建人: zhongwang
 * 功能描述:
 */
public class ParmanageFragment extends BaseFragment implements View.OnClickListener {
    private static final int LOADSUCESS = 99;
    private static final int LOADFAILURE = 88;
    private Spinner spParaType;
    private Button btDelete;
    private Button btSubmit;
    private Button btCancel;
    private Button btBackUp;
    private Button btRecover;
    private CheckBox cbSeleceAll;
    private RecyclerView rvParamdata;
    private List<String> typeStrings;
    private StringAdapter stringAdapter;
    private List<DevParam> devParams;
    private ProgressDialog progressDialog;
    private ParamAdapter paramAdapter;
    private Handler handler = new Handler() {
        /**
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            switch (msg.what) {
                case LOADSUCESS:
                    if (paramAdapter == null) {
                        paramAdapter = new ParamAdapter(devParams);
                        Log.d("Zw", "devsize " + devParams.size());
                        rvParamdata.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvParamdata.setAdapter(paramAdapter);
                    } else {
                        paramAdapter.setDatasToView(devParams);
                    }

                    break;
                case LOADFAILURE:
                    showToast("加载失败");
                    break;
            }
        }
    };

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_parammanage, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        spParaType = (Spinner) view.findViewById(R.id.spParaType);
        btDelete = (Button) view.findViewById(R.id.btDelete);
        btSubmit = (Button) view.findViewById(R.id.btSubmit);
        btCancel = (Button) view.findViewById(R.id.btCancel);
        btBackUp = (Button) view.findViewById(R.id.btBackUp);
        btRecover = (Button) view.findViewById(R.id.btRecover);
        cbSeleceAll = (CheckBox) view.findViewById(R.id.cbSeleceAll);
        rvParamdata = (RecyclerView) view.findViewById(R.id.rvParamdata);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initSpinnerData();

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        cbSeleceAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (paramAdapter != null)
                    paramAdapter.operateAll(isChecked);
            }
        });
        spParaType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initParamData(position);
                cbSeleceAll.setChecked(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spParaType.setSelection(0);

        btDelete.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        btBackUp.setOnClickListener(this);
        btRecover.setOnClickListener(this);
    }

    private void initParamData(final int position) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    devParams = mlService.getDevParamByType(position);
                    handler.sendEmptyMessage(LOADSUCESS);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(LOADFAILURE);

                }
            }
        }.start();
    }

    private void initSpinnerData() {
        typeStrings = new ArrayList<>();
        typeStrings.add("可见异物检测");
        typeStrings.add("微粒异物检测");
        stringAdapter = new StringAdapter(typeStrings, getActivity());
        spParaType.setAdapter(stringAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btDelete:

                if (paramAdapter != null) {
                    final List<String> ids = paramAdapter.getIds();
                    if (progressDialog == null)
                        progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("操作中...");
                    progressDialog.show();
                    cbSeleceAll.setSelected(false);

                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                mlService.deleteDevParamByIds(ids);
                                initParamData(spParaType.getSelectedItemPosition());
                                handler.sendEmptyMessage(LOADSUCESS);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                handler.sendEmptyMessage(LOADFAILURE);

                            }
                        }
                    }.start();

                }
                break;
            case R.id.btSubmit:
                if (paramAdapter != null) {
                    final List<DevParam> devParams = paramAdapter.getDevParams();
                }
                if (progressDialog == null)
                    progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("操作中...");
                progressDialog.show();
                cbSeleceAll.setSelected(false);

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            mlService.saveDevParam(devParams);
                            initParamData(spParaType.getSelectedItemPosition());
                            handler.sendEmptyMessage(LOADSUCESS);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(LOADFAILURE);

                        }
                    }
                }.start();
                break;
            case R.id.btBackUp:
                if (progressDialog == null)
                    progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("操作中...");
                progressDialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            mlService.backUpDevParam();
                            initParamData(spParaType.getSelectedItemPosition());
                            handler.sendEmptyMessage(LOADSUCESS);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(LOADFAILURE);

                        }
                    }
                }.start();
                break;
            case R.id.btRecover:
                if (progressDialog == null)
                    progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("操作中...");
                progressDialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            mlService.recoveryParam();
                            initParamData(spParaType.getSelectedItemPosition());
                            handler.sendEmptyMessage(LOADSUCESS);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(LOADFAILURE);

                        }
                    }
                }.start();
                break;

        }
    }

    private class ParamAdapter extends RecyclerView.Adapter<ParamAdapter.ParamViewHolder> {
        private List<DevParam> devParams;
        private List<Boolean> booleanList;
        private List<String> ids;

        public ParamAdapter(List<DevParam> devParams) {
            this.devParams = devParams;
            booleanList = new ArrayList<>();
            ids = new ArrayList<>();
            for (DevParam devParam :
                    devParams) {
                booleanList.add(false);
            }
        }

        public void operateAll(boolean b) {
            for (int i = 0; i < booleanList.size(); i++) {
                booleanList.set(i, b);
            }
            ids.clear();
            if (b) {
                for (DevParam devParam :
                        devParams) {
                    ids.add(devParam.getId() + "");
                }
            }
            notifyDataSetChanged();
        }

        public List<DevParam> getDevParams() {
            return devParams;
        }

        public void setDatasToView(List<DevParam> devParams) {
            this.devParams.clear();
            booleanList.clear();
            this.devParams.addAll(devParams);
            for (DevParam devParam :
                    devParams) {
                booleanList.add(false);
            }
            notifyDataSetChanged();
        }

        public List<String> getIds() {
            return ids;
        }

        @Override
        public ParamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.paramitmelayout, parent, false);
            return new ParamViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ParamViewHolder holder, final int position) {
            holder.setIsRecyclable(false);
            final DevParam devParam = devParams.get(position);
            holder.checkBox.setChecked(booleanList.get(position));
            holder.etParamValue.setText(devParam.getParamValue() + "");
            holder.etParamName.setText(devParam.getParamName());
            holder.etParamName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        devParams.get(position).setParamName(s.toString());
                    } catch (NumberFormatException e) {
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            holder.etParamValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        devParams.get(position).setParamValue(Double.valueOf(s.toString()));
                    } catch (NumberFormatException e) {
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    booleanList.set(position, isChecked);
                    if (isChecked) ids.add(devParams.get(position).getId() + "");
                    else ids.remove(devParams.get(position).getId() + "");
                }
            });
        }

        @Override
        public int getItemCount() {
            return devParams.size();
        }

        class ParamViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;
            EditText etParamName;
            EditText etParamValue;

            public ParamViewHolder(View itemView) {
                super(itemView);
                checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
                etParamName = (EditText) itemView.findViewById(R.id.etParamName);
                etParamValue = (EditText) itemView.findViewById(R.id.etParamValue);
            }
        }
    }
}
