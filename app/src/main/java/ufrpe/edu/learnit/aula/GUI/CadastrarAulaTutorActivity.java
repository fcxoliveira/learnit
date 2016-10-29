package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.negocio.TagNegocio;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class CadastrarAulaTutorActivity extends AppCompatActivity {

    private EditText editTextNomeAula;
    private EditText editTextDescricao;
    private EditText editTextHorasDeAula;
    private EditText editTextPrecoHoraAula;
    private Spinner tag1, tag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_aula);
        TagNegocio tagNegocio = new TagNegocio();
        ArrayList<Tag> tags = tagNegocio.retornarTodasTags();
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,tags);
        tag1 = (Spinner) findViewById(R.id.spinner);
        tag1.setAdapter(adapter);
        tag2 = (Spinner) findViewById(R.id.spinner2);
        tag2.setAdapter(adapter);
        Session.setContext(getApplicationContext());
        editTextNomeAula = (EditText) findViewById(R.id.editText5);
        editTextDescricao = (EditText) findViewById(R.id.editText4);
        editTextHorasDeAula = (EditText) findViewById(R.id.editText12);
        editTextPrecoHoraAula = (EditText) findViewById(R.id.editText11);
    }
    public void chamarTelaInicial(View view) {
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        this.finish();
    }
    public boolean verificarNomeAula(String nomeAula){
        boolean autorizacao = false;
        if (TextUtils.isEmpty(nomeAula)) {
            editTextNomeAula.requestFocus();
            editTextNomeAula.setError("Nome da aula é um campo necessario");
        } else {
            autorizacao = true;
        }
        return autorizacao;

    }
    public boolean verificarDescricao(String descricao){
        boolean autorizacao = false;
        if (TextUtils.isEmpty(descricao)) {
            editTextDescricao.requestFocus();
            editTextDescricao.setError("Descrição da aula é um campo necessario");
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
            editTextPrecoHoraAula.requestFocus();
            editTextPrecoHoraAula.setError("Preço da hora/aula é um campo necessário");
        } else {
            autorizacao = true;
        }
        return autorizacao;
    }

    public boolean anyTagNot1(ArrayList<Tag> tags) {
        boolean autorizacao = TagNegocio.anyTagNotIsEmpty(tags);
        if (autorizacao) {
            return autorizacao;
        }
        Toast.makeText(Session.getContext(), "selecione pelo menos uma tag para aula", Toast.LENGTH_LONG).show();
        return autorizacao;
    }

    public void cadastrarAulaTutor(View v){
        String nomeAula = editTextNomeAula.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String horasDeAula = editTextHorasDeAula.getText().toString();
        String precoHoraAula = editTextPrecoHoraAula.getText().toString();
        ArrayList<Tag> tags = new ArrayList<Tag>(Arrays.asList((Tag) tag1.getSelectedItem(),(Tag) tag2.getSelectedItem()));

        if(verificarNomeAula(nomeAula)&& verificarDescricao(descricao)&& verificarHorasDeAula(horasDeAula) && verificarPrecoHoraAula(precoHoraAula) && anyTagNot1(tags)){
            GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
            Tag tag1 =(Tag)this.tag1.getSelectedItem();
            Tag tag2 =(Tag) this.tag2.getSelectedItem();
            gerenciadorAulasTutor.cadastrarAula(nomeAula,descricao,Integer.parseInt(horasDeAula), tag1,tag2,Integer.parseInt(precoHoraAula));
            chamarTelaInicial(v);
        }
}
}