package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlservice.Bean.TrayHelper;
import cn.ml_tech.mx.mlservice.DAO.LoginLog;
import cn.ml_tech.mx.mlservice.DAO.Tray;
import cn.ml_tech.mx.mlservice.IMlService;

/**
 * Created by ml on 2017/5/24.
 */

public class TrayManagerFragment extends BaseFragment implements View.OnClickListener{

    private EditText etTrayIcId;
    private EditText etTrayDisplayNumber;
    private EditText etContainerDiameter;
    private EditText etTrayInnerDiameter;
    private EditText etTrayExternalDiameter;
    private EditText etMark;
    private Button btnReadTray;
    private Button btnSaveTray;
    private Button btnResetTray;
    private Tray mTray;
    private TrayHelper mTrayHelper;
    private RecyclerView mRecyclerViewTray;
    private List<Tray> trayList=new ArrayList<Tray>();
    private AdapterTray adapterTray;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View initView(LayoutInflater inflater) {
        view= inflater.inflate(R.layout.fragment_traymanager,null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        etTrayIcId = (EditText) view.findViewById(R.id.etTrayIcId);
        etTrayDisplayNumber = (EditText) view.findViewById(R.id.etTrayDisplayNumber);
        etContainerDiameter = (EditText) view.findViewById(R.id.etContainerDiameter);
        etTrayInnerDiameter = (EditText) view.findViewById(R.id.etTrayInnerDiameter);
        etTrayExternalDiameter = (EditText) view.findViewById(R.id.etTrayExternalDiameter);
        etMark = (EditText) view.findViewById(R.id.etMark);
        btnReadTray = (Button) view.findViewById(R.id.btnReadTray);
        btnSaveTray = (Button) view.findViewById(R.id.btnSaveTray);
        btnResetTray = (Button) view.findViewById(R.id.btnResetTray);
        mRecyclerViewTray = (RecyclerView) view.findViewById(R.id.rcvTray);

    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTray=new Tray();
        mTrayHelper=new TrayHelper(mActivity);
        adapterTray = new AdapterTray(mActivity, trayList);
        mRecyclerViewTray.setAdapter(adapterTray);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mActivity);
        mRecyclerViewTray.setLayoutManager(linearLayoutManager);
        LoadTrayData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnReadTray.setOnClickListener(this);
        btnSaveTray.setOnClickListener(this);
        btnResetTray.setOnClickListener(this);
    }
    private String  ReadTrayIcId()
    {
        String icid=mTrayHelper.getTrayIcId();
        this.mTray.setIcId(icid);

        if(TextUtils.isEmpty(icid))return "";
        else return icid;
    }
    private void SaveTray(Tray tray)
    {
        this.mTray.setIcId(etTrayIcId.getText().toString().trim());
        this.mTray.setDisplayId(UtilsHelper.String2Int(etTrayDisplayNumber.getText().toString().trim()));
        this.mTray.setInnerDiameter(UtilsHelper.String2Double(etTrayInnerDiameter.getText().toString().trim()));
        this.mTray.setExternalDiameter(UtilsHelper.String2Double(etTrayExternalDiameter.getText().toString().trim()));
        this.mTray.setDiameter(UtilsHelper.String2Double(etContainerDiameter.getText().toString().trim()));
        this.mTray.setMark(etMark.getText().toString().trim());
        if(mTrayHelper.saveOrUpdateTray(this.mTray)) {
            ResetTray();
            LoadTrayData();
        }
        else Toast.makeText(mActivity, "保存托环信息失败", Toast.LENGTH_SHORT).show();
    }
    private void LoadTrayData()
    {
        try {
            trayList = ((BaseActivity)mActivity).getmService().getTrayList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mActivity.LogDebug("LoadTrayData: "+ String.valueOf(trayList.size()));
        adapterTray.UpdateList(trayList);
    }
    private void ResetTray()
    {
        this.mTrayHelper.resetTray();
        this.etContainerDiameter.setText("");
        this.etMark.setText("");
        this.etTrayDisplayNumber.setText("");
        this.etTrayInnerDiameter.setText("");
        this.etTrayIcId.setText("");
        this.etTrayExternalDiameter.setText("");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnReadTray:
                this.etTrayIcId.setText( ReadTrayIcId());
                break;
            case R.id.btnSaveTray:
                SaveTray(mTray);
                break;
            case R.id.btnResetTray:
                ResetTray();
                break;
            default:
                break;
        }
    }
    class AdapterTray extends RecyclerView.Adapter<AdapterTray.ViewHolderTray>
    {
        public void UpdateList(List<Tray>list)

        {
            this.trayList=list;
            notifyDataSetChanged();
        }
        List<Tray> trayList;
        Context mcontext;
        AdapterTray(Context context,List<Tray>list)
        {
            this.mcontext=context;
            this.trayList=list;
        }
        @Override
        public ViewHolderTray onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(this.mcontext).inflate(R.layout.recyletray,viewGroup,false);
            ViewHolderTray viewHolderTray=new ViewHolderTray(view);
            return viewHolderTray;
        }

        @Override
        public void onBindViewHolder(ViewHolderTray viewHolder, int i) {
            viewHolder.txtTrayIcId.setText(trayList.get(i).getIcId());
            viewHolder.txtTrayDisplayNumber.setText(String.valueOf(trayList.get(i).getDisplayId()));
            viewHolder.txtContainerDiameter.setText(String.valueOf(trayList.get(i).getDiameter()));
            viewHolder.txtTrayInnerDiameter.setText(String.valueOf(trayList.get(i).getInnerDiameter()));
            viewHolder.txtTrayExternalDiameter.setText(String.valueOf(trayList.get(i).getExternalDiameter()));
            viewHolder.txtMark.setText(trayList.get(i).getMark());
            viewHolder.txtEdit.setText("修改");
            viewHolder.txtDel.setText("删除");
        }

        @Override
        public int getItemCount() {
            return null==this.trayList?0:this.trayList.size();
        }
        class ViewHolderTray extends RecyclerView.ViewHolder
        {
            TextView txtTrayIcId;
            TextView txtTrayDisplayNumber;
            TextView txtContainerDiameter;
            TextView txtTrayInnerDiameter;
            TextView txtTrayExternalDiameter;
            TextView txtMark;
            TextView txtEdit;
            TextView txtDel;

            public ViewHolderTray(View itemView) {
                super(itemView);
                txtTrayIcId= (TextView) itemView.findViewById(R.id.txtTrayIcId);
                txtTrayDisplayNumber= (TextView) itemView.findViewById(R.id.txtTrayDisplayNumber);
                txtContainerDiameter= (TextView) itemView.findViewById(R.id.txtContainerDiameter);
                txtTrayInnerDiameter= (TextView) itemView.findViewById(R.id.txtTrayInnerDiameter);
                txtTrayExternalDiameter= (TextView) itemView.findViewById(R.id.txtTrayExternalDiameter);
                txtMark= (TextView) itemView.findViewById(R.id.txtMark);
                txtEdit= (TextView) itemView.findViewById(R.id.txtEdit);
                txtDel= (TextView) itemView.findViewById(R.id.txtDel);
            }
        }
    }

}
