package ufrpe.edu.learnit.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.tag.dominio.Tag;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.tag.negocio.TagNegocio;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;
import ufrpe.edu.learnit.usuario.dominio.Usuario;
import ufrpe.edu.learnit.usuario.negocio.UsuarioNegocio;

public class CadastroPerfilActivity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextBio;
    AutoCompleteTextView editTextTags;
    ListView listViewTags;
    ArrayList<String> tags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_perfil);
        Session.setContext(getApplicationContext());
        findEditableItens();
        newListViewTags();
        setOnlistenerSearch();
    }

    private void newListViewTags() {
        listViewTags = (ListView) findViewById(R.id.listViewTags);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,tags);
        listViewTags.setAdapter(adapter);
    }

    private void findEditableItens() {
        editTextNome = (EditText) findViewById(R.id.EditTextNome);
        editTextBio = (EditText) findViewById(R.id.editTextDescricao);
        editTextTags = (AutoCompleteTextView) findViewById(R.id.editTextTag);
        editTextBio.setMovementMethod(new ScrollingMovementMethod());
    }

    public void chamarHome(View view) {
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        this.finish();
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


    public void confirmar(View v){
        String nome =editTextNome.getText().toString();
        if(verificarNome(nome)) {
            Usuario user = Session.getUsuario();
            UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
            usuarioNegocio.cadastrarUsuario(user.getLogin(), user.getSenha(), user.getEmail());
            Session.setUsuario(usuarioNegocio.retornarUsuario(user.getLogin(), user.getSenha()));
            SessionNegocio sessionNegocio = new SessionNegocio();
            sessionNegocio.cadastrarUsuarioLogado(Session.getUsuario());
            PerfilNegocio perfilNegocio = new PerfilNegocio();
            perfilNegocio.cadastrarPerfil(Session.getUsuario().getID(), editTextBio.getText().toString(), editTextNome.getText().toString());
            trabalharTags();
            chamarHome(v);
        }
    }

    public void populateListView(View v) {
        String tag = editTextTags.getText().toString();
        if (tags.contains(tag) || tag.equals("")) {
            editTextTags.requestFocus();
            editTextTags.setError("Este interesse já foi adicionado a este perfil ou o campo está vazio");
        }else{
            tags.add(tag);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tags);
            listViewTags.setAdapter(adapter);
            editTextTags.setText("");
        }
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
                ArrayAdapter<Tag> adapterSugerido = new ArrayAdapter<>(CadastroPerfilActivity.this, android.R.layout.simple_list_item_1);
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
            tagNegocio.inserirRelacaoTagPerfil(tagNegocio.retornarTag(tag).getID());
        }
    }

    public void onBackPressed(){
        cancelarUsuario();
        chamarTelaLogin();
    }

    private void cancelarUsuario() {
        UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
        usuarioNegocio.excluirUsuario(Session.getUsuario());
        SessionNegocio sessionNegocio = new SessionNegocio();
        sessionNegocio.deslogarUsuario();
        Session.setUsuario(null);
    }

    private void chamarTelaLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}


