package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.infra.negocio.TagNegocio;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class EditarAulaActivity extends AppCompatActivity {
    EditText  editTextHorasDeAula, editTextPreco;
    TextView TextViewNomeAula,TextViewDescricao;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_aula);
        Aula aula=Session.getAula();
        Session.setContext(getApplicationContext());
        newListViewTags();
        findEditablesItens();
        editItens(aula);
    }

    private void editItens(Aula aula) {
        editTextHorasDeAula.setText(aula.getHoras()+"");
        editTextPreco.setText(aula.getValor()+"");
        TextViewDescricao.setText(aula.getDescricao());
        TextViewNomeAula.setText(aula.getTitulo());
    }


    private void findEditablesItens() {
        TextViewNomeAula = (TextView) findViewById(R.id.TextViewNomeAula);
        TextViewDescricao = (TextView) findViewById(R.id.TextViewDescricao);
        editTextHorasDeAula = (EditText) findViewById(R.id.TextViewHorasDeAula);
        editTextPreco = (EditText) findViewById(R.id.TextViewPreco);
    }
    private void newListViewTags(){
        listView=(ListView) findViewById(R.id.ListViewTags);
        TagNegocio tagNegocio = new TagNegocio();
        ArrayList<Tag> arrayTags = tagNegocio.retornarTagsAula(Session.getAula().getId());
        ArrayList<String> tagsString= new ArrayList<String>();
        for (Tag tag :arrayTags){
            tagsString.add(tag.getTitulo());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tagsString);
    }

    public boolean verificarNomeAula(String nomeAula){
        boolean autorizacao = false;
        if (TextUtils.isEmpty(nomeAula)) {
            TextViewNomeAula.requestFocus();
            TextViewNomeAula.setError("Nome da aula é um campo necessario");
        } else {
            autorizacao = true;
        }
        return autorizacao;

    }
    public boolean verificarDescricao(String descricao){
        boolean autorizacao = false;
        if (TextUtils.isEmpty(descricao)) {
            TextViewDescricao.requestFocus();
            TextViewDescricao.setError("Descrição da aula é um campo necessario");
        } else {
            autorizacao = true;
        }
        return autorizacao;

    }
    public boolean verificarHorasDeAula(String horasDeAula){
        boolean autorizacao = false;
        if (TextUtils.isEmpty(horasDeAula)) {
            editTextHorasDeAula.requestFocus();
            editTextHorasDeAula.setError("Nome da aula é um campo necessario");
        } else {
            autorizacao = true;
        }
        return autorizacao;

    }
    public boolean verificarPrecoHoraAula(String precoHoraAula){
        boolean autorizacao = false;
        if (TextUtils.isEmpty(precoHoraAula)){
            editTextPreco.requestFocus();
            editTextPreco.setError("Preço da hora/aula é um campo necessário");
        } else {
            autorizacao = true;
        }
        return autorizacao;
    }



    public void cadastrarAulaTutor(View v){
        String nomeAula = TextViewNomeAula.getText().toString();
        String descricao = TextViewDescricao.getText().toString();
        String horasDeAula = editTextHorasDeAula.getText().toString();
        String precoHoraAula = editTextPreco.getText().toString();
        if(verificarNomeAula(nomeAula)&& verificarDescricao(descricao)&& verificarHorasDeAula(horasDeAula) && verificarPrecoHoraAula(precoHoraAula)){
            GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();

            gerenciadorAulasTutor.editarAula(Session.getAula().getId(), nomeAula, descricao, Integer.parseInt(horasDeAula),Integer.parseInt(precoHoraAula));
            voltar();
        }
    }

    @Override
    public void onBackPressed(){
        voltar();
    }

    public void voltar() {
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        finish();
    }
}
