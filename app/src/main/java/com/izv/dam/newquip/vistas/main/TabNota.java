package com.izv.dam.newquip.vistas.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorNota;

/**
 * Created by dam on 08/11/2016.
 */

public class TabNota extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public TabNota() {
    }

    public static TabNota newInstance(int sectionNumber, AdaptadorNota adaptador, PresentadorQuip presentador) {
        TabNota fragment = new TabNota();
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
