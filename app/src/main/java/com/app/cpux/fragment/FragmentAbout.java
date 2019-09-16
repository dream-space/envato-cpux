package com.app.cpux.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.cpux.R;
import com.app.cpux.tools.Utils;

public class FragmentAbout extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        rootView.findViewById(R.id.bt_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.rateAction(getActivity());
            }
        });


        return rootView;
    }
}
