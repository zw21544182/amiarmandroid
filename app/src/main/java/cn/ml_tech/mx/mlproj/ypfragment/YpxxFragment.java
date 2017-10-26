package cn.ml_tech.mx.mlproj.ypfragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlproj.activity.YpjcActivity;
import cn.ml_tech.mx.mlservice.DAO.DrugContainer;
import cn.ml_tech.mx.mlservice.DAO.FactoryControls;
import cn.ml_tech.mx.mlservice.IMlService;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YpxxFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YpxxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YpxxFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private IMlService mService;
    private List<FactoryControls> factoryControlses;
    private List<DrugContainer> typeList;
    List<String> factorydata, specificatedata;
    private YpjcActivity ypjcActivity;

    public IMlService getmService() {
        return mService;
    }

    public void setmService(IMlService mService) {
        this.mService = mService;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public YpxxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YpxxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YpxxFragment newInstance(String param1, String param2) {
        YpxxFragment fragment = new YpxxFragment();
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

    public String getName() {
        return ((EditText) getActivity().findViewById(R.id.etDrugName)).getEditableText().toString();
    }

    public String getPinyin() {
        return ((EditText) getActivity().findViewById(R.id.etPinYin)).getEditableText().toString();

    }

    public String getEnname() {
        return ((EditText) getActivity().findViewById(R.id.etEnName)).getEditableText().toString();

    }

    public int getFactoryId() {
        return ((Spinner) getActivity().findViewById(R.id.etFactory)).getSelectedItemPosition() + 1;
    }

    public int getSpecificationTypeId() {
        return ((Spinner) getActivity().findViewById(R.id.etBottleType)).getSelectedItemPosition() + 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ypxx, container, false);
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
        ypjcActivity = (YpjcActivity) getActivity();
        getActivity().findViewById(R.id.btYpxxPre
        ).setOnClickListener((View.OnClickListener) getActivity());

        getActivity().findViewById(R.id.btYpxxNext).setOnClickListener((View.OnClickListener) getActivity());
        getActivity().findViewById(R.id.btYpxxAddFactory).setOnClickListener((View.OnClickListener) getActivity());
        try {
            factoryControlses = mService.queryFactoryControl();
            typeList = mService.getDrugContainer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        setDataToView(ypjcActivity.pos, ypjcActivity.druginfo_id);

    }

    private void setDataToView(int pos, int drug_id) {
        List<String> channel = new ArrayList<>();
        channel.add("40um通道");
        channel.add("50um通道");
        channel.add("60um通道");
        channel.add("70um通道");
        factorydata = new ArrayList<>();
        for (FactoryControls controls :
                factoryControlses
                ) {
            factorydata.add(controls.getName());
        }
        specificatedata = new ArrayList<String>();
        for (DrugContainer type : typeList
                ) {
            specificatedata.add(type.getName());
        }
        ((Spinner) getActivity().findViewById(R.id.etFactory)).setAdapter(new StringAdapter(factorydata));
        ((Spinner) getActivity().findViewById(R.id.etThreshold)).setAdapter(new StringAdapter(channel));
        ((Spinner) getActivity().findViewById(R.id.etBottleType)).setSelection(0);
        ((Spinner) getActivity().findViewById(R.id.etFactory)).setSelection(0);
        ((Spinner) getActivity().findViewById(R.id.etThreshold)).setSelection(1);
        ((Spinner) getActivity().findViewById(R.id.etBottleType)).setAdapter(new StringAdapter(specificatedata));
        if (drug_id != 0) {
            ((TextView) getActivity().findViewById(R.id.etDrugName)).setText(ypjcActivity.drugControl.getDrugName());
            ((TextView) getActivity().findViewById(R.id.etPinYin)).setText(ypjcActivity.drugControl.getPinyin());
            ((TextView) getActivity().findViewById(R.id.etEnName)).setText(ypjcActivity.drugControl.getEnname());
            for (int i = 0; i < factorydata.size(); i++) {
                if (factorydata.get(i).trim().equals(ypjcActivity.drugControl.getDrugFactory().trim())) {
                    ((Spinner) getActivity().findViewById(R.id.etFactory)).setSelection(i);

                }
            }
            for (int i = 0; i < specificatedata.size(); i++) {
                if (specificatedata.get(i).trim().equals(ypjcActivity.drugControl.getDrugBottleType().trim())) {
                    ((Spinner) getActivity().findViewById(R.id.etBottleType)).setSelection(i);

                }
            }

        }
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

    public class StringAdapter extends BaseAdapter {
        List<String> data;

        private StringAdapter() {
        }

        public StringAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_spinner, null);
                textView = (TextView) convertView.findViewById(R.id.tvFactoryName);
                convertView.setTag(textView);
            } else {
                textView = (TextView) convertView.getTag();
            }
            textView.setText(data.get(position));
            return convertView;
        }
    }
}
