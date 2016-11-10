package com.izv.dam.newquip.vistas.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.izv.dam.newquip.R;

/**
 * Created by dam on 08/11/2016.
 */

public class prueba2 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public prueba2() {
    }

    public static prueba2 newInstance(int sectionNumber) {
        prueba2 fragment = new prueba2();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.prueba2, container, false);
        return rootView;
    }

}
