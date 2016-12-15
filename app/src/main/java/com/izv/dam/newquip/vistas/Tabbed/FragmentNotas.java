package com.izv.dam.newquip.vistas.Tabbed;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorNota;
import com.izv.dam.newquip.vistas.main.PresentadorQuip;

/**
 * Created by dam on 08/11/2016.
 */

public class FragmentNotas extends Fragment {
    private static final String ARG_SECTION_NUMBER = "index";
    private static AdaptadorNota adapt;
    private static PresentadorQuip present;

    public FragmentNotas() {
    }

    public static FragmentNotas newInstance(int position, AdaptadorNota adaptador, PresentadorQuip presentador) {

        // Instantiate a new fragment
        FragmentNotas fragment = new FragmentNotas();
        adapt=adaptador;
        present=presentador;

        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, position);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_notas, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        int display_mode = getResources().getConfiguration().orientation;

        if (display_mode == Configuration.ORIENTATION_PORTRAIT) {
            StaggeredGridLayoutManager linear = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linear);
            recyclerView.setAdapter(adapt);
        } else {
            StaggeredGridLayoutManager linear = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linear);
            recyclerView.setAdapter(adapt);
        }

        return rootView;
    }

    @Override
    public void onPause() {
        present.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        present.onResume();
        super.onResume();
    }


}