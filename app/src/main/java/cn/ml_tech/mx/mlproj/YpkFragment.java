package cn.ml_tech.mx.mlproj;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import cn.ml_tech.mx.mlproj.Adapter.DrugAdapter;
import cn.ml_tech.mx.mlservice.DrugControls;
import cn.ml_tech.mx.mlservice.IMlService;

import static cn.ml_tech.mx.mlproj.R.id.me_name;

public class YpkFragment extends BaseFragment{


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


    public IMlService getmService() {
        return mService;
    }

    private int cuurentPage = 1, lastPage;

    public void setmService(IMlService mService) {
        this.mService = mService;
    }

    RecyclerView recyclerView;
    private IMlService mService;
    private String mDrugName = "", mPinyin = "", mEnName = "";
    private DrugAdapter.OperateToData operateToData = new DrugAdapter.OperateToData() {
        @Override
        public boolean delete(long id) {
            if (getPermissionById(9, 5)) {
                try {
                    getmService().deleteDrugInfoById((int) id);
                    getmService().deleteDrugParamById((int) id);
                    adapter.deleteDataById((int) id);
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "删除成功 原因:" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                showRefuseTip();
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
            if (getPermissionById(9, 4)) {
                ypjcActivity.druginfo_id = (int) drugControls.getId();
                ypjcActivity.drugControl = drugControls;
                ypjcActivity.moveToAddDrug();
            } else {
                showRefuseTip();
            }
        }
    };

    private void initDrugs() {
        try {
            drugList = mService.queryDrugControl();
            setDataToView(drugList, true);
        } catch (RemoteException e) {
            e.printStackTrace();
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
        setPermission(((YpjcActivity) getActivity()).permission);
        initDrugs();
        query.setVisibility(getPermissionById(7, 1) == true ? View.VISIBLE : View.INVISIBLE);
        query.setEnabled(getPermissionById(7, 8));
        addphonetic.setVisibility(getPermissionById(8, 1) == true ? View.VISIBLE : View.INVISIBLE);
        addphonetic.setEnabled(getPermissionById(8, 8));
        btnypxNext.setVisibility(getPermissionById(10, 1) == true ? View.VISIBLE : View.INVISIBLE);

    }

    public void setPreDataToView() throws RemoteException {
        if (cuurentPage == 1) {
            Toast.makeText(getActivity(), "已经是第一页了", Toast.LENGTH_SHORT).show();
            return;
        }
        cuurentPage--;
        adapter.setDatasToView(mService.queryDrugControlByInfo(mDrugName, mPinyin, mEnName, cuurentPage));
        ((TextView) getActivity().findViewById(R.id.tvCurrentPage)).setText(cuurentPage + "/");
    }

    public void setNexTDataToView() throws RemoteException {
        if (cuurentPage == lastPage) {
            Toast.makeText(getActivity(), "已经是最后一页了", Toast.LENGTH_SHORT).show();
            return;
        }
        cuurentPage++;
        adapter.setDatasToView(mService.queryDrugControlByInfo(mDrugName, mPinyin, mEnName, cuurentPage));
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
        adapter.setDatasToView(mService.queryDrugControlByInfo(mDrugName, mPinyin, mEnName, cuurentPage));
        ((TextView) getActivity().findViewById(R.id.tvCurrentPage)).setText(cuurentPage + "/");

    }

    public void resetting() {

        ((EditText) getActivity().findViewById(R.id.me_name)).setText("");

        ((EditText) getActivity().findViewById(R.id.me_phonetic)).setText("");

        ((EditText) getActivity().findViewById(R.id.me_enname)).setText("");

    }

    public void setDataToView(List<DrugControls> drugList, boolean isReseting) {
        if (getPermissionById(9, 1)) {
            lastPage = ((int) Math.floor(drugList.size() / 20)) + 1;
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
