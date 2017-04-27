package DrugStandardCheck;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ml on 2017/4/22.
 */

public class workflowitemView extends TextView{
    private String title;
    private int bgcolor;
    private int textcolor;
    private boolean isHightLight;
    public int getTextcolor() {
        return textcolor;
    }

    public void setTextcolor(int textcolor) {
        this.textcolor = textcolor;
    }

    public boolean isHightLight() {
        return isHightLight;
    }

    public void setHightLight(boolean hightLight) {
        isHightLight = hightLight;
    }

    public workflowitemView(Context context) {
        super(context);
    }
    public  workflowitemView(Context context,@Nullable AttributeSet attrs,String text,int textcolor,int bgcolor){
        super(context,attrs);

        this.setTextcolor(textcolor);
        this.setText(text);
        this.setBgcolor(bgcolor);
    }
    public workflowitemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }
    public int getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(int bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
