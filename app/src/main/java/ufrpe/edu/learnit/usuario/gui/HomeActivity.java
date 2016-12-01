package ufrpe.edu.learnit.usuario.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import ufrpe.edu.learnit.CustomAdapter;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.gui.CadastrarAulaTutorActivity;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasAlunos;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.perfil.gui.PerfilActivity;

public class HomeActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;
    private ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Session.setContext(getApplicationContext());
        listView = (ListView)findViewById(R.id.listView);
        editText = (EditText)findViewById(R.id.editText6);
        ArrayList<Aula> values = getValoresListView();
        CustomAdapter adapter= new CustomAdapter(values,getApplicationContext());
        listView.setAdapter(adapter);
        setOnlistenerSearch();
        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        setupDrawer();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tem certeza que quer sair do APP?");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                HomeActivity.super.finish();
            }
    });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void fazerLogoff(View v){
        SessionNegocio sessionNegocio = new SessionNegocio();
        sessionNegocio.deslogarUsuario();
        Session.setUsuario(null);
        Intent secondActivity = new Intent(this, LoginActivity.class);
        startActivity(secondActivity);
        this.finish();
    }

    public void chamarPerfil(View view){
        Intent secondActivity = new Intent(this, PerfilActivity.class);
        startActivity(secondActivity);
    }

    public void chamarCadastrarAula(View view){
        Intent secondActivity = new Intent(this, CadastrarAulaTutorActivity.class);
        startActivity(secondActivity);
    }

    public ArrayList<Aula> getValoresListView() {
        GerenciadorAulasAlunos gerenciadorAulasAlunos = new GerenciadorAulasAlunos();
        ArrayList<Aula> aulas =  gerenciadorAulasAlunos.getTodasAulasOfertadas();
        return aulas;
    }

    public void setOnlistenerSearch() {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                GerenciadorAulasAlunos gerenciadorAulasAlunos = new GerenciadorAulasAlunos();
                String text = editText.getText().toString();
                CustomAdapter adapter;
                if (text != "") {
                    ArrayList<Aula> aulas = gerenciadorAulasAlunos.getAulasPorTexto(text);
                    adapter = new CustomAdapter(aulas, getApplicationContext());

                } else {
                    ArrayList<Aula> values = getValoresListView();
                    adapter = new CustomAdapter(values, getApplicationContext());
                }

                listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        }
    private void addDrawerItems() {
        String[] osArray = { "Android", "iOS", "Windows", "OS X", "Linux" };
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);

    }
}
