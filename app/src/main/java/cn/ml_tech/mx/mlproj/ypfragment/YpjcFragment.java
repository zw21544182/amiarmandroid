package cn.ml_tech.mx.mlproj.ypfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.activity.YpjcActivity;
import cn.ml_tech.mx.mlproj.base.BaseFragment;


public class YpjcFragment extends BaseFragment {
    private View view;
    private RadioButton rbNew;
    private RadioButton rbContinue;
    private Button btPre;
    private Button btNext;

    public boolean isContinue() {
        return rbContinue.isChecked();
    }

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_ypjc, null);
        initFindViewById(view);
        return view;
    }

    public void initFindViewById(View view) {
        rbNew = (RadioButton) view.findViewById(R.id.rbNew);
        rbContinue = (RadioButton) view.findViewById(R.id.rbContinue);
        btPre = (Button) view.findViewById(R.id.btPre);
        btNext = (Button) view.findViewById(R.id.btNext);
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }
    protected void initEvent() {
        btPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        btNext.setOnClickListener((YpjcActivity) getActivity());

    }

}
