package cn.ml_tech.mx.mlproj.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ml on 2017/6/13.
 */

public class ActivityUtil {
    public static List<Activity> activityList = new ArrayList<Activity>();
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }
    public static void removeActivity(Activity activity) {
        if(!activity.isFinishing())activity.finish();
        activityList.remove(activity);
    }
    public static void finishAll() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
    }

}
