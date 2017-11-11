package cn.ml_tech.mx.mlproj.ypfragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.activity.YpjcActivity;
import cn.ml_tech.mx.mlproj.adapter.DrugAdapter;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlservice.DAO.DrugControls;
import cn.ml_tech.mx.mlservice.IMlService;

import static cn.ml_tech.mx.mlproj.R.id.me_name;

public class YpkFragment extends BaseFragment {
    private List<DrugControls> drugList = new ArrayList<>();
    private DrugControls drugControls = null;
    private DrugAdapter adapter;
    private YpjcActivity ypjcActivity;
    private View view;
    private EditText meName;
    private Button query;
    private EditText mePhonetic;
    private Button reseting;
    private EditText meEnname;
    private Button addphonetic;
    private LinearLayout wholelayout;
    private ImageButton ibPre;
    private EditText etPage;
    private ImageButton ibNext;
    private TextView tvCurrentPage;
    private TextView tvAllPage;
    private ImageButton ibSearch;
    private Button btnypxNext;
    private static final int RSUCESS = 123;
    private ProgressDialog progressDialog;
    private int size;

    public IMlService getmlService() {
        return mlService;
    }

    private int cuurentPage = 1, lastPage;


    RecyclerView recyclerView;
    private String mDrugName = "", mPinyin = "", mEnName = "";
    private DrugAdapter.OperateToData operateToData = new DrugAdapter.OperateToData() {
        @Override
        public boolean delete(long id) {
            try {
                mlService.deleteDrugInfoById((int) id);
                mlService.deleteDrugParamById((int) id);
                adapter.deleteDataById((int) id);
                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "删除成功 原因:" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
            return false;
        }

        @Override
        public void operateToPre(boolean isNext, DrugControls drugControl) {
            btnypxNext.setEnabled(isNext);
            ypjcActivity.drugControl = drugControl;
            ypjcActivity.druginfo_id = (int) drugControl.getId();

        }

        @Override
        public void update(DrugControls drugControls) {
            ypjcActivity.druginfo_id = (int) drugControls.getId();
            ypjcActivity.drugControl = drugControls;
            ypjcActivity.moveToAddDrug();
        }
    };

    private void initDrugs() {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(mActivity);
        progressDialog.setTitle("数据加载中....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    drugList = mlService.queryDrugControlByInfo("", "", "", 1);
                    size = mlService.getNumByTableName("druginfo");
                    handler.sendEmptyMessage(RSUCESS);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    @Override
    protected void handleMsg(Message message) {
        super.handleMsg(message);
        switch (message.what) {

            case RSUCESS:

                setDataToView(drugList, true, size);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ypjcActivity = (YpjcActivity) getActivity();
    }

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_ypk, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        meName = (EditText) view.findViewById(R.id.me_name);
        query = (Button) view.findViewById(R.id.query);
        mePhonetic = (EditText) view.findViewById(R.id.me_phonetic);
        reseting = (Button) view.findViewById(R.id.reseting);
        meEnname = (EditText) view.findViewById(R.id.me_enname);
        addphonetic = (Button) view.findViewById(R.id.addphonetic);
        wholelayout = (LinearLayout) view.findViewById(R.id.wholelayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        ibPre = (ImageButton) view.findViewById(R.id.ibPre);
        etPage = (EditText) view.findViewById(R.id.etPage);
        ibNext = (ImageButton) view.findViewById(R.id.ibNext);
        tvCurrentPage = (TextView) view.findViewById(R.id.tvCurrentPage);
        tvAllPage = (TextView) view.findViewById(R.id.tvAllPage);
        ibSearch = (ImageButton) view.findViewById(R.id.ibSearch);
        btnypxNext = (Button) view.findViewById(R.id.btnypxNext);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        view.findViewById(R.id.ibPre).setOnClickListener((View.OnClickListener) getActivity());
        view.findViewById(R.id.ibNext).setOnClickListener((View.OnClickListener) getActivity());
        view.findViewById(R.id.ibSearch).setOnClickListener((View.OnClickListener) getActivity());
        view.findViewById(R.id.btnypxNext).setEnabled(false);
        view.findViewById(R.id.query).setOnClickListener((View.OnClickListener) getActivity());
        view.findViewById(R.id.reseting).setOnClickListener((View.OnClickListener) getActivity());
        view.findViewById(R.id.btnypxNext).setOnClickListener((View.OnClickListener) getActivity());
        view.findViewById(R.id.addphonetic).setOnClickListener((View.OnClickListener) getActivity());
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDrugs();
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if (adapter != null && progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });
    }

    public void setPreDataToView() throws RemoteException {
        if (cuurentPage == 1) {
            Toast.makeText(getActivity(), "已经是第一页了", Toast.LENGTH_SHORT).show();
            return;
        }
        cuurentPage--;
        adapter.setDatasToView(mlService.queryDrugControlByInfo(mDrugName, mPinyin, mEnName, cuurentPage));
        ((TextView) getActivity().findViewById(R.id.tvCurrentPage)).setText(cuurentPage + "/");
    }

    public void setNexTDataToView() throws RemoteException {
        if (cuurentPage == lastPage) {
            Toast.makeText(getActivity(), "已经是最后一页了", Toast.LENGTH_SHORT).show();
            return;
        }
        cuurentPage++;
        adapter.setDatasToView(mlService.queryDrugControlByInfo(mDrugName, mPinyin, mEnName, cuurentPage));
        ((TextView) getActivity().findViewById(R.id.tvCurrentPage)).setText(cuurentPage + "/");
    }

    public void setSearchDataToView() throws RemoteException {
        String content = ((EditText) getActivity().findViewById(R.id.etPage)).getEditableText().toString();
        if (content.trim().equals("")) {
            Toast.makeText(getActivity(), "请输入页码再进行查询", Toast.LENGTH_SHORT).show();
            return;
        }
        cuurentPage = Integer.parseInt(content);
        if (cuurentPage > lastPage) {
            Toast.makeText(getActivity(), "已超过最大页", Toast.LENGTH_SHORT).show();
            return;
        }
        adapter.setDatasToView(mlService.queryDrugControlByInfo(mDrugName, mPinyin, mEnName, cuurentPage));
        ((TextView) getActivity().findViewById(R.id.tvCurrentPage)).setText(cuurentPage + "/");

    }

    public void resetting() {

        ((EditText) getActivity().findViewById(R.id.me_name)).setText("");

        ((EditText) getActivity().findViewById(R.id.me_phonetic)).setText("");

        ((EditText) getActivity().findViewById(R.id.me_enname)).setText("");

    }

    public void setDataToView(List<DrugControls> drugList, boolean isReseting, int size) {
        lastPage = ((int) Math.floor(size / 20)) + 1;
        ((TextView) view.findViewById(R.id.tvAllPage)).setText(lastPage + "");
        if (isReseting) {
            cuurentPage = 1;
            ((TextView) view.findViewById(R.id.tvCurrentPage)).setText(cuurentPage + "/");
            for (int i = 20; i < drugList.size(); i++) {
                drugList.remove(i);
                i--;
            }
        }
        adapter = new DrugAdapter(drugList, getActivity(), operateToData);
        recyclerView.setAdapter(adapter);
        Log.d("zw", "set data to view");
    }


    public void setDataByName(String name) {
        meName.setText(name);
        Log.d("zw", " name " + name);
        try {
            List<DrugControls> drugControlses = mlService.queryDrugControlByInfo(name, "", "", 1);
            size = mlService.getNumByTableName("druginfo");

            setDataToView(drugControlses, true, size);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public String getDrugName() {
        return ((EditText) getActivity().findViewById(me_name)).getEditableText().toString();
    }

    public String getPinyin() {
        return ((EditText) getActivity().findViewById(R.id.me_phonetic)).getEditableText().toString();
    }

    public String getEnName() {
        return ((EditText) getActivity().findViewById(R.id.me_enname)).getEditableText().toString();
    }


}
