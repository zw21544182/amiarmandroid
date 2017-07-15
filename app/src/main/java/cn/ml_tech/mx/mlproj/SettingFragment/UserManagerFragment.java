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
import android.text.TextUtils;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.Adapter.StringAdapter;
import cn.ml_tech.mx.mlproj.BaseActivity;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.User;
import cn.ml_tech.mx.mlservice.DAO.UserType;

/**
 * Created by ml on 2017/5/18.
 */

public class UserManagerFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "UserManagerFragment";
    private static Activity mActivity;
    private RecyleUserAdapter recyleUserAdapter;
    private RecyclerView recylerView;
    private LinearLayoutManager layoutmanager;
    private List<User> listUser;
    private static List<UserType> userTypes;
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
    private int postion;

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
            userTypes = ((BaseActivity) mActivity).mService.getAllUserType();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        setDataToView();

    }

    private void clearText() {
        etNickName.setText("");
        etUserName.setText("");
        etUserPwd.setText("");
        etUserPwd2.setText("");
    }

    private void setDataToView() {
        List<String> strings = new ArrayList<>();
        for (UserType userType :
                userTypes) {
            strings.add(userType.getTypeName());
        }
        comUserType.setAdapter(new StringAdapter(strings, getActivity()));
        recyleUserAdapter = new RecyleUserAdapter(mActivity, listUser, new RecyleUserAdapter.Operate() {
            @Override
            public void update(int pos) {
                mUser = listUser.get(pos);
                etUserName.setText(mUser.getUserName());
                etUserPwd.setText(mUser.getUserPassword());
                etUserPwd2.setText(mUser.getUserPassword());
                chbEnable.setChecked(mUser.getIsEnable() == 1 ? true : false);
                etNickName.setText(mUser.getUserId().trim());
                comUserType.setSelection((int) (mUser.getUsertype_id() - 1));
                for (int i = 0; i < userTypes.size(); i++) {
                    UserType userType = userTypes.get(i);
                    if (userType.getTypeId() == mUser.getUsertype_id()) {
                        comUserType.setSelection(i);
                    }
                }
            }

            @Override
            public void delete(int pos) {
                try {
                    ((BaseActivity) mActivity).mService.deleteUserById(pos);
                    listUser = ((BaseActivity) mActivity).mService.getUserList();
                    recyleUserAdapter.setDataToView(listUser);
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();

                } catch (RemoteException e) {
                    Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();


                }
            }
        });
        recylerView.setAdapter(recyleUserAdapter);
        layoutmanager = new LinearLayoutManager(mActivity);
        recylerView.setLayoutManager(layoutmanager);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usermanager, container, false);
        initView(view);
        event();
        return view;
    }

    private void event() {
        btSave.setOnClickListener(this);
        btResver.setOnClickListener(this);
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
        comUserType.setSelection(0);
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSave:
                if (!TextUtils.isEmpty(etNickName.getEditableText().toString().trim())) {
                    mUser.setUserId(etNickName.getEditableText().toString().trim());
                }
                if (!TextUtils.isEmpty(etUserName.getEditableText().toString().trim())) {
                    mUser.setUserName(etUserName.getEditableText().toString().trim());
                }
                if (!etUserPwd.getEditableText().toString().trim().equals(etUserPwd2.getEditableText().toString().trim())) {
                    Toast.makeText(getActivity(), "两次密码输入不一致,保存密码无效", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(etUserName.getEditableText().toString().trim())) {
                    Log.d("zw", "excute update passsword");
                    mUser.setUserPassword(etUserPwd.getEditableText().toString().trim());
                    Log.d("zw", "password:" + etUserPwd.getEditableText().toString().trim());
                }
                mUser.setIsEnable(chbEnable.isChecked() == true ? 1 : 0);
                mUser.setDeparecate(chbEnable.isChecked());
                String currenttype = comUserType.getSelectedItem().toString();
                for (UserType userType :
                        userTypes) {
                    if (userType.getTypeName().equals(currenttype)) {

                        mUser.setUsertype_id(userType.getTypeId());
                    }
                }

                try {
                    ((BaseActivity) mActivity).mService.updateUser(mUser);
                    listUser = ((BaseActivity) mActivity).mService.getUserList();
                    recyleUserAdapter.setDataToView(listUser);
                    Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case R.id.btResver:
                break;
        }
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

        public void setDataToView(List<User> usertList) {
            listUser.clear();
            listUser.addAll(usertList);
            notifyDataSetChanged();
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
            holder.txtUserDel.setText(Html.fromHtml("<u>" + "删除" + "</u>"));
            holder.txtUserEdit.setText(Html.fromHtml("<u>" + "修改" + "</u>"));
            holder.txtUserEnable.setText(listUser.get(position).getIsEnable() == 1 ? "可用" : "禁用");
            holder.txtUserEdit.setTag(position);
            holder.txtUserDel.setTag(position);
            holder.txtUserDel.setOnClickListener(this);
            holder.txtUserEdit.setOnClickListener(this);
            for (UserType userType :
                    userTypes) {
                if (userType.getTypeId() == listUser.get(position).getUsertype_id()) {
                    holder.txtUserType.setText(userType.getTypeName());
                }

            }
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
