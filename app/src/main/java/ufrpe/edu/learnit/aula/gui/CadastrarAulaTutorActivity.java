package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.negocio.TagNegocio;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class CadastrarAulaTutorActivity extends AppCompatActivity {

    private EditText editTextHorasDeAula,editTextPrecoHoraAula,editTextDescricao, editTextNomeAula;
    private AutoCompleteTextView editTextTags;
    ListView listViewTags;
    ArrayList<String> tags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_aula);
        Session.setContext(getApplicationContext());
        editTextNomeAula = (EditText) findViewById(R.id.editText5);
        editTextDescricao = (EditText) findViewById(R.id.editTextDescricao);
        editTextHorasDeAula = (EditText) findViewById(R.id.editTextHotasAssistidas);
        editTextPrecoHoraAula = (EditText) findViewById(R.id.editText11);
        listViewTags = (ListView) findViewById(R.id.ListViewTags);
        editTextTags = (AutoCompleteTextView) findViewById(R.id.editTextTag);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,tags);
        listViewTags.setAdapter(adapter);
        setOnlistenerSearch();

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
        if(verificarNomeAula(nomeAula)&& verificarDescricao(descricao)&& verificarHorasDeAula(horasDeAula) && verificarPrecoHoraAula(precoHoraAula)){
            GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
            gerenciadorAulasTutor.cadastrarAula(nomeAula,descricao,Integer.parseInt(horasDeAula),Integer.parseInt(precoHoraAula));
            chamarTelaInicial(v);
        }
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

    public void populateListView(View v){
        tags.add(editTextTags.getText().toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,tags);
        listViewTags.setAdapter(adapter);
    }

    public void setOnlistenerSearch() {

        editTextTags.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TagNegocio tagNegocio = new TagNegocio();
                String text = editTextTags.getText().toString();
                ArrayList<Tag> tags = tagNegocio.retornarTagsPorTexto(text);
                ArrayAdapter<Tag> adapterSugerido = new ArrayAdapter<>(CadastrarAulaTutorActivity.this, android.R.layout.simple_list_item_1);
                adapterSugerido.addAll(tags);
                editTextTags.setAdapter(adapterSugerido);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });




    }
}