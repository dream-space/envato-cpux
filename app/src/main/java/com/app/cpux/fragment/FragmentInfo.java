package com.app.cpux.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.cpux.R;
import com.app.cpux.adapter.AdapterListItem;
import com.app.cpux.data.SharedPref;
import com.app.cpux.tools.LoaderData;

public class FragmentInfo extends Fragment {

    public static final String ARG_SECTION_TITLE = "section_number";

    private ListView listView1;
    private String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);
        title = getArguments().getString(ARG_SECTION_TITLE, "");

        listView1 = (ListView) rootView.findViewById(R.id.listView1);
        loadAndDisplayData(title);

        return rootView;
    }

    public void loadAndDisplayData(String title) {
        String s = "";
        if (title.equalsIgnoreCase(getString(R.string.tab_title_cpu))) {
            s = SharedPref.getCPUData(getActivity());
            listView1.setAdapter(new AdapterListItem(getActivity(), R.layout.item_list_info, LoaderData.getArrList(s)));

        } else if (title.equalsIgnoreCase(getString(R.string.tab_title_device))) {
            s = SharedPref.getDeviceData(getActivity());
            listView1.setAdapter(new AdapterListItem(getActivity(), R.layout.item_list_info, LoaderData.getArrList(s)));

        } else if (title.equalsIgnoreCase(getString(R.string.tab_title_system))) {
            s = SharedPref.getSystemData(getActivity());
            listView1.setAdapter(new AdapterListItem(getActivity(), R.layout.item_list_info, LoaderData.getArrList(s)));

        } else if (title.equalsIgnoreCase(getString(R.string.tab_title_battery))) {
            s = SharedPref.getBateryData(getActivity());
            listView1.setAdapter(new AdapterListItem(getActivity(), R.layout.item_list_info, LoaderData.getArrList(s)));

        } else if (title.equalsIgnoreCase(getString(R.string.tab_title_sensor))) {
            s = SharedPref.getSensorData(getActivity());
            listView1.setAdapter(new AdapterListItem(getActivity(), R.layout.item_list_info, LoaderData.getArrList(s)));

        }

    }

}