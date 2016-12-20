package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.tag.dominio.Tag;
import ufrpe.edu.learnit.tag.negocio.TagNegocio;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class EditarAulaActivity extends AppCompatActivity {
    private EditText  editTextHorasDeAula, editTextPreco;
    private TextView TextViewNomeAula,TextViewDescricao;
    private AutoCompleteTextView editTextTags;
    private ListView listView;
    private ArrayList<String> tags = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_aula);
        Session.setContext(getApplicationContext());
        Aula aula=Session.getAula();
        listView = (ListView) findViewById(R.id.ListViewTags);
        editTextTags = (AutoCompleteTextView) findViewById(R.id.editTextTag);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,tags);
        listView.setAdapter(adapter);
        setOnlistenerSearch();
        findEditablesItens();
        editItens(aula);
        newListViewTags();
    }


    public void populateListView(View v) {
        String tag = editTextTags.getText().toString();
        if (tags.contains(tag)){
            editTextTags.requestFocus();
            editTextTags.setError("esta tag ja foi adicionada a esta aula");
        }else{
            tags.add(tag);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tags);
            listView.setAdapter(adapter);
            editTextTags.setText("");

        }
    }
    private void newListViewTags(){
        listView=(ListView) findViewById(R.id.ListViewTags);
        TagNegocio tagNegocio = new TagNegocio();
        ArrayList<Tag> arrayTags = tagNegocio.retornarTagsAula(Session.getAula().getId());
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayTags);
        listView.setAdapter(adapter);
    }


    private void editItens(Aula aula) {
        editTextHorasDeAula.setText(aula.getHoras()+"");
        editTextPreco.setText(aula.getValor()+"");
        TextViewDescricao.setText(aula.getDescricao());
        TextViewNomeAula.setText(aula.getTitulo());
    }


    private void findEditablesItens() {
        TextViewNomeAula = (TextView) findViewById(R.id.EditTextNome);
        TextViewDescricao = (TextView) findViewById(R.id.editTextDescricao);
        editTextHorasDeAula = (EditText) findViewById(R.id.TextViewHorasDeAula);
        editTextPreco = (EditText) findViewById(R.id.TextViewPreco);
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
            trabalharTags();
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
                ArrayAdapter<Tag> adapterSugerido = new ArrayAdapter<>(EditarAulaActivity.this, android.R.layout.simple_list_item_1);
                adapterSugerido.addAll(tags);
                editTextTags.setAdapter(adapterSugerido);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }
    private void trabalharTags(){
        TagNegocio tagNegocio = new TagNegocio();
        for(String tag : tags){
            if(!tagNegocio.existeTag(tag)) {
                tagNegocio.inserirTag(tag);
            }
            tagNegocio.inserirRelacaoTagAula(tagNegocio.retornarTag(tag).getID());
        }
    }








}

