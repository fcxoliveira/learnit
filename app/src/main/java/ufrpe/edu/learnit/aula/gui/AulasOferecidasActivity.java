package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.CustomAdapter;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class AulasOferecidasActivity extends AppCompatActivity {

    ListView listView;
    CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas_oferecidas);
        newListViewAulasOferecidas();
    }

    private void newListViewAulasOferecidas() {
        listView = (ListView)findViewById(R.id.listViewMinhasAulas);
        ArrayList<Aula> aulas = getValoresListView();
        adapter = new CustomAdapter(aulas, getApplicationContext());
        listView.setAdapter(adapter);
        setOnItemClickListener();
    }

    private ArrayList<Aula> getValoresListView() {
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        ArrayList<Aula> aulas = gerenciadorAulasTutor.retornarAulasOfertadas();
        return aulas;
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
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        finish();
    }

}
