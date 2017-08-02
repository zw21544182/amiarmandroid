package cn.ml_tech.mx.mlproj.Dialog;

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
    private TextView tvPiaoFuNum;
    private TextView tvPiaoFuRes;
    private TextView tvSuJianPer;
    private TextView tvSuJianRes;
    private TextView tvsuJianTime;
    private TextView tvCheck40;
    private TextView tvCheckRes;
    private TextView tvCheck50;
    private TextView tvCheck60;
    private TextView tvCheck70;
    private TextView tvCheckNum40;
    private TextView tvMinNumRes;
    private TextView tvCheckNum50;
    private TextView tvCheckNum70;
    private TextView tvMaxCheckRes;


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
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(layoutResID);
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
        initView();
        setDataToView();
    }

    private void setDataToView() {
        try {
            JSONObject floatdata = jsonObject.getJSONObject("floatdta");
            tvPiaoFuNum.setText(floatdata.getDouble("data") + "");
            tvPiaoFuRes.setText(floatdata.getString("result"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tvPiaoFuNum = (TextView) findViewById(R.id.tvPiaoFuNum);
        tvPiaoFuRes = (TextView) findViewById(R.id.tvPiaoFuRes);
        tvSuJianPer = (TextView) findViewById(R.id.tvSuJianPer);
        tvSuJianRes = (TextView) findViewById(R.id.tvSuJianRes);
        tvsuJianTime = (TextView) findViewById(R.id.tvsuJianTime);
        tvCheck40 = (TextView) findViewById(R.id.tvCheck40);
        tvCheckRes = (TextView) findViewById(R.id.tvCheckRes);
        tvCheck50 = (TextView) findViewById(R.id.tvCheck50);
        tvCheck60 = (TextView) findViewById(R.id.tvCheck60);
        tvCheck70 = (TextView) findViewById(R.id.tvCheck70);
        tvCheckNum40 = (TextView) findViewById(R.id.tvCheckNum40);
        tvMinNumRes = (TextView) findViewById(R.id.tvMinNumRes);
        tvCheckNum50 = (TextView) findViewById(R.id.tvCheckNum50);
        tvCheckNum70 = (TextView) findViewById(R.id.tvCheckNum70);
        tvMaxCheckRes = (TextView) findViewById(R.id.tvMaxCheckRes);
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
