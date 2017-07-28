package cn.ml_tech.mx.mlproj.SettingFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.Adapter.StringAdapter;
import cn.ml_tech.mx.mlproj.AmiApp;
import cn.ml_tech.mx.mlproj.BaseFragment;
import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.XtwhActivity;
import cn.ml_tech.mx.mlproj.XtwhFragment;
import cn.ml_tech.mx.mlservice.DAO.P_Source;
import cn.ml_tech.mx.mlservice.DAO.User;
import cn.ml_tech.mx.mlservice.DAO.UserType;

/**
 * Created by ml on 2017/5/18.
 */
public class UserManagerFragment extends BaseFragment implements View.OnClickListener {
    private static final int OPERATESUCESS = 11;
    private static final int OPERATEFAILURE = 22;
    private static final int INITDATASUCESS = 33;
    private static final int INITDATAFAILURE = 44;
    private RecyleUserAdapter recyleUserAdapter;
    private LinearLayoutManager layoutmanager;
    private List<User> listUser;
    private EditText etNickName;
    private EditText etUserName;
    private EditText etUserPwd;
    private EditText etUserPwd2;
    private CheckBox chbEnable;
    private Spinner comUserType;
    private Button btSave;
    private Button btResver;
    private Button btAddType;
    private XtwhFragment xtwhFragment;
    private RecyclerView rcvUser;
    private User mUser;
    private ProgressDialog progressDialog;
    private List<UserType> userTypes;
    private List<String> typeName;
    private StringAdapter typeNameAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null) progressDialog.dismiss();
            switch (msg.what) {
                case OPERATEFAILURE:
                    showToast("操作失败");
                    break;
                case OPERATESUCESS:
                    mUser = null;
                    clearText();
                    initUserTypeInfo();
                    initUserInfo();
                    break;
                case INITDATASUCESS:
                    if (getPermissionById(20, 2)) {
                        initUserTypeInfo();
                        initUserInfo();
                    } else {
                        if (recyleUserAdapter == null) {
                            recyleUserAdapter = new RecyleUserAdapter(listUser, getActivity(), itmeClick);
                            rcvUser.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rcvUser.setAdapter(recyleUserAdapter);
                            recyleUserAdapter.clearAll();
                        } else {
                            recyleUserAdapter.clearAll();


                        }
                    }
                    break;
                case INITDATAFAILURE:
                    showToast("加载失败");
                    break;

            }
        }
    };

    private void clearText() {
        etNickName.setText("");
        etUserName.setText("");
        etUserPwd.setText("");
        etUserPwd2.setText("");
    }

    private void initUserTypeInfo() {
        typeName.clear();
        for (UserType userType :
                userTypes) {
            typeName.add(userType.getTypeName());
        }
        typeNameAdapter = new StringAdapter(typeName, getActivity());
        comUserType.setAdapter(typeNameAdapter);
    }

    private void initUserInfo() {
        if (recyleUserAdapter == null) {
            recyleUserAdapter = new RecyleUserAdapter(listUser, getActivity(), itmeClick);
            rcvUser.setAdapter(recyleUserAdapter);
            layoutmanager = new LinearLayoutManager(mActivity);
            rcvUser.setLayoutManager(layoutmanager);
        } else {
            recyleUserAdapter.setDataToView(listUser);

        }

    }

    private OnItmeClick itmeClick = new OnItmeClick() {
        @Override
        public void update(User user) {
            if (getPermissionById(20, 4)) {
                if (getPermissionById(20, 9)) {
                    if (user.getId() != ((AmiApp) getActivity().getApplication()).getUserid()) {
                        showToast("仅能操作自身张账号");
                        return;
                    }
                }
                mUser = user;
                setUserInfoToView(user);
            } else {
                showRefuseTip();
            }
        }

        @Override
        public void delete(final long id) {
            if (getPermissionById(20, 5)) {
                if (getPermissionById(20, 9)) {
                    if (id != ((AmiApp) getActivity().getApplication()).getUserid()) {
                        showToast("仅能操作自身张账号");
                        return;
                    }
                }
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(getActivity());
                }
                progressDialog.setTitle("操作中...");
                progressDialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Log.d("zw", "delete data");
                            mlService.deleteUserById(id);
                            listUser = mlService.getUserList();
                            handler.sendEmptyMessage(OPERATESUCESS);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(OPERATEFAILURE);

                        }

                    }
                }.start();
            } else {
                showRefuseTip();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_usermanager, null);
        initFindViewById(view);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btSave.setOnClickListener(this);
        btAddType.setOnClickListener(this
        );

    }

    @Override
    public void initFindViewById(View view) {
        etNickName = (EditText) view.findViewById(R.id.etNickName);
        etUserName = (EditText) view.findViewById(R.id.etUserName);
        etUserPwd = (EditText) view.findViewById(R.id.etUserPwd);
        etUserPwd2 = (EditText) view.findViewById(R.id.etUserPwd2);
        chbEnable = (CheckBox) view.findViewById(R.id.chbEnable);
        comUserType = (Spinner) view.findViewById(R.id.comUserType);
        btSave = (Button) view.findViewById(R.id.btSave);
        btResver = (Button) view.findViewById(R.id.btResver);
        rcvUser = (RecyclerView) view.findViewById(R.id.rcvUser);
        btAddType = (Button) view.findViewById(R.id.btAddType);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) initData(null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mlService = mActivity.getmService();
        xtwhFragment = ((XtwhActivity) getActivity()).getXtwhFragment();
        typeName = new ArrayList<>();
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("加载中...");
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                String url = "";
                try {
                    for (P_Source p_source : amiApp.getP_sources()
                            ) {
                        if (p_source.getId() == 27) {
                            url = p_source.getUrl();
                            break;
                        }
                    }
                    setPermission(mlService.getPermissonByUrl(url, false));
                    if (mlService != null) {
                        listUser = mlService.getUserList();
                        userTypes = mlService.getAllUserType();
                        handler.sendEmptyMessage(INITDATASUCESS);

                    } else {
                        handler.sendEmptyMessage(INITDATAFAILURE);

                    }


                } catch (RemoteException e) {
                    handler.sendEmptyMessage(INITDATAFAILURE);
                    e.printStackTrace();
                }
            }
        }.start();

    }


    public void setUserInfoToView(User user) {
        etNickName.setText(user.getUserId());
        etUserName.setText(user.getUserName());
        etUserPwd.setText(user.getUserPassword());
        etUserPwd2.setText(user.getUserPassword());
        chbEnable.setChecked(user.getIsEnable() == 1 ? true : false);
        for (int i = 0; i < userTypes.size(); i++) {
            UserType userType = userTypes.get(i);
            if (userType.getTypeId() == mUser.getUsertype_id())
                comUserType.setSelection(i);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSave:
                if (mUser == null && getPermissionById(20, 3)) {
                    mUser = new User();
                } else if (mUser == null && !getPermissionById(20, 3)) {
                    showRefuseTip();
                    return;
                }
                mUser.setIsEnable(chbEnable.isChecked() ? 1 : 0);
                if (!TextUtils.isEmpty(etNickName.getEditableText().toString())) {
                    mUser.setUserId(etNickName.getEditableText().toString());
                } else {
                    showToast("昵称尚未输入完整");
                    return;
                }
                if (!TextUtils.isEmpty(etUserName.getEditableText().toString())) {
                    mUser.setUserName(etUserName.getEditableText().toString());
                } else {
                    showToast("用户名尚未输入完整");
                    return;
                }
                if (!TextUtils.isEmpty(etUserPwd.getEditableText().toString()) && (etUserPwd.getEditableText().toString().trim()
                        .equals(etUserPwd2.getEditableText().toString().trim()))) {
                    mUser.setUserPassword(etUserPwd.getEditableText().toString().trim());
                } else {
                    showToast("密码为输入完整或两次密码输入不一致");
                    return;
                }
                for (UserType userType :
                        userTypes) {
                    if (userType.getTypeName().trim().equals(comUserType.getSelectedItem().toString().trim()))
                        mUser.setUsertype_id(userType.getTypeId());
                }
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("操作中...");
                    progressDialog.show();
                } else {
                    progressDialog.show();
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            mlService.updateUser(mUser);
                            Thread.sleep(1000);
                            listUser = mlService.getUserList();
                            handler.sendEmptyMessage(OPERATESUCESS);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(OPERATEFAILURE);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
                break;
            case R.id.btAddType:
                xtwhFragment.moveToAddType();
                break;
            case R.id.btResver:
                clearText();
                break;
        }

    }


    class RecyleUserAdapter extends RecyclerView.Adapter<RecyleUserAdapter.ViewHolder> implements View.OnClickListener {
        private List<User> listUser;
        private Context context;
        private OnItmeClick itmeClick;


        public RecyleUserAdapter(List<User> listUser, Context context, OnItmeClick itmeClick) {
            this.listUser = listUser;
            this.context = context;
            this.itmeClick = itmeClick;
        }

        public void setDataToView(List<User> users) {
            listUser.clear();
            listUser.addAll(users);
            notifyDataSetChanged();
        }

        public void clearAll() {
            this.listUser.clear();
            notifyDataSetChanged();
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
            holder.txtUserDel.setTag(listUser.get(position).getId());
            holder.txtUserEdit.setTag(listUser.get(position));
            holder.txtUserEdit.setOnClickListener(this);
            holder.txtUserDel.setOnClickListener(this);
            if (userTypes != null) {
                for (UserType userType :
                        userTypes) {
                    if (userType.getTypeId() == listUser.get(position).getUsertype_id()) {
                        holder.txtUserType.setText(userType.getTypeName());

                    }
                }
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txtEdit:
                    User user = (User) v.getTag();
                    itmeClick.update(user);
                    break;
                case R.id.txtDel:
                    if (getPermissionById(20, 5)) {
                        long id = (long) v.getTag();
                        itmeClick.delete(id);
                    } else {
                        showRefuseTip();

                    }
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return listUser.size();
        }

    }

    interface OnItmeClick {
        void update(User user);

        void delete(long id);
    }

}
