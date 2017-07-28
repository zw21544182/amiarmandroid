package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.jeesoft.widget.pickerview.CharacterPickerWindow;
import cn.ml_tech.mx.mlservice.DAO.Factory;

import static android.view.View.Y;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YpxaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YpxaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YpxaFragment extends BaseFragment {
    private String province_code;
    private String city_code;
    private String area_code;
    private View view;
    private ImageButton btBack;
    private EditText etMachineFactoryName;
    private EditText etMachineFactoryAddr;
    private EditText etMachineFactoryAddress;
    private EditText etMachineFactoryPhone;
    private EditText etMachineFactoryFax;
    private EditText etMachineFactoryEmail;
    private EditText etUserName;
    private EditText etPhone;
    private Button btnSaveFactory;
    private Button resver;
    private YpjcActivity ypjcActivity;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_ypxa, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        btBack = (ImageButton) view.findViewById(R.id.bt_back);
        etMachineFactoryName = (EditText) view.findViewById(R.id.etMachineFactoryName);
        etMachineFactoryAddr = (EditText) view.findViewById(R.id.etMachineFactoryAddr);
        etMachineFactoryAddress = (EditText) view.findViewById(R.id.etMachineFactoryAddress);
        etMachineFactoryPhone = (EditText) view.findViewById(R.id.etMachineFactoryPhone);
        etMachineFactoryFax = (EditText) view.findViewById(R.id.etMachineFactoryFax);
        etMachineFactoryEmail = (EditText) view.findViewById(R.id.etMachineFactoryEmail);
        etUserName = (EditText) view.findViewById(R.id.etUserName);
        etPhone = (EditText) view.findViewById(R.id.etPhone);
        btnSaveFactory = (Button) view.findViewById(R.id.btnSaveFactory);
        resver = (Button) view.findViewById(R.id.resver);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ypjcActivity = (YpjcActivity) getActivity();
        setPermission(ypjcActivity.permission);
        getActivity().findViewById(R.id.etMachineFactoryAddr).setOnTouchListener((View.OnTouchListener) getActivity());
        getActivity().findViewById(R.id.btnSaveFactory).setOnClickListener((View.OnClickListener) getActivity());
        getActivity().findViewById(R.id.bt_back).setOnClickListener((View.OnClickListener) getActivity());

    }


    public Factory getFactory() {
        Factory factory = new Factory();
        //etMachineFactoryName
        if (((EditText) getActivity().findViewById(R.id.etMachineFactoryName)).getEditableText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "请填写厂家名称", Toast.LENGTH_SHORT).show();
            return null;
        }
        if ((((EditText) getActivity().findViewById(R.id.etMachineFactoryAddress)).getEditableText().toString().trim().equals("")))

        {
            Toast.makeText(getActivity(), "请填写厂家地址", Toast.LENGTH_SHORT).show();
            return null;

        }
        if (province_code.trim().equals("") || city_code.trim().equals("") || area_code.trim().equals("")) {
            Toast.makeText(getActivity(), "请填写地址", Toast.LENGTH_SHORT).show();
            return null;
        }
        factory.setName(((EditText) getActivity().findViewById(R.id.etMachineFactoryName)).getEditableText().toString());
        factory.setAddress(((EditText) getActivity().findViewById(R.id.etMachineFactoryAddress)).getEditableText().toString());
        factory.setPhone(((EditText) getActivity().findViewById(R.id.etMachineFactoryPhone)).getEditableText().toString());
        factory.setFax(((EditText) getActivity().findViewById(R.id.etMachineFactoryFax)).getEditableText().toString());
        factory.setMail(((EditText) getActivity().findViewById(R.id.etMachineFactoryEmail)).getEditableText().toString());
        factory.setContactName(((EditText) getActivity().findViewById(R.id.etUserName)).getEditableText().toString());
        factory.setContactPhone(((EditText) getActivity().findViewById(R.id.etPhone)).getEditableText().toString());
        factory.setProvince_code(province_code);
        factory.setCity_code(city_code);
        factory.setArea_code(area_code);
        return factory;
    }

    public void showWindow() {
        final CharacterPickerWindow window = OptionsWindowHelper.builder(getActivity(), new OptionsWindowHelper.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(String province, String city, String area) {
                YpxaFragment.this.province_code = province;
                YpxaFragment.this.city_code = city;
                YpxaFragment.this.area_code = area;
                ((TextView) getActivity().findViewById(R.id.etMachineFactoryAddr)).setText(province + city + area);
            }
        });
        window.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

    }

}
