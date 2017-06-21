package cn.ml_tech.mx.mlproj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import cn.ml_tech.mx.CustomView.SystemSetUp.MenuItemView;

/**
 * Created by ml on 2017/6/9.
 */

public class SystemSetUpMainFragment extends BaseFragment implements View.OnClickListener {
    private String[]arrayItemMenu;

    @Override
    public View initView(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.layout_systemfragmentmain, null);
        initFindViewById(view);
        return view;
    }
    @Override
    protected   void initEvent()
    {

    }



    @Override
    public void initFindViewById(View view) {
        initMenus();
    }
    private void initMenus()
    {
        arrayItemMenu=new String[]{
                getString(R.string.MenuSystemMain),
                getString(R.string.MenuUser),
                getString(R.string.MenuDeviceDebug),
                getString(R.string.MenuTray),
                getString(R.string.MenuInformation),
                getString(R.string.MenuDrugParam),
                getString(R.string.MenuData),
                getString(R.string.MenuSysConfig),
                getString(R.string.MenuCaptureConfig),
                getString(R.string.MenuAuditTrail),
                getString(R.string.MenuRightManager),
                getString(R.string.MenuProgramUpdate),
                getString(R.string.MenuLogShow),
        };
        LinearLayout llRoot= (LinearLayout) view.findViewById(R.id.llSystemRoot);
        for (int i=0;i<arrayItemMenu.length;i++)
        {
            MenuItemView itemView=new MenuItemView(getParentFragment().getActivity());
            itemView.setTitle(arrayItemMenu[i]);
            llRoot.addView(itemView);
        }

    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {

    }
}
