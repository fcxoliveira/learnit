package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ufrpe.edu.learnit.CustomAdapterPerfil;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.gui.PerfilAlheioActivity;


public class AlunosCadastradosActivity extends AppCompatActivity {

    ListView listView;
    CustomAdapterPerfil adapter;
    TextView nomeDaAula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos_cadastrados);
        Session.setContext(getApplicationContext());
        Aula aula=Session.getAula();
        int idAula = Session.getAula().getId();
        listView = (ListView) findViewById(R.id.listViewAlunosCadastrados);
        newTextViewNomeDaAula(aula);
        newListViewAlunosCadastrados(idAula);
    }

    private void newListViewAlunosCadastrados(int idAula) {
        ArrayList<Perfil> perfis =getAlunosCadastrados(idAula);
        adapter = new CustomAdapterPerfil(perfis,getApplicationContext());
        listView = (ListView) findViewById(R.id.listViewAlunosCadastrados);
        listView.setAdapter(adapter);
        setOnItemClickListener();
    }

    private void newTextViewNomeDaAula(Aula aula) {
        nomeDaAula = (TextView) findViewById(R.id.textViewNomeDaAula);
        nomeDaAula.setText(aula.getTitulo());
    }

    private ArrayList<Perfil> getAlunosCadastrados(int idAula) {
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        ArrayList<Perfil> alunos = gerenciadorAulasTutor.retornarAlunosCadastrados(idAula);
        return alunos;
    }

    public void setOnItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object= adapter.getItem(position);
                Perfil perfil=(Perfil) object;
                Session.setPerfilAlheio(perfil);
                Intent secondActivity = new Intent(Session.getContext(),PerfilAlheioActivity.class);
                startActivity(secondActivity);
                AlunosCadastradosActivity.super.finish();
            }
        });
    }

    public void onBackPressed(){
        chamarAulaProfessor();
    }

    public void chamarAulaProfessor() {
        Intent secondActivity = new Intent(this, AulaProfessorActivity.class);
        startActivity(secondActivity);
        this.finish();
    }
}