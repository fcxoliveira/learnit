package ufrpe.edu.learnit.usuario.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.CustomAdapter;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.gui.AlunoInscreverEmAulaActivity;
import ufrpe.edu.learnit.aula.gui.CadastrarAulaTutorActivity;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasAlunos;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.perfil.gui.PerfilActivity;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    EditText editText;
    CustomAdapter adapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Session.setContext(getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText6);
        ArrayList<Aula> values = getValoresListView();
        adapter = new CustomAdapter(values, getApplicationContext());
        setOnItemClickListener();
        listView.setAdapter(adapter);
        setOnlistenerSearch();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void setOnItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = adapter.getItem(position);
                Aula aula = (Aula) object;
                Session.setAula(aula);
                Intent secondActivity = new Intent(Session.getContext(), AlunoInscreverEmAulaActivity.class);
                startActivity(secondActivity);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

    }

    public void fazerLogoff(View v) {
        SessionNegocio sessionNegocio = new SessionNegocio();
        sessionNegocio.deslogarUsuario();
        Session.setUsuario(null);
        Intent secondActivity = new Intent(this, LoginActivity.class);
        startActivity(secondActivity);
        this.finish();
    }

    public void chamarPerfil(View view) {
        Intent secondActivity = new Intent(this, PerfilActivity.class);
        startActivity(secondActivity);
    }

    public void chamarCadastrarAula(View view) {
        Intent secondActivity = new Intent(this, CadastrarAulaTutorActivity.class);
        startActivity(secondActivity);
    }

    public ArrayList<Aula> getValoresListView() {
        GerenciadorAulasAlunos gerenciadorAulasAlunos = new GerenciadorAulasAlunos();
        ArrayList<Aula> aulas = gerenciadorAulasAlunos.getTodasAulasOfertadas();
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
                ArrayList<Aula> aulas = gerenciadorAulasAlunos.getAulasPorTexto(text);
                adapter = new CustomAdapter(aulas, getApplicationContext());
                listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Toast.makeText(this, "Minhas aulas selecionado", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {}

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


    }

