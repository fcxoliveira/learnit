package ufrpe.edu.learnit.usuario.gui;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import ufrpe.edu.learnit.infra.Populador;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.gui.AulasCompradasActivity;
import ufrpe.edu.learnit.aula.gui.AulasOferecidasActivity;
import ufrpe.edu.learnit.aula.gui.CadastrarAulaTutorActivity;
import ufrpe.edu.learnit.aula.gui.ComprarAulaAlunoActivity;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasAlunos;
import ufrpe.edu.learnit.infra.adaptersDoProjeto.HomeAdapter;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.gui.PerfilActivity;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;
import ufrpe.edu.learnit.rating.negocio.RatingNegocio;
import ufrpe.edu.learnit.recomendacao.Recomendacao;
import ufrpe.edu.learnit.tag.negocio.TagNegocio;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    EditText editText;
    HomeAdapter adapter;
    NavigationView navigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    Toolbar toolbar;
    Recomendacao recomendacao = new Recomendacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Session.setContext(getApplicationContext());
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        Session.getUsuario().setPerfil(perfilNegocio.retornarPerfil(Session.getUsuario().getID()));
        newListViewHome();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        preencherMoedas();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        createButtonnavBar();
    }


    private void createButtonnavBar() {
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }






    private void newListViewHome() {
        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText6);
        ArrayList<Aula> values = recomendar(Session.getUsuario().getID());
        adapter = new HomeAdapter(values, getApplicationContext());
        listView.setAdapter(adapter);
        setOnItemClickListener();
        setOnlistenerSearch();
    }

    public void preencherMoedas() {
        Menu menu = navigationView.getMenu();
        MenuItem coins = menu.findItem(R.id.moedas);
        String moedas = String.valueOf(Session.getUsuario().getPerfil().getMoedas());
        coins.setTitle(moedas+" Moedas");
    }


    public void setOnItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object= adapter.getItem(position);
                Aula aula=(Aula)object;
                Session.setAula(aula);
                TagNegocio tagNegocio = new TagNegocio();
                tagNegocio.inserirRelacaoRecomendacao(tagNegocio.retornarTagsAula(aula.getId()),Session.getUsuario().getID());
                Intent secondActivity = new Intent(Session.getContext(),ComprarAulaAlunoActivity.class);
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

    public void fazerLogoff(){
        SessionNegocio sessionNegocio = new SessionNegocio();
        sessionNegocio.deslogarUsuario();
        Session.setUsuario(null);
        Intent secondActivity = new Intent(this, LoginActivity.class);
        startActivity(secondActivity);
        this.finish();
    }

    public void chamarPerfil(){
        Intent secondActivity = new Intent(this, PerfilActivity.class);
        startActivity(secondActivity);
        finish();
    }

    public void chamarCadastrarAula(){
        Intent secondActivity = new Intent(this, CadastrarAulaTutorActivity.class);
        startActivity(secondActivity);
        finish();
    }

    public ArrayList<Aula> getValoresListView() {
        GerenciadorAulasAlunos gerenciadorAulasAlunos = new GerenciadorAulasAlunos();
        return gerenciadorAulasAlunos.getAulasPorTexto("",Session.getUsuario().getID());
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
                HomeAdapter adapter;
                ArrayList<Aula> aulas = gerenciadorAulasAlunos.getAulasPorTexto(text,Session.getUsuario().getID());
                adapter = new HomeAdapter(aulas, getApplicationContext());
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
        if (id == R.id.moedas) {
            chamarMoedas();

        } else if (id == R.id.perfil) {
            chamarPerfil();

        } else if (id == R.id.cadastrarAula) {
            chamarCadastrarAula();
        } else if (id == R.id.aulasCompradas) {
            Intent secondActivity = new Intent(this, AulasCompradasActivity.class);
            startActivity(secondActivity);
            finish();
        } else if (id == R.id.aulasOferecidas) {
            Intent secondActivity = new Intent(this, AulasOferecidasActivity.class);
            startActivity(secondActivity);
            finish();
        } else if (id == R.id.chat) {

        } else if (id == R.id.sair) {
            fazerLogoff();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void chamarMoedas() {
        Intent secondActivity = new Intent(this, CoinsActivity.class);
        startActivity(secondActivity);
        finish();
    }
    public ArrayList<Aula> recomendar(int idPerfil){
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        ArrayList<Perfil> perfils = perfilNegocio.retornarTodosOsPerfis();
        Map<Perfil,Map<Perfil, Float>> DadosUsuario = new HashMap<>();
        HashMap <Perfil, Float> avaliacaoUsuario = new HashMap<>();
            for (Perfil usuario : perfils) {
                RatingNegocio ratingNegocio = new RatingNegocio();
                ArrayList<Perfil> avaliacaoTemp = ratingNegocio.retornarTodasAvaliacoesPerfil(idPerfil);
                for(Perfil perfil :avaliacaoTemp) {
                    float avaliacao = ratingNegocio.retornarAvaliacaoPerfil(usuario.getId(), perfil.getId());
                    avaliacaoUsuario.put(perfil, avaliacao);
                }
                DadosUsuario.put(usuario,avaliacaoUsuario);
            }
        Map<Float, Perfil> recomendacoes =recomendacao.predizer(avaliacaoUsuario,DadosUsuario);
        Map<Float, Perfil> treeMap = new TreeMap<>(
                new Comparator<Float>() {

                    @Override
                    public int compare(Float o1, Float o2) {
                        return o2.compareTo(o1);
                    }

                });
        treeMap.putAll(recomendacoes);
        ArrayList<Perfil> list = new ArrayList<>(treeMap.values());
        ArrayList<Aula> resultado = new ArrayList<>();
        ArrayList<Aula> suporte = new ArrayList<>();
        ArrayList<Aula> aulas = getValoresListView();
        boolean teste;
        for(Aula aula:aulas){
            teste =false;
            for(Perfil perfil:list){
                if(aula.getPerfil().getId()==perfil.getId()) {
                    resultado.add(aula);
                    teste = true;
                }
            }
            if (!teste) {
                suporte.add(aula);
            }
        }

        resultado.addAll(suporte);
        return resultado;


    }


}