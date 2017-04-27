package cn.ml_tech.mx.mlproj;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import DrugStandardCheck.workflowitemView;

public class CsbdActivity  extends BaseActivity implements CsbdFragment.OnFragmentInteractionListener, View.OnClickListener {
    CsbdFragment csbdFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_csbd);
        LogDebug(CsbdFragment.class.getSimpleName());
        csbdFragment = (CsbdFragment) switchContentFragment(CsbdFragment.class.getSimpleName());
    }
    protected void onStart() {
        super.onStart();
        CsbdFragment f=(CsbdFragment) getFragment(CsbdFragment.class.getSimpleName());
//        CsbdActivity.this.findViewById(R.id.btBack).setOnClickListener(this);
        if(f!=null)
        {
           View view= f.getView();
          Button btnBack= (Button) view.findViewById(R.id.btBack);
            if(btnBack!=null)
            LogDebug("btnback is not null ");
            btnBack.setOnClickListener(CsbdActivity.this);
            LinearLayout ll=(LinearLayout)view.findViewById(R.id.layoutworkflow);
            //read from database

            List<String > list=new ArrayList<String>();

            list.add("自动进样");

            list.add("机械手取样");
            list.add("固定样品");
            list.add("高速旋瓶");
            list.add("激光扫描");
            list.add("结果显示");
            list.add("自动分拣");
            for (int var =0;var<list.size();var++)
            {
               String[]temp= list.get(var).split("");
                String string= TextUtils.join("\n",temp);
                string=string.substring(1,string.length());
                list.set(var,string);
            }
            LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            for(int var=0;var<list.size();var++){
                workflowitemView w=new workflowitemView(CsbdActivity.this,null,list.get(var),Color.BLACK,Color.BLUE);
                w.setHeight(160);
              w.setMinWidth(60);
              w.setMaxWidth(80);
                w.getPaint().setFakeBoldText(true);
                w.setGravity(Gravity.CENTER);
                w.setTextSize(22);
                w.setBackgroundColor(Color.BLUE);
                w.setTextcolor(Color.RED);
                llp.setMargins(0,0,20,0);
                w.setLayoutParams(llp);
                ll.addView(w);
                if(var<list.size()-1){
                 ImageView imageView=new ImageView(CsbdActivity.this);
                 imageView.setImageDrawable(getResources().getDrawable(R.drawable.arrowright));
                    llp.setMargins(20,0,0,0);
                    imageView.setLayoutParams(llp);
                     ll.addView(imageView);
                }
            }
        }

    }

    @Override
    protected Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);
        if (f == null) {
            if (tag.equals("content")) {
            } else if (tag.equals("CsbdFragment")) {
                LogDebug("new CsbdFragment");
                f = new CsbdFragment();
            } else {
                f = super.getFragment(tag);
            }
        }
        return f;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btBack:
             //   this.getFragment(csbdFragment.getClass().getSimpleName()).onDetach();
                LogDebug("the onclick is btnBack");
                this.finish();
                break;
            default:
        }
    }
}
