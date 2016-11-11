package com.izv.dam.newquip.vistas.Tabbed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorNota;
import com.izv.dam.newquip.vistas.main.PresentadorQuip;

/**
 * Created by dam on 07/11/2016.
 */

public class FragmentListas extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public FragmentListas() {
    }

    public static FragmentListas newInstance(int sectionNumber, AdaptadorNota adaptador, PresentadorQuip presentador) {
        FragmentListas fragment = new FragmentListas();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notas, container, false);
        return rootView;
    }
}

