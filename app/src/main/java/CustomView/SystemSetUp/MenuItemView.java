package SystemSetUp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.ml_tech.mx.mlproj.R;
/**
 * Created by ml on 2017/5/16.
 */

public class MenuItemView extends RelativeLayout {
    private static final String NAMESPACE="http://schemas.android.com/apk/res/cn.ml_tech.mx.mlproj";
    private String mTitle;
    private TextView txtTitle;

    public MenuItemView(Context context) {
        super(context);
        initView();
    }
    public MenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTitle=attrs.getAttributeValue(NAMESPACE,"title");//根据属性名称获取属性的值

        initView();
    }

    public MenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }
    /*
    初始化布局
     */
    private void initView()
    {
        //将自定义好的布局文件加载给当前的MenuItemView
        View.inflate(getContext(), R.layout.view_menu_item,this);
        txtTitle= (TextView) findViewById(R.id.txtSubMenuText);
        setTitle(mTitle);
    }
    public void setTitle(String title)
    {
        txtTitle.setText(title);
    }
}
