package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ml on 2017/5/18.
 */

public class UserManagerFragment extends Fragment {
    private final  String TAG="UserManagerFragment";
    public UserManagerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: onCreateView");
    return   inflater.inflate(R.layout.fragment_usermanager,container,false);
       // return super.onCreateView(inflater, container, savedInstanceState);
    }

}
