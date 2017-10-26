package cn.ml_tech.mx.mlproj.settingfragment;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ml_tech.mx.mlproj.base.BaseFragment;
import cn.ml_tech.mx.mlproj.R;

/**
 * 创建时间: 2017/6/29
 * 创建人: zhongwang
 * 功能描述:
 */

public class LogShowFragment extends BaseFragment {
    private EditText etLogShow;
    private RecyclerView rvlogdata;
    private SimpleDateFormat dateFormat;
    private String currenttime;
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_logshow, null);
        initFindViewById(view);
        return view;
    }
    @Override
    protected void initEvent() {
        super.initEvent();
        etLogShow.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDatePickDlg(etLogShow);
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public void initFindViewById(View view) {
        etLogShow = (EditText) view.findViewById(R.id.etLogShow);
        rvlogdata = (RecyclerView) view.findViewById(R.id.rvlogdata);
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        currenttime = dateFormat.format(new Date());
        etLogShow.setText(currenttime);
        loadData(currenttime);

    }
    private void loadData(String currenttime) {
        showToast("加载" + currenttime + "的数据");
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
                        Toast.makeText(getActivity(), "不能大于当前时间", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dateView.setText(times);
                    loadData(times);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            initData(null);
    }
}
