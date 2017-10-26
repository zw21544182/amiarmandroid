package cn.ml_tech.mx.mlproj.customview.SystemSetUp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import cn.ml_tech.mx.mlproj.R;

/**
 * 创建时间: 2017/7/21
 * 创建人: zhongwang
 * 功能描述:
 */

public class DataItmeView extends LinearLayout {
    private EditText content;
    private LinearLayout datalayout;


    public DataItmeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.dataview, this);
        content = (EditText) findViewById(R.id.content);
        datalayout = (LinearLayout) findViewById(R.id.datalayout);
    }

    public EditText getContentView() {
        return content;
    }

    public void setContentEdit(boolean canEdit) {
        content.setEnabled(canEdit);
    }

    public void setContentValue(String value) {
        content.setText(value);
    }

    public void setBackGroudColor(int color) {
        datalayout.setBackgroundResource(color);
    }

    public String getContentValue() {
        if (content != null)
            return content.getEditableText().toString();
        return "";
    }

}
