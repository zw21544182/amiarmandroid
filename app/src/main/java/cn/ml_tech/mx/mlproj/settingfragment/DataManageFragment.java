package cn.ml_tech.mx.mlproj.settingfragment;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.ml_tech.mx.mlproj.customview.MyHorizontalScrollView;
import cn.ml_tech.mx.mlproj.customview.SystemSetUp.DataItmeView;
import cn.ml_tech.mx.mlproj.adapter.StringAdapter;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.Modern;
import cn.ml_tech.mx.mlservice.IMlService;

import static cn.ml_tech.mx.mlproj.util.CommonUtil.FAILURE;
import static cn.ml_tech.mx.mlproj.util.CommonUtil.SUCESS;

/**
 * 创建时间: 2017/6/30
 * 创建人: zhongwang
 * 功能描述:数据管理fragment
 */
public class DataManageFragment extends BaseFragment implements View.OnClickListener {
    private static final int DELETESUCESS = 99;
    private static final int DELETEFAILURE = 33;
    private ProgressDialog progressDialog = null;
    private Spinner spDataTyle;
    private Button btAdd;
    private Button btDelete;
    private Button btRevoke;
    private Button btSubmit;
    private LinearLayout toplayout;
    private RecyclerView rvData;
    private IMlService mlService;
    private List<String> tabNames;
    private List<String> filedNames;
    private StringAdapter tabNameAdapter;
    private String tableName;
    private Modern modern;
    private TableAdapter tableAdapter;
    private HorizontalScrollView horizon;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCESS:
                    initFiledInfo();
                    Map<Integer, List<String>> data = modern.getMap();
                    if (tableAdapter == null) {
                        tableAdapter = new TableAdapter(getActivity(), data);
                        rvData.setAdapter(tableAdapter);
                    } else {
                        tableAdapter.setDataToView(data);
                    }

