package com.izv.dam.newquip.vistas.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorClickJoin;
import com.izv.dam.newquip.adaptadores.AdaptadorClickLista;
import com.izv.dam.newquip.adaptadores.AdaptadorClickNota;
import com.izv.dam.newquip.adaptadores.AdaptadorJoin;
import com.izv.dam.newquip.adaptadores.AdaptadorLista;
import com.izv.dam.newquip.adaptadores.AdaptadorNota;
import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.dialogo.DialogoBorrar;
import com.izv.dam.newquip.dialogo.DialogoBorrarJoin;
import com.izv.dam.newquip.dialogo.OnBorrarDialogListener;
import com.izv.dam.newquip.dialogo.OnBorrarJoinDialogListener;
import com.izv.dam.newquip.pojo.Join;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.vistas.Tabbed.FragmentListas;
import com.izv.dam.newquip.vistas.Tabbed.FragmentNotas;
import com.izv.dam.newquip.vistas.Tabbed.FragmentUnion;
import com.izv.dam.newquip.vistas.listas.VistaLista;
import com.izv.dam.newquip.vistas.notas.VistaNota;


public class VistaQuip extends AppCompatActivity implements ContratoMain.InterfaceVista , OnBorrarDialogListener, AdaptadorClickNota, AdaptadorClickLista, AdaptadorClickJoin, OnBorrarJoinDialogListener {

    private AdaptadorNota adaptador;
    private AdaptadorJoin adaptadorJ;
    private AdaptadorLista adaptadorL;
    private PresentadorQuip presentador;

    //tabbed
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quip);

        presentador = new PresentadorQuip(this);

        adaptador = new AdaptadorNota(this, null, this);
        adaptadorJ = new AdaptadorJoin(this, null, this);
        adaptadorL = new AdaptadorLista(this, null, this);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //FloatingButtonMenu

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                presentador.onAddNota();
                materialDesignFAM.close(true);

            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                presentador.onAddLista();
                materialDesignFAM.close(true);
            }
        });


    }


    //MENU PRINCIPAL
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //FIN MENU PRINCIPAL

    @Override
    protected void onPause() {
        presentador.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        presentador.onResume();
        super.onResume();
    }

    @Override
    public void mostrarAgregarLista() {
        Toast.makeText(VistaQuip.this, "add Lista", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, VistaLista.class);
        startActivity(i);
    }

    @Override
    public void mostrarAgregarNota() {
        Toast.makeText(VistaQuip.this, "add Nota", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, VistaNota.class);
        startActivity(i);
    }

    @Override
    public void mostrarDatosN(Cursor c) {
        adaptador.changeCursor(c);
    }

    @Override
    public void mostrarDatosL(Cursor c) {
        adaptadorL.changeCursorL(c);
    }

    @Override
    public void mostrarDatosJ(Cursor c) {
        adaptadorJ.changeCursorJ(c);
    }

    @Override
    public void mostrarEditarNota(Nota n) {
        Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, VistaNota.class);
        Bundle b = new Bundle();
        b.putParcelable("nota", n);
        i.putExtras(b);
        startActivity(i);
    }

    @Override
    public void mostrarEditarLista(Lista l) {
        Toast.makeText(VistaQuip.this, "edit", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, VistaLista.class);
        Bundle b = new Bundle();
        b.putParcelable("lista", l);
        i.putExtras(b);
        startActivity(i);
    }

    @Override
    public void mostrarConfirmarBorrarNota(Nota n) {
        DialogoBorrar fragmentBorrar = DialogoBorrar.newInstance(n);
        fragmentBorrar.show(getSupportFragmentManager(), "Dialogo borrar");
    }

    @Override
    public void mostrarConfirmarBorrarLista(Lista l) {
        DialogoBorrar fragmentBorrar = DialogoBorrar.newInstance(l);
        fragmentBorrar.show(getSupportFragmentManager(), "Dialogo borrar");
    }

    @Override
    public void mostrarConfirmarBorrarJoin(Join n) {
        DialogoBorrarJoin fragmentBorrar = DialogoBorrarJoin.newInstance(n);
        fragmentBorrar.show(getSupportFragmentManager(), "Dialogo Join borrar");
    }

    @Override
    public void onBorrarPossitiveButtonClick(Nota n) {
        presentador.onDeleteNota(n);
    }

    @Override
    public void onBorrarNegativeButtonClick() {

    }

    @Override
    public void onBorrarPossitiveButtonClickL(Lista l) {
        presentador.onDeleteLista(l);
    }

    @Override
    public void onBorrarNegativeButtonClickJ() {

    }

    @Override
    public void onBorrarPossitiveButtonClickJ(Join l) {
        presentador.onDeleteJoin(l);
    }

    @Override
    public void onItemClickListenerN(int pos) {
        presentador.onEditNota(pos);
    }

    @Override
    public void onItemClickListenerL(int pos) {
        presentador.onEditLista(pos);
    }

    @Override
    public void onItemLongClickListenerL(int pos) {
        presentador.onShowBorrarLista(pos);
    }

    @Override
    public void onItemLongClickListenerN(int pos) {
        presentador.onShowBorrarNota(pos);
    }

    @Override
    public void onItemClickListenerJ(int pos) {
        presentador.onEditJoin(pos);
    }

    @Override
    public void onItemLongClickListenerJ(int pos) {
        presentador.onShowBorrarJoin(pos);
    }

    //tab


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a FragmentNotas (defined as a static inner class below).
            switch (position) {
                case 0:
                    return FragmentUnion.newInstance(position + 1, adaptadorJ, presentador);
                case 1:
                    return FragmentNotas.newInstance(position + 1, adaptador, presentador);
                case 2:
                    return FragmentListas.newInstance(position + 1, adaptadorL, presentador);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "TODO";
                case 1:
                    return "NOTA";
                case 2:
                    return "LISTA";
            }
            return null;
        }
    }

}