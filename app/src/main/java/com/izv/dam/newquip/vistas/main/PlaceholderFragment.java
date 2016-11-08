package com.izv.dam.newquip.vistas.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorNota;
import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.dialogo.DialogoBorrar;
import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.vistas.notas.VistaNota;
import com.izv.dam.newquip.vistas.main.VistaQuip;

/**
 * Created by dam on 08/11/2016.
 */

public class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "index";
    private static AdaptadorNota adapt;
    private static PresentadorQuip present;

    public PlaceholderFragment() {
    }

    public static PlaceholderFragment newInstance(int position, AdaptadorNota adaptador, PresentadorQuip presentador) {

        // Instantiate a new fragment
        PlaceholderFragment fragment = new PlaceholderFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager linear = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linear);
        recyclerView.setAdapter(adapt);

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