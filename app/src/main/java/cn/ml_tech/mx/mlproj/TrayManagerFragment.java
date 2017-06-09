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
        adapterTray.setOnItemClickListener(new AdapterTray.OnItemClickListener() {
            @Override
            public void onSubViewClick(View view, int position) {
                switch (view.getId())
                {
                    case R.id.txtEdit:
                        EditTray(adapterTray.trayList.get(position));
                        break;
                    case R.id.txtDel:
                        if(DelTray(adapterTray.trayList.get(position)))
                        {
                            adapterTray.trayList.remove(position);
                            adapterTray.notifyItemRemoved(position);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
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
        if(!CheckInfoComplete())
        {
            Toast.makeText(mActivity, "托环信息不完整", Toast.LENGTH_SHORT).show();
            return ;
        }
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
    private boolean CheckInfoComplete()
    {
        String icid=etTrayIcId.getText().toString().trim();
        String dia=etContainerDiameter.getText().toString().trim();
        String inndia=etTrayInnerDiameter.getText().toString().trim();
        String extdia=etTrayExternalDiameter.getText().toString().trim();
        String disid=etTrayDisplayNumber.getText().toString().trim();
        String mark=etMark.getText().toString().trim();
        if(TextUtils.isEmpty(icid)||TextUtils.isEmpty(dia)||TextUtils.isEmpty(inndia)
                ||TextUtils.isEmpty(extdia)||TextUtils.isEmpty(disid)||TextUtils.isEmpty(mark))
            return false;
        else return true;
    }
    private boolean DelTray(Tray tray)
    {
        boolean flag=false;
        flag= mTrayHelper.DelTray(tray);
        return flag;
    }
    private void EditTray(Tray tray)
    {

        mTrayHelper.EditTray(tray);
        etContainerDiameter.setText(String.valueOf(tray.getDiameter()));
        etTrayInnerDiameter.setText(String.valueOf(tray.getInnerDiameter()));
        etTrayExternalDiameter.setText(String.valueOf(tray.getExternalDiameter()));
        etTrayIcId.setText(tray.getIcId());
        etTrayDisplayNumber.setText(String.valueOf(tray.getDisplayId()));
        etMark.setText(tray.getMark());
    }
    private void LoadTrayData()
    {
        try {
            trayList = ((BaseActivity)mActivity).getmService().getTrayList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

    static class AdapterTray extends RecyclerView.Adapter<AdapterTray.ViewHolderTray>implements View.OnClickListener
    {
        public interface OnItemClickListener{
            void onSubViewClick(View view,int position);
        }
        public void setOnItemClickListener(OnItemClickListener listener){this.mOnItemClickListener=listener;}
        public void UpdateList(List<Tray>list)
        {
            this.trayList=list;
            notifyDataSetChanged();
        }
        List<Tray> trayList;
        Context mcontext;
        private OnItemClickListener mOnItemClickListener=null;
        AdapterTray(Context context,List<Tray>list)
        {
            this.mcontext=context;
            this.trayList=list;
        }
        @Override
        public ViewHolderTray onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(this.mcontext).inflate(R.layout.recyletray,viewGroup,false);
            ViewHolderTray viewHolderTray=new ViewHolderTray(view);
            viewHolderTray.txtEdit.setOnClickListener(this);
            viewHolderTray.txtDel.setOnClickListener(this);
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
            viewHolder.txtEdit.setTag(i);
            viewHolder.txtDel.setTag(i);
        }

        @Override
        public int getItemCount() {
            return null==this.trayList?0:this.trayList.size();
        }

        @Override
        public void onClick(View v) {
            if(null!=mOnItemClickListener)
            {
                mOnItemClickListener.onSubViewClick(v, (Integer) v.getTag());
            }
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
