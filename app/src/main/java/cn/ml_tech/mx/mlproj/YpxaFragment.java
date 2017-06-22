package cn.ml_tech.mx.mlproj;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.jeesoft.widget.pickerview.CharacterPickerWindow;
import cn.ml_tech.mx.mlservice.DAO.Factory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YpxaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YpxaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YpxaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param12";
    private static final String ARG_PARAM2 = "param23";
    private String province_code;
    private String city_code;
    private String area_code;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public YpxaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YpxaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YpxaFragment newInstance(String param1, String param2) {
        YpxaFragment fragment = new YpxaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ypxa, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
