package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ufrpe.edu.learnit.CustomAdapter;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class AulasOferecidasActivity extends AppCompatActivity {

    ListView listView;
    CustomAdapter adapter;
    ArrayList<Aula> aulas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas_oferecidas);
        listView = (ListView)findViewById(R.id.listViewMinhasAulas);
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        aulas = gerenciadorAulasTutor.retornarAulasOfertadas();
        adapter = new CustomAdapter(aulas, getApplicationContext());
        listView.setAdapter(adapter);
        setOnItemClickListener();
    }

    public void setOnItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object= adapter.getItem(position);
                Aula aula=(Aula)object;
                Session.setAula(aula);
                Intent secondActivity = new Intent(Session.getContext(),AulaProfessorActivity.class);
                startActivity(secondActivity);
            }
        });
    }

    @Override
    public void onBackPressed(){
        carregarHome();
    }
    public void carregarHome() {
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        finish();
    }
}
