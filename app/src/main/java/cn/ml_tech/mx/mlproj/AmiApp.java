package cn.ml_tech.mx.mlproj;

import android.app.Application;

/**
 * Created by mx on 2017/5/8.
 */

public class AmiApp extends Application {

    public AmiApp() {
        this.isLogined = false;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getLogined() {
        return isLogined;
    }

    public void setLogined(Boolean logined) {
        isLogined = logined;
    }

    private String userName;
    private Boolean isLogined;


}
