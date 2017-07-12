package cn.ml_tech.mx.mlproj;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ml_tech.mx.mlservice.IMlService;

/**
 * 创建时间: 2017/6/23
 * 创建人: zhongwang
 * 功能描述:
 */

public class JcsjcxmainFragment extends BaseFragment {
    private RecyclerView checkDrug;
    private Button resver;
    private Button search;
    private EditText stopDate;
    private EditText startDate;
    private EditText retrieveNum;
    private EditText checkNum;
    private EditText drugFactory;
    private EditText drugName;
    private EditText checkFormat;
    private SimpleDateFormat dateFormat;
    private long staDate = 0, stoDate = 0;
    private IMlService iMlService;

    public IMlService getiMlService() {
        return iMlService;
    }

    public void setiMlService(IMlService iMlService) {
        this.iMlService = iMlService;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_jcsjcxmain, null);
        initFindViewById(view);
        return view;

    }

    @Override
    public void initFindViewById(View view) {
        checkDrug = (RecyclerView) view.findViewById(R.id.rvCheckDrug);
        resver = (Button) view.findViewById(R.id.btResver);
        search = (Button) view.findViewById(R.id.btSearch);
        stopDate = (EditText) view.findViewById(R.id.etStopDate);
        startDate = (EditText) view.findViewById(R.id.etStartDate);
        retrieveNum = (EditText) view.findViewById(R.id.etRetrieveNum);
        checkNum = (EditText) view.findViewById(R.id.etCheckNum);
        drugFactory = (EditText) view.findViewById(R.id.etDrugFactory);
        drugName = (EditText) view.findViewById(R.id.etDrugName);
        checkFormat = (EditText) view.findViewById(R.id.etCheckFormat);
        event();
    }

    private void event() {
        setDateToEdit(stopDate);
        setDateToEdit(startDate);

    }

    @Override
    protected void initEvent() {
        super.initEvent();

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

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
}
