package DrugStandardCheck;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ml on 2017/4/22.
 */

public class workflowitemView extends TextView {
    private String title;
    private Color bgcolor;
    public workflowitemView(Context context) {
        super(context);
    }
    public workflowitemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public Color getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(Color bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
