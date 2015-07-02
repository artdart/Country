package com.dataart.country.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dataart.country.Country;
import com.dataart.country.R;

/**
 * Created by aakimov on 26/06/15.
 */
public class FaceFragment extends Fragment {
    private ImageView flagImage;
    private TextView nameText;
    private TextView populationText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_face, container, false);
        flagImage = (ImageView) view.findViewById(R.id.flag);
        nameText = (TextView) view.findViewById(R.id.name);
        populationText = (TextView) view.findViewById(R.id.population);
        return view;
    }

    public void fillCountry(Country country) {
        flagImage.setImageBitmap(country.flag);
        nameText.setText(country.name);
        populationText.setText(getString(R.string.population, country.population));
    }
}
