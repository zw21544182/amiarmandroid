package cn.ml_tech.mx.mlproj.customview.SystemSetUp;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ml on 2017/5/16.
 */

public class MenuItemView extends RelativeLayout {
    private static final String NAMESPACE="http://schemas.android.com/apk/res/cn.ml_tech.mx.mlproj";
    private String mTitle;
    private TextView txtTitle;
    private String fragmentTag;

    public String getFragmentTag() {
        return fragmentTag;
    }

    public void setFragmentTag(String fragmentTag) {
        this.fragmentTag = fragmentTag;
    }

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
//        //将自定义好的布局文件加载给当前的MenuItemView
//        View.inflate(getContext(), R.layout.view_menu_item,this);
//        txtTitle= (TextView) findViewById(R.id.txtSubMenuText);
//        setTitle(mTitle);
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                XtwhActivity activity= ((XtwhActivity)getContext());
//                activity.switchContentFragment(getFragmentTag());
//            }
//        });
    }
    public void setTitle(String title)
    {
        txtTitle.setText(title);
    }
    public void setViewOnClickListener(OnClickListener l)
    {
        this.setOnClickListener(l);
    }


}
