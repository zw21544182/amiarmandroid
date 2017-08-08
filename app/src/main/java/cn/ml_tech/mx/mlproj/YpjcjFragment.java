package cn.ml_tech.mx.mlproj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class YpjcjFragment extends BaseFragment {
    private View view;
    private EditText etDetectionBatch;
    private EditText etdetectionCount;
    private EditText etdetectionNumber;
    private Button ypjcjPre;
    private Button ypjcjNext;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_ypjcj, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        etDetectionBatch = (EditText) view.findViewById(R.id.etDetectionBatch);
        etdetectionCount = (EditText) view.findViewById(R.id.etdetectionCount);
        etdetectionNumber = (EditText) view.findViewById(R.id.etdetectionNumber);
        ypjcjPre = (Button) view.findViewById(R.id.ypjcjPre);
        ypjcjNext = (Button) view.findViewById(R.id.ypjcjNext);

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        ypjcjNext.setOnClickListener((View.OnClickListener) getActivity());
        ypjcjPre.setOnClickListener((View.OnClickListener) getActivity());
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    public String getDetectionBatch() {
        return etDetectionBatch.getEditableText().toString();
    }

    /**
     * 检测数量
     *
     * @return 检测数量
     */
    public String getDetectionCount() {
        return etdetectionCount.getEditableText().toString();
    }

    /**
     * 检测编号
     *
     * @return 检测编号
     */
    public String getDetectionNumber() {
        return etdetectionNumber.getEditableText().toString();
    }

}