                    break;
                case FAILURE:
                    showToast("数据加载失败");
                    break;
                case DELETEFAILURE:
                    break;
                case DELETESUCESS:
                    initTableData();
                    break;
            }

        }
    };

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_datamanage, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        spDataTyle = (Spinner) view.findViewById(R.id.spDataTyle);
        btAdd = (Button) view.findViewById(R.id.btAdd);
        btDelete = (Button) view.findViewById(R.id.btDelete);
        btRevoke = (Button) view.findViewById(R.id.btRevoke);
        btSubmit = (Button) view.findViewById(R.id.btSubmit);
        toplayout = (LinearLayout) view.findViewById(R.id.toplayout);
        rvData = (RecyclerView) view.findViewById(R.id.rvData);
        rvData.setLayoutManager(new LinearLayoutManager(getActivity()));
        horizon = (MyHorizontalScrollView) view.findViewById(R.id.horizon);
        horizon.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        horizon.setFocusable(false);
        horizon.setFocusableInTouchMode(false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mlService = mActivity.getmService();
        initTableInfo();
        rvData.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if (tableAdapter != null && progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            initData(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btSubmit.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        spDataTyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tableName = spDataTyle.getSelectedItem().toString();
                initTableData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initTableData() {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("数据加载中......");
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    filedNames = mlService.getFieldByName(tableName);
                    modern = mlService.getDataByTableName(tableName, 1);
                    handler.sendEmptyMessage(SUCESS);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(FAILURE);

                }

            }
        }.start();

    }

    private void initFiledInfo() {
        toplayout.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(getActivity());
        CheckBox checkBox = new CheckBox(getActivity());
        checkBox.setBackgroundResource(R.color.colorheadLine);
        linearLayout.setBackgroundResource(R.color.colorheadLine);
        LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        checkBox.setLayoutParams(checkParams);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tableAdapter.setAllChecked(isChecked);
            }
        });
        linearLayout.addView(checkBox);
        toplayout.addView(linearLayout);
        if (filedNames != null) {
            for (String name : filedNames) {
                Log.d("zw", name);
                DataItmeView dataItmeView = new DataItmeView(getActivity(), null);
                dataItmeView.setContentValue(name);
                dataItmeView.setContentEdit(false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                dataItmeView.setLayoutParams(layoutParams);
                toplayout.addView(dataItmeView);
            }
        }

    }

    private void initTableInfo() {
        try {
            tabNames = mlService.getAllTableName();
            if (tabNames != null) {
                tabNameAdapter = new StringAdapter(tabNames, getActivity());
                spDataTyle.setAdapter(tabNameAdapter);
                spDataTyle.setSelection(0);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSubmit:
                if (modern == null)
                    modern = new Modern();
                modern.setMap(tableAdapter.getData());
                try {
                    mlService.updateData(tableName, modern);
                    initTableData();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btDelete:
                final List<String> ids = tableAdapter.getPos();
                if (ids != null && ids.size() != 0) {
                    if (progressDialog == null)
                        progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("删除中....");
                    progressDialog.show();
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                mlService.deleteData(tableName, ids);
                                handler.sendEmptyMessage(DELETESUCESS);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                handler.sendEmptyMessage(DELETEFAILURE);
                            }
                        }
                    }.start();
                } else {
                    showToast("尚未选中");
                }
                break;
            case R.id.btAdd:
                rvData.scrollToPosition(tableAdapter.getItemCount() - 1);
                tableAdapter.addNewDataToView();
                break;
        }
    }

    public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder> {
        private Context context;
        private Map<Integer, List<String>> data;
        private List<String> pos;
        private List<Boolean> checks;

        public List<String> getPos() {
            return pos;
        }

        public TableAdapter(Context context, Map<Integer, List<String>> data) {
            pos = new ArrayList<>();
            checks = new ArrayList<>();
            this.context = context;
            this.data = data;

            for (int i = 0; i < data.size(); i++) {
                checks.add(false);
            }
        }

        public void setAllChecked(boolean checked) {
            pos.clear();
            for (int i = 0; i < checks.size(); i++)
                checks.set(i, checked);
            if (checked) {
                for (int c = 0; c < data.size(); c++) {
                    pos.add(data.get(c).get(0));
                }
            }
            notifyDataSetChanged();

        }

        public void addNewDataToView() {
            List<String> newData = new ArrayList<>();
            for (int i = 0; i < data.get(data.size() - 1).size(); i++) {
                if (i == 0) {
                    String id = (Integer.parseInt(data.get(data.size() - 1).get(0)) + 1) + "";
                    newData.add(id);
                } else {
                    newData.add("");
                }
            }
            data.put(data.size(), newData);
            checks.clear();
            for (int i = 0; i < data.size(); i++) {
                checks.add(false);
            }
            notifyDataSetChanged();
        }

        public void setDataToView(Map<Integer, List<String>> data) {
            Log.d("zw", "setDataToView");
            this.data.clear();
            pos.clear();
            this.data.putAll(data);
            checks.clear();
            for (int i = 0; i < data.size(); i++) {
                checks.add(false);
            }
            notifyDataSetChanged();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.dataitmelayout, parent, false);
            return new MyViewHolder(view);
        }

        public Map<Integer, List<String>> getData() {
            return data;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.linearLayout.removeAllViews();
            final CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setBackgroundResource(R.color.colorheadLine);
            LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            checkBox.setLayoutParams(checkParams);
            checkBox.setChecked(checks.get(position));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checks.set(position, isChecked);
                    if (isChecked) {
                        pos.add(data.get(position).get(0));
                    } else {
                        pos.remove(data.get(position).get(0));
                    }
                }
            });
            holder.linearLayout.addView(checkBox);
            final List<String> datas = data.get(position);
            for (int i = 0; i < datas.size(); i++) {
                String value = datas.get(i);
                DataItmeView dataItmeView = new DataItmeView(getActivity(), null);
                dataItmeView.setContentValue(value);
                if (i == 0)
                    dataItmeView.setContentEdit(false);
                dataItmeView.setBackGroudColor(R.color.colorWhite);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                dataItmeView.setLayoutParams(layoutParams);
                holder.linearLayout.addView(dataItmeView);
                EditText content = dataItmeView.getContentView();
                final int finalI = i;
                content.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        data.get(position).set(finalI, s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }

        }

        /**
         * @return
         */
        @Override
        public int getItemCount() {
            return data.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout linearLayout;

            public MyViewHolder(View itemView) {
                super(itemView);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            }
        }
    }
}
