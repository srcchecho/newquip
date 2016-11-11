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
 * Created by dam on 08/11/2016.
 */

public class FragmentUnion extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public FragmentUnion() {
    }

    public static FragmentUnion newInstance(int sectionNumber, AdaptadorNota adaptador, PresentadorQuip presentador) {
        FragmentUnion fragment = new FragmentUnion();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_union, container, false);
        return rootView;
    }

}
