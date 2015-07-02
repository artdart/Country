package com.dataart.country.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dataart.country.Country;
import com.dataart.country.R;

/**
 * Created by aakimov on 26/06/15.
 */
public class BackFragment extends Fragment {
    private Button backBtn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_back, container, false);
           return view;
    }

}
