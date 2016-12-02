package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class AulasOferecidasActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<Aula> adapter;
    ArrayList<Aula> aulas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas_oferecidas);
        listView = (ListView)findViewById(R.id.listViewMinhasAulas);
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        aulas = aulaPersistencia.retornarAulasOfertadas();
        adapter = new ArrayAdapter<Aula>(this, android.R.layout.simple_list_item_1, aulas);
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
                Intent secondActivity = new Intent(Session.getContext(),ComprarAulaAlunoActivity.class);
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
