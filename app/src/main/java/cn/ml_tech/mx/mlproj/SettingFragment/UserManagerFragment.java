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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
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
        recyleUserAdapter = new RecyleUserAdapter(mActivity, listUser);
        recylerView.setAdapter(recyleUserAdapter);
        layoutmanager = new LinearLayoutManager(mActivity);
        recylerView.setLayoutManager(layoutmanager);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_usermanager, container, false);
        // return super.onCreateView(inflater, container, savedInstanceState);
    }

    class RecyleUserAdapter extends RecyclerView.Adapter<RecyleUserAdapter.ViewHolder> {
        private List<User> listUser;
        private Context context;
        private AdapterView.OnItemClickListener mlistener;

        public RecyleUserAdapter(Context context, List<User> list) {
            this.context = context;
            this.listUser = list;
        }

        public void setMlistener(AdapterView.OnItemClickListener mlistener) {
            this.mlistener = mlistener;
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
//            holder.txtUserType.setText(listUser.get(position).getUserType().getTypeName());
            holder.txtUserType.setText("用户类型");
            holder.txtUserDel.setText("删除");
            holder.txtUserEdit.setText("修改");
            holder.txtUserEnable.setText(listUser.get(position).isEnable() ? "可用" : "禁用");
        }

        @Override
        public int getItemCount() {
            return listUser.size();
        }
    }

}
