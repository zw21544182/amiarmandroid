package cn.ml_tech.mx.mlproj;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ml_tech.mx.mlservice.DAO.DrugContainer;
import cn.ml_tech.mx.mlservice.FactoryControls;
import cn.ml_tech.mx.mlservice.IMlService;


public class YpxxFragment extends BaseFragment {
    private View view;
    private EditText etDrugName;
    private EditText etPinYin;
    private EditText etEnName;
    private Spinner etBottleType;
    private Spinner etFactory;
    private Button btYpxxAddFactory;
    private Spinner etThreshold;
    private Button btYpxxPre;
    private Button btYpxxNext;

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


    public YpxxFragment() {
        // Required empty public constructor
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
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_ypxx, null);
        initFindViewById(view);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        etDrugName = (EditText) view.findViewById(R.id.etDrugName);
        etPinYin = (EditText) view.findViewById(R.id.etPinYin);
        etEnName = (EditText) view.findViewById(R.id.etEnName);
        etBottleType = (Spinner) view.findViewById(R.id.etBottleType);
        etFactory = (Spinner) view.findViewById(R.id.etFactory);
        btYpxxAddFactory = (Button) view.findViewById(R.id.btYpxxAddFactory);
        etThreshold = (Spinner) view.findViewById(R.id.etThreshold);
        btYpxxPre = (Button) view.findViewById(R.id.btYpxxPre);
        btYpxxNext = (Button) view.findViewById(R.id.btYpxxNext);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ypjcActivity = (YpjcActivity) getActivity();
        setPermission(ypjcActivity.permission);
        btYpxxAddFactory.setVisibility(getPermissionById(12, 1) == true ? View.VISIBLE : View.INVISIBLE);
        btYpxxAddFactory.setEnabled(getPermissionById(12, 8));
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
    public void onDetach() {
        super.onDetach();
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
