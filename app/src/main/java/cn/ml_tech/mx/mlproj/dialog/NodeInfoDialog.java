package cn.ml_tech.mx.mlproj.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.ml_tech.mx.mlproj.R;

/**
 * 创建时间: 2017/7/12
 * 创建人: zhongwang
 * 功能描述: 显示DetectionDetail nodeInfo对话框
 */

public class NodeInfoDialog extends Dialog implements View.OnClickListener {
    private Context context;      // 上下文
    private int layoutResID;      // 布局文件id
    private int[] listenedItems;
    private JSONObject jsonObject;
    private RelativeLayout nodelayout;
    private TextView tvPiaoFuNum;
    private TextView tvSuJianPer;
    private TextView tvsuJianTime;
    private TextView tvYi40;
    private TextView tvYi50;
    private TextView tvYi60;
    private TextView tvYi70;
    private TextView tvNum40;
    private TextView tvNum50;
    private TextView tvNum70;
    private TextView tvPiaoFuRes;
    private TextView tvSuJianRes;
    private TextView tvYiRes;
    private TextView tvNumRes;
    private TextView tvMaxRes;


    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public NodeInfoDialog(@NonNull Context context) {
        super(context, R.style.dialog_node);
    }

    public NodeInfoDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public NodeInfoDialog(Context context, int layoutResID, int[] listenedItems) {
        super(context, R.style.dialog_node); //dialog的样式
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        initView();
        setDataToView();
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 4 / 10;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
        //遍历控件id,添加点击事件
        for (int id : listenedItems) {
            findViewById(id).setOnClickListener(this);
        }
    }

    private void setDataToView() {
        try {
            JSONObject value = jsonObject.getJSONObject("floatdta");
            tvPiaoFuNum.setText(value.getDouble("data") + "");
            tvPiaoFuRes.setText(value.getString("result"));
            value = jsonObject.getJSONObject("glassprecent");
            tvSuJianRes.setText(value.getString("result"));
            tvSuJianPer.setText(value.getDouble("data") + "");
            tvsuJianTime.setText(jsonObject.getJSONObject("glasstime").getDouble("data") + "");
            tvYi40.setText(jsonObject.getJSONObject("statistics40").getDouble("data") + "");
            tvYi50.setText(jsonObject.getJSONObject("statistics50").getDouble("data") + "");
            tvYi60.setText(jsonObject.getJSONObject("statistics60").getDouble("data") + "");
            tvYi70.setText(jsonObject.getJSONObject("statistics70").getDouble("data") + "");
            tvNum40.setText(jsonObject.getJSONObject("min").getDouble("data") + "");
            tvNum50.setText(jsonObject.getJSONObject("max").getDouble("data") + "");
            tvNum70.setText(jsonObject.getJSONObject("super").getDouble("data") + "");
            tvYiRes.setText(jsonObject.getJSONObject("statistics40").getString("result") + "");
            tvNumRes.setText(jsonObject.getJSONObject("min").getString("result") + "");
            tvMaxRes.setText(jsonObject.getJSONObject("max").getString("result") + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        nodelayout = (RelativeLayout) findViewById(R.id.nodelayout);
        tvPiaoFuNum = (TextView) findViewById(R.id.tvPiaoFuNum);
        tvSuJianPer = (TextView) findViewById(R.id.tvSuJianPer);
        tvsuJianTime = (TextView) findViewById(R.id.tvsuJianTime);
        tvYi40 = (TextView) findViewById(R.id.tvYi40);
        tvYi50 = (TextView) findViewById(R.id.tvYi50);
        tvYi60 = (TextView) findViewById(R.id.tvYi60);
        tvYi70 = (TextView) findViewById(R.id.tvYi70);
        tvNum40 = (TextView) findViewById(R.id.tvNum40);
        tvNum50 = (TextView) findViewById(R.id.tvNum50);
        tvNum70 = (TextView) findViewById(R.id.tvNum70);
        tvPiaoFuRes = (TextView) findViewById(R.id.tvPiaoFuRes);
        tvSuJianRes = (TextView) findViewById(R.id.tvSuJianRes);
        tvYiRes = (TextView) findViewById(R.id.tvYiRes);
        tvNumRes = (TextView) findViewById(R.id.tvNumRes);
        tvMaxRes = (TextView) findViewById(R.id.tvMaxRes);
    }

    private OnCenterItemClickListener listener;

    public interface OnCenterItemClickListener {
        void OnCenterItemClick(NodeInfoDialog dialog, View view);
    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        dismiss();//注意：我在这里加了这句话，表示只要按任何一个控件的id,弹窗都会消失，不管是确定还是取消。
        listener.OnCenterItemClick(this, v);

    }
}
