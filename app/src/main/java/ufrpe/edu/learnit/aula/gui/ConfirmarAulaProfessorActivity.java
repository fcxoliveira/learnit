package ufrpe.edu.learnit.aula.gui;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ufrpe.edu.learnit.ListaAlunoCheckboxAdapter;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.negocio.ConfirmacaoNegocio;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class ConfirmarAulaProfessorActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ListView listView;
    private EditText editText;
    private ArrayAdapter<String> adapter;
    private ArrayList<Perfil> perfis;
    private ArrayList<String> nomesPerfis;
    private ListaAlunoCheckboxAdapter lacAdapter;
    private ArrayList<Perfil> perfisMarcados;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_aula_professor);
        Session.setContext(getApplicationContext());
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        perfis = gerenciadorAulasTutor.retornarAlunosCadastrados(Session.getAula().getId());
        nomesPerfis = pegarNomesPerfil(perfis);
        listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        editText = (EditText) findViewById(R.id.editTextHorasDadas);
        setOnItemClickListener();
        lacAdapter=new ListaAlunoCheckboxAdapter(perfis,this);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(lacAdapter);
    }

    private ArrayList<String> pegarNomesPerfil(ArrayList<Perfil> perfis) {
        ArrayList<String> nomesPerfil = new ArrayList<>();
        for (int i = 0; i < perfis.size(); i++) {
            nomesPerfil.add(perfis.get(i).getNome());
        }
        return nomesPerfil;
    }

    public void setOnItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String string = ((TextView) view).getText().toString();
                if (nomesPerfis.contains(string)) {
                    nomesPerfis.remove(string);
                } else {
                    nomesPerfis.add(string);
                }
            }
        });
    }

    public void confirmar(View view) {
        ConfirmacaoNegocio confirmacaoNegocio = new ConfirmacaoNegocio();
        for(Perfil perfil:perfis) {
            if (perfil.isSelected()) {
                confirmacaoNegocio.enviarConfirmacao(Session.getAula().getId(), perfil.getId(), Integer.parseInt(editText.getText().toString()), 0);
                chamarHome(view);
            }
        }
    }

    public void chamarHome(View view) {
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int posicao = listView.getPositionForView(buttonView);
        if (posicao != listView.INVALID_POSITION) {
            Perfil perfil = perfis.get(posicao);
            perfil.setSelected(isChecked);
        }

    }
}
