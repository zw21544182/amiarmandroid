package cn.ml_tech.mx.mlservice.DAO;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * 相机参数
 */
public class CameraParams extends DataSupport {
    @Column(unique = true, nullable = false)
    private String ParamName;//参数名
    @Column(nullable = false)
    private double ParamValue;//参数值

    public String getParamName() {
        return ParamName;
    }

    public void setParamName(String paramName) {
        ParamName = paramName;
    }

    public double getParamValue() {
        return ParamValue;
    }

    public void setParamValue(double paramValue) {
        ParamValue = paramValue;
    }
}
