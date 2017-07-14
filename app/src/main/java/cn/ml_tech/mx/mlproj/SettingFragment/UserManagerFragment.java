package cn.ml_tech.mx.mlproj.SettingFragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.BaseActivity;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.Bean.User;

/**
 * Created by ml on 2017/5/18.
 */

public class UserManagerFragment extends Fragment {
    private final String TAG = "UserManagerFragment";
    private Activity mActivity;
    private RecyleUserAdapter recyleUserAdapter;
    private RecyclerView recylerView;
    private LinearLayoutManager layoutmanager;
    private List<User> listUser;
    private LinearLayout llUserEdit;
    private TextView nickname;
    private EditText etNickName;
    private EditText etUserName;
    private EditText etUserPwd;
    private EditText etUserPwd2;
    private CheckBox chbEnable;
    private Spinner comUserType;
    private Button btSave;
    private Button btResver;
    private User mUser;

    public UserManagerFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
        mActivity = getActivity();
        recylerView = (RecyclerView) (mActivity.findViewById(R.id.rcvUser));
        List<String> listNickName = new ArrayList<String>();
        List<String> listUserName = new ArrayList<String>();
        listUser = new ArrayList<User>();
        listUser.clear();
        try {
            listUser = ((BaseActivity) mActivity).mService.getUserList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        recyleUserAdapter = new RecyleUserAdapter(mActivity, listUser, new RecyleUserAdapter.Operate() {
            @Override
            public void update(int pos) {
                mUser = listUser.get(pos);
                nickname.setText(mUser.getUserId());
                etUserName.setText(mUser.getUserName());
                etUserPwd.setText(mUser.getUserPassword());
                etUserPwd2.setText(mUser.getUserPassword());
                chbEnable.setChecked(mUser.isEnable());
            }

            @Override
            public void delete(int pos) {

            }
        });
        recylerView.setAdapter(recyleUserAdapter);
        layoutmanager = new LinearLayoutManager(mActivity);
        recylerView.setLayoutManager(layoutmanager);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        initView(view);
        return inflater.inflate(R.layout.fragment_usermanager, container, false);
    }

    private void initView(View view) {
        llUserEdit = (LinearLayout) view.findViewById(R.id.llUserEdit);
        nickname = (TextView) view.findViewById(R.id.nickname);
        etNickName = (EditText) view.findViewById(R.id.etNickName);
        etUserName = (EditText) view.findViewById(R.id.etUserName);
        etUserPwd = (EditText) view.findViewById(R.id.etUserPwd);
        etUserPwd2 = (EditText) view.findViewById(R.id.etUserPwd2);
        chbEnable = (CheckBox) view.findViewById(R.id.chbEnable);
        comUserType = (Spinner) view.findViewById(R.id.comUserType);
        btSave = (Button) view.findViewById(R.id.btSave);
        btResver = (Button) view.findViewById(R.id.btResver);
    }

    static class RecyleUserAdapter extends RecyclerView.Adapter<RecyleUserAdapter.ViewHolder> implements View.OnClickListener {
        private List<User> listUser;
        private Context context;
        private AdapterView.OnItemClickListener mlistener;
        private Operate operate;

        public RecyleUserAdapter(Context context, List<User> list, Operate operate) {
            this.context = context;
            this.listUser = list;
            this.operate = operate;
        }

        public void setMlistener(AdapterView.OnItemClickListener mlistener) {
            this.mlistener = mlistener;
        }

        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            switch (v.getId()) {
                case R.id.txtDel:
                    operate.delete(pos);
                    break;
                case R.id.txtEdit:
                    operate.update(pos);
                    break;
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBoxSelect;
            TextView txtNickName;
            TextView txtUserName;
            TextView txtUserType;
            TextView txtUserEnable;
            TextView txtUserEdit;
            TextView txtUserDel;

            public ViewHolder(View itemView) {
                super(itemView);
                checkBoxSelect = (CheckBox) itemView.findViewById(R.id.chkSelect);
                txtNickName = (TextView) itemView.findViewById(R.id.txtNickName);
                txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
                txtUserType = (TextView) itemView.findViewById(R.id.txtUserType);
                txtUserEnable = (TextView) itemView.findViewById(R.id.txtUserEnable);
                txtUserEdit = (TextView) itemView.findViewById(R.id.txtEdit);
                txtUserDel = (TextView) itemView.findViewById(R.id.txtDel);
            }
        }

        @Override
        public RecyleUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(this.context).inflate(R.layout.recyleuser, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyleUserAdapter.ViewHolder holder, int position) {
            holder.txtNickName.setText(listUser.get(position).getUserId());
            holder.txtUserName.setText(listUser.get(position).getUserName());
            holder.txtUserType.setText(listUser.get(position).getUserType().getTypeName());
            holder.txtUserType.setText(listUser.get(position).getUserType().getTypeName());
            holder.txtUserDel.setText(Html.fromHtml("<u>" + "删除" + "</u>"));
            holder.txtUserEdit.setText(Html.fromHtml("<u>" + "修改" + "</u>"));
            holder.txtUserEnable.setText(listUser.get(position).isEnable() ? "可用" : "禁用");
            holder.txtUserEdit.setTag(position);
            holder.txtUserDel.setTag(position);
            holder.txtUserDel.setOnClickListener(this);
            holder.txtUserEdit.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return listUser.size();
        }

        private interface Operate {
            void update(int pos);

            void delete(int pos);
        }


    }

}
