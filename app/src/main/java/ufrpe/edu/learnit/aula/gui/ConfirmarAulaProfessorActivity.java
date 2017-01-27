package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.adaptersDoProjeto.ListaAlunoCheckboxAdapter;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.confirmacao.negocio.ConfirmacaoNegocio;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class ConfirmarAulaProfessorActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ListView listView;
    private EditText editText;
    private ArrayList<Perfil> perfis;
    private ArrayList<String> nomesPerfis;
    private ListaAlunoCheckboxAdapter adapterAlunoCheckbox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_aula_professor);
        Session.setContext(getApplicationContext());
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        perfis = gerenciadorAulasTutor.retornarAlunosCadastrados(Session.getAula().getId());
        nomesPerfis = pegarNomesPerfil(perfis);
        editText = (EditText) findViewById(R.id.editTextHorasDadas);
        newListViewAlunosCadastrados();
        setOnItemClickListener();

    }
    private void newListViewAlunosCadastrados() {
        listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapterAlunoCheckbox =new ListaAlunoCheckboxAdapter(perfis,this);
        listView.setAdapter(adapterAlunoCheckbox);
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

        if (perfis.isEmpty())
            Toast.makeText(this, "Não é possível confirmar horas sem ter alunos", Toast.LENGTH_SHORT).show();
        ConfirmacaoNegocio confirmacaoNegocio = new ConfirmacaoNegocio();
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        int idAula= Session.getAula().getId();
        int horasParaConfirmar = Integer.parseInt(editText.getText().toString());
        for(Perfil perfil:perfis) {
            int idAluno=perfil.getId();
            if (perfil.isSelected()&& !checarConfirmaçãoPendente(perfil.getId()) && gerenciadorAulasTutor.verificarHorasDisponiveis(idAula,idAluno,horasParaConfirmar)) {
                confirmacaoNegocio.enviarConfirmacao(Session.getAula().getId(), perfil.getId(), horasParaConfirmar, 0);
                chamarHome(view);
            }
        }
    }

    public boolean checarConfirmaçãoPendente(int idAluno){
        boolean result=false;
        ConfirmacaoNegocio confirmacaoNegocio = new ConfirmacaoNegocio();
        if(confirmacaoNegocio.confirmacaoPendente(idAluno)){
            result=true;
        }
        return result;
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
