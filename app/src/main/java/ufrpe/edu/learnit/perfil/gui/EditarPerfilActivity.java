package ufrpe.edu.learnit.perfil.gui;

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
import ufrpe.edu.learnit.aula.gui.CadastrarAulaTutorActivity;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.infra.negocio.TagNegocio;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;

public class EditarPerfilActivity extends AppCompatActivity {

    private EditText editTextNome,editTextBio;
    private ListView listView;
    private AutoCompleteTextView editTextTags;
    ArrayList<String> tags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        SessionNegocio sessionNegocio = new SessionNegocio();
        int idUsuario = sessionNegocio.retornarUsuarioLogado().getID();
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        Perfil perfil = perfilNegocio.retornarPerfil(idUsuario);
        listView = (ListView) findViewById(R.id.ListViewTags);
        Session.setContext(getApplicationContext());
        editTextTags = (AutoCompleteTextView) findViewById(R.id.editTextTag);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,tags);
        findEditablesItens();
        editItens(perfil);
        newListViewTags();
        setOnlistenerSearch();
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
                ArrayAdapter<Tag> adapterSugerido = new ArrayAdapter<>(EditarPerfilActivity.this, android.R.layout.simple_list_item_1);
                adapterSugerido.addAll(tags);
                editTextTags.setAdapter(adapterSugerido);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }
    private void newListViewTags(){
        listView=(ListView) findViewById(R.id.ListViewTags);
        TagNegocio tagNegocio = new TagNegocio();
        ArrayList<Tag> arrayTags = tagNegocio.retornarTagsPerfil(Session.getUsuario().getID());
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayTags);
        listView.setAdapter(adapter);
    }
    private void editItens(Perfil perfil) {
        editTextNome.setText(perfil.getNome());
        editTextBio.setText(perfil.getBio());
    }

    private void findEditablesItens() {
        editTextNome = (EditText) findViewById(R.id.EditTextNome);
        editTextBio = (EditText) findViewById(R.id.editTextDescricao);
    }

    public boolean verificarNome(String nome) {
        boolean autorizacao = false;
        if (TextUtils.isEmpty(nome)) {
            editTextNome.requestFocus();
            editTextNome.setError("nome é um campo necessario");
        } else {
            autorizacao = true;
        }
        return autorizacao;
    }

    public void chamarPerfil(View view) {
        Intent secondActivity = new Intent(this, PerfilActivity.class);
        startActivity(secondActivity);
        this.finish();
    }

    public void confirmar(View v){
        String nome =editTextNome.getText().toString();
        if(verificarNome(nome)) {
            PerfilNegocio perfilNegocio = new PerfilNegocio();
            perfilNegocio.editarPerfil(Session.getUsuario().getID(), editTextBio.getText().toString(), editTextNome.getText().toString());
            trabalharTags();
            chamarPerfil(v);
        }
    }
    @Override
    public void onBackPressed() {
        Intent activity = new Intent(this,PerfilActivity.class);
        startActivity(activity);
        this.finish();
    }
    public void populateListView(View v) {
        String tag = editTextTags.getText().toString();
        if (tags.contains(tag)){
            editTextTags.requestFocus();
            editTextTags.setError("Esta tag já foi adicionada à esta aula");
        }else{
            tags.add(tag);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tags);
            listView.setAdapter(adapter);
            editTextTags.setText("");
        }
    }
    private void trabalharTags(){
        TagNegocio tagNegocio = new TagNegocio();
        for(String tag : tags){
            if(!tagNegocio.existeTag(tag)) {
                tagNegocio.inserirTag(tag);
            }
            if (!tagNegocio.existeRelacaoTagPerfil(tagNegocio.retornarTag(tag).getID())){
                tagNegocio.inserirRelacaoTagAula(tagNegocio.retornarTag(tag).getID());
            }

        }
    }

}