package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class CadastrarAulaActivity extends AppCompatActivity {

    private EditText editTextNomeAula;
    private EditText editTextDescricao;
    private EditText editTextHorasDeAula;
    private EditText editTextPrecoHoraAula;
    private Spinner tag1, tag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_aula);
        String[] tags = new String[]{"Informatica", "Musica", "Portugues", "Matematica", "Biologia", "Fisica", "Quimica", "Ed. Fisica"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tags);
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
    public void cadastrarAulaTutor(View v){
        String nomeAula = editTextNomeAula.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String horasDeAula = editTextHorasDeAula.getText().toString();
        String precoHoraAula = editTextPrecoHoraAula.getText().toString();

        if(verificarNomeAula(nomeAula)&& verificarDescricao(descricao)&& verificarHorasDeAula(horasDeAula) && verificarPrecoHoraAula(precoHoraAula) ){
            GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
            String tag1 = this.tag1.getSelectedItem().toString();
            String tag2 = this.tag2.getSelectedItem().toString();
            gerenciadorAulasTutor.cadastrarAula(nomeAula,descricao,Integer.parseInt(horasDeAula),Integer.parseInt(precoHoraAula), tag1,tag2);
            chamarTelaInicial(v);

        }
    }
}