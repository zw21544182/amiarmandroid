package cn.ml_tech.mx.mlproj.SettingFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.ml_tech.mx.CustomView.SystemSetUp.DataParamView;
import cn.ml_tech.mx.mlproj.Adapter.StringAdapter;
import cn.ml_tech.mx.mlproj.BaseFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.Modern;

/**
 * 创建时间: 2017/6/30
 * 创建人: zhongwang
 * 功能描述:数据管理fragment
 */
public class DataManageFragment extends BaseFragment implements View.OnClickListener {
    private Spinner spDataTyle;
    private Button btAdd;
    private Button btDelete;
    private Button btRevoke;
    private Button btSubmit;
    private LinearLayout datalayout;
    private List<String> tableList = new ArrayList<>();
    private List<String> itemList = new ArrayList<>();
    private Modern modern;
    private RecyclerView rvdata;
    private DataAdapter adapter;

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
        datalayout = (LinearLayout) view.findViewById(R.id.datalayout);
        rvdata = (RecyclerView) view.findViewById(R.id.rvdata);
        btSubmit.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        btRevoke.setOnClickListener(this);
        btAdd.setOnClickListener(this);
    }

    @Override
    protected void initEvent() {
        super.initEvent();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            tableList = mActivity.mService.getAllTableName();
            spDataTyle.setAdapter(new StringAdapter(tableList, getActivity()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        event();
    }


    private void event() {
        spDataTyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tableName = tableList.get(position);
                try {
                    itemList = mActivity.mService.getFieldByName(tableName);
                    modern = mActivity.mService.getDataByTableName(tableName);
                    addTopView(itemList, modern);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void addTopView(List<String> itemList, Modern modern) {
        datalayout.removeAllViews();
        addCheckBox();
        for (int i = 0; i < itemList.size(); i++) {
            String content = itemList.get(i);
            DataParamView dataParamView = new DataParamView(getActivity(), null, content);
            dataParamView.setEditEnable(false);
            dataParamView.setBackgroundColor(R.color.colorWhite);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            dataParamView.setLayoutParams(layoutParams);
            datalayout.addView(dataParamView);
        }
        if (null != modern.getMap().get(0)) {
            Log.d("zw", "sucess");
        }
        if (adapter != null) {
            adapter.setDataToView(modern.getMap());
        } else {
            adapter = new DataAdapter(modern.getMap(), getActivity());
        }
        rvdata.setAdapter(adapter);
        rvdata.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void addCheckBox() {
        CheckBox checkBox = new CheckBox(getActivity());
        LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        checkBox.setGravity(Gravity.CENTER);
        checkBox.setBackgroundColor(R.color.colorheadLine);
        checkBox.setVisibility(View.INVISIBLE);
        checkBox.setLayoutParams(checkParams);
        datalayout.addView(checkBox);
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSubmit:
                modern = new Modern();
                modern.setMap(adapter.getData());
                try {
                    mActivity.mService.updateData(spDataTyle.getSelectedItem().toString().trim(), modern);
                    Toast.makeText(getActivity(), "sucess", Toast.LENGTH_SHORT).show();
                    modern = mActivity.mService.getDataByTableName(spDataTyle.getSelectedItem().toString().trim());
                    adapter.setDataToView(modern.getMap());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btDelete:
                List<String> ids = adapter.getIdList();
                if (ids.size() == 0) {
                    Toast.makeText(getActivity(), "请选中", Toast.LENGTH_SHORT).show();

                    return;
                }
                try {
                    mActivity.mService.deleteData(spDataTyle.getSelectedItem().toString().trim(), ids);
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                    modern = mActivity.mService.getDataByTableName(spDataTyle.getSelectedItem().toString());
                    adapter.setDataToView(modern.getMap());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btRevoke:
                try {
                    if (adapter != null) {


                        adapter.setDataToView(mActivity.mService.getDataByTableName(spDataTyle.getSelectedItem().toString()).getMap());

                    } else {
                        adapter = new DataAdapter(mActivity.mService.getDataByTableName(spDataTyle.getSelectedItem().toString()).getMap(), getActivity());
                        rvdata.setAdapter(adapter);
                        rvdata.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btAdd:
                rvdata.smoothScrollToPosition(modern.getMap().size());
                adapter.addNewDataToView();
                break;
        }

    }


    private class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
        Map<Integer, List<String>> data;
        Context context;
        List<String> id = new ArrayList<>();
        List<Boolean> isCheck = new ArrayList<>();

        public DataAdapter(Map<Integer, List<String>> data, Context context) {
            this.data = data;
            this.context = context;
            for (int i = 0; i < data.size(); i++) {
                isCheck.add(false);
            }
        }

        public List<String> getIdList() {
            return id;
        }

        public void setDataToView(Map<Integer, List<String>> data) {
            this.data.clear();
            this.data.putAll(data);
            isCheck.clear();
            for (int i = 0; i < data.size(); i++) {
                isCheck.add(false);
            }
            notifyDataSetChanged();
        }

        public void addNewDataToView() {
            String id = "1";
            if (data.get(data.size() - 1) != null) {
                id = (Integer.parseInt(data.get(data.size() - 1).get(0)) + 1) + "";
                List<String> list = new ArrayList<>();
                for (int i = 0; i < data.get(0).size(); i++) {
                    if (i == 0) {
                        list.add(id);
                    } else {
                        list.add("");
                    }
                }
                data.put(data.size(), list);
                isCheck.clear();
                for (int i = 0; i < data.size(); i++) {
                    isCheck.add(false);
                }
            } else {
                List list = new ArrayList();
                for (int i = 0; i < itemList.size(); i++) {
                    if (i == 0) {
                        list.add(id);
                    } else {
                        list.add("");
                    }
                }
                data.put(0, list);
                isCheck.clear();
                for (int i = 0; i < data.size(); i++) {
                    isCheck.add(false);
                }
            }

            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.linearlayout, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.linearLayout.removeAllViews();
            CheckBox checkBox = new CheckBox(getActivity());

            LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setLayoutParams(checkParams);
            holder.linearLayout.addView(checkBox);
            EditText content = null;
            List<String> strings = data.get(position);
            for (int i = 0; i < strings.size(); i++) {
                DataParamView dataParamView = new DataParamView(getActivity(), null, strings.get(i));
                if (i == 0)
                    dataParamView.setEditEnable(false);
                dataParamView.setBackgroud(R.color.colorWhite);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                dataParamView.setLayoutParams(layoutParams);
                final int finalI = i;
                content = dataParamView.getEditText();
                content.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        data.get(position).set(finalI, s.toString());
                        Log.d("zw", data.get(position).get(finalI));
                    }
                });
                holder.linearLayout.addView(dataParamView);

            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isCheck.set(position, isChecked);
                    if (isChecked) {
                        id.add(data.get(position).get(0));
                    } else {
                        id.remove(data.get(position).get(0));
                    }
                }
            });
            checkBox.setChecked(isCheck.get(position));
        }

        public Map<Integer, List<String>> getData() {
            return this.data;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout linearLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.datalayout);
            }
        }
    }
}
