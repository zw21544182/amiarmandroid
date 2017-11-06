package cn.ml_tech.mx.mlproj.settingfragment;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.activity.XtwhActivity;
import cn.ml_tech.mx.mlproj.adapter.AuditTrackAdapter;
import cn.ml_tech.mx.mlproj.adapter.StringAdapter;
import cn.ml_tech.mx.mlproj.base.AmiApp;
import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlservice.DAO.AuditTrail;
import cn.ml_tech.mx.mlservice.DAO.AuditTrailEventType;
import cn.ml_tech.mx.mlservice.DAO.AuditTrailInfoType;
import cn.ml_tech.mx.mlservice.DAO.User;

/**
 * 创建时间: 2017/6/29
 * 创建人: zhongwang
 * 功能描述:审计追踪
 */

public class AuditTrackFragment extends BaseFragment implements View.OnClickListener {
    private Spinner speventtype;
    private Spinner spinfotype;
    private Spinner etuser;
    private Button btsearch;
    private Button btresver;
    private RecyclerView rvaudiotdata;
    private List<AuditTrailEventType> eventTypes;
    private List<AuditTrailInfoType> infoTypes;
    private List<String> eventstrings;
    private List<String> infostrings;
    private List<User> userList;
    private EditText startDate;
    private EditText stopDate;
    private long staDate = 0, stoDate = 0;
    private SimpleDateFormat dateFormat;
    private List<AuditTrail> auditTrails;
    private AuditTrackAdapter auditTrackAdapter;
    private List<String> username;
    private XtwhActivity xtwhActivity;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_auditrack, null);
        initFindViewById(view);
        return view;
    }

    @Override
    protected void initEvent() {
        setDateToEdit(stopDate);
        setDateToEdit(startDate);
        btsearch.setOnClickListener(this);
    }

    @Override
    public void initFindViewById(View view) {
        speventtype = (Spinner) view.findViewById(R.id.speventtype);
        spinfotype = (Spinner) view.findViewById(R.id.spinfotype);
        etuser = (Spinner) view.findViewById(R.id.etuser);
        btsearch = (Button) view.findViewById(R.id.btsearch);
        btresver = (Button) view.findViewById(R.id.btresver);
        rvaudiotdata = (RecyclerView) view.findViewById(R.id.rvaudiotdata);
        rvaudiotdata.setLayoutManager(new LinearLayoutManager(getActivity()));
        stopDate = (EditText) view.findViewById(R.id.etStartDate);
        startDate = (EditText) view.findViewById(R.id.etStopDate);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        xtwhActivity = (XtwhActivity) getActivity();
        String url = "";
        speventtype.setSelection(0);
        spinfotype.setSelection(0);
        username = new ArrayList<>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        eventTypes = new ArrayList<>();
        eventstrings = new ArrayList<>();
        infoTypes = new ArrayList<>();
        infostrings = new ArrayList<>();
        try {
            userList = mlService.getUserList();
            eventTypes = mlService.getAuditTrailEventType();
            infoTypes = mlService.getAuditTrailInfoType();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (User user :
                userList) {
            username.add(user.getUserName());
        }
        for (AuditTrailEventType eventType :
                eventTypes) {
            eventstrings.add(eventType.getName());
        }
        for (AuditTrailInfoType infoType :
                infoTypes) {
            infostrings.add(infoType.getName());

        }
        etuser.setAdapter(new StringAdapter(username, getActivity()));
        speventtype.setAdapter(new StringAdapter(eventstrings, getActivity()));
        spinfotype.setAdapter(new StringAdapter(infostrings, getActivity()));

        long selfId = ((AmiApp) xtwhActivity.getApplication()).getUserid();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (user.getId() == selfId) {
                etuser.setSelection(i);
            }

        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            initData(null);
    }

    private void setDateToEdit(final EditText dateView) {
        dateView.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDatePickDlg(dateView);
                    return true;
                }
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void showDatePickDlg(final EditText dateView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String times = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                Date current = new Date();
                Date time = null;
                try {
                    Log.d("ZW", times);
                    time = dateFormat.parse(times);
                    if (time.getTime() > (current.getTime())) {
                        Log.d("ZW", "选择框时间" + time.getTime() + " 当前时间" + current.getTime());
                        Toast.makeText(getActivity(), "不能大于当前时间", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    switch (dateView.getId()) {
                        case R.id.etStartDate:
                            staDate = time.getTime();
                            if (stoDate != 0) {
                                if (staDate < stoDate) {
                                    Toast.makeText(getActivity(), "开始时间不能小于结束时间", Toast.LENGTH_SHORT).show();
                                }
                            }
                            Log.d("ZW", "开始时间" + staDate);
                            dateView.setText(times);
                            stopDate.setEnabled(true);
                            break;
                        case R.id.etStopDate:
                            stoDate = time.getTime();
                            Log.d("ZW", "开始时间" + staDate + " 结束时间" + stoDate);

                            if (stoDate < staDate) {
                                Toast.makeText(getActivity(), "结束时间不能大于开始时间", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            dateView.setText(times);

                            break;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btsearch:

                if (TextUtils.isEmpty(etuser.getSelectedItem().toString()) ||
                        TextUtils.isEmpty(stopDate.getEditableText().toString()) ||
                        TextUtils.isEmpty(startDate.getEditableText().toString())) {
                    showToast("请检查信息是否填写完整");
                    return;
                }
                try {
                    auditTrails = mActivity.getmService().getAuditTrail(getStartTime(), getStopTime(), getUserName(), getEventId(),
                            getInfoId());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                AuditTrackAdapter auditTrackAdapter = new AuditTrackAdapter(auditTrails, getActivity());
                rvaudiotdata.setAdapter(auditTrackAdapter);
                break;
        }
    }

    public String getStartTime() {
        return stopDate.getEditableText().toString();
    }

    public String getStopTime() {
        return startDate.getEditableText().toString();
    }

    public String getUserName() {
        return etuser.getSelectedItem().toString();
    }

    public int getEventId() {
        return speventtype.getSelectedItemPosition() + 1;
    }

    public int getInfoId() {
        return spinfotype.getSelectedItemPosition() + 1;
    }


}
