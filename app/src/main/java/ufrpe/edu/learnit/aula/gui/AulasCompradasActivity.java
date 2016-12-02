package ufrpe.edu.learnit.aula.gui;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import ufrpe.edu.learnit.CustomAdapter;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasAlunos;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;


public class AulasCompradasActivity extends AppCompatActivity {
    ListView listView;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas_compradas);
        Session.setContext(getApplicationContext());
        listView = (ListView) findViewById(R.id.listViewMinhasAulas);
        ArrayList<Aula> values = getValoresListView();
        adapter = new CustomAdapter(values, getApplicationContext());
        setOnItemClickListener();
        listView.setAdapter(adapter);
    }


    public void setOnItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object= adapter.getItem(position);
                Aula aula=(Aula)object;
                Session.setAula(aula);
                Intent secondActivity = new Intent(Session.getContext(),AulaAlunoActivity.class);
                startActivity(secondActivity);
                AulasCompradasActivity.super.finish();
            }
        });
    }

    public ArrayList<Aula> getValoresListView() {
        GerenciadorAulasAlunos gerenciadorAulasAlunos = new GerenciadorAulasAlunos();
        ArrayList<Aula> aulas =  gerenciadorAulasAlunos.retornarAulasQueAlunoAssistiu();
        return aulas;
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
