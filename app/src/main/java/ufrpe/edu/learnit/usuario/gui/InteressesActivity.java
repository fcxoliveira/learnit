package ufrpe.edu.learnit.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.negocio.TagNegocio;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;

public class InteressesActivity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextBio;
    private Spinner tag1, tag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interesses);
        TagNegocio tagNegocio = new TagNegocio();
        ArrayList<Tag> tags = tagNegocio.retornarTodasTags();
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,tags);
        tag1 = (Spinner) findViewById(R.id.spinner);
        tag1.setAdapter(adapter);
        tag2 = (Spinner) findViewById(R.id.spinner2);
        tag2.setAdapter(adapter);
        Session.setContext(getApplicationContext());
        editTextNome = (EditText) findViewById(R.id.editText5);
        editTextBio = (EditText) findViewById(R.id.editText4);
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
    /*public boolean verificarIdTag( Spinner spinner){
        boolean autorizacao = false;
        Tag tag= (Tag) spinner.getSelectedItem();
        if(tag.getID()!=0){
            autorizacao = true;
        }
        return autorizacao;*/

    public boolean anyTagNot1(ArrayList<Tag> tags) {
        boolean autorizacao = ufrpe.edu.learnit.infra.negocio.TagNegocio.anyTagNotIsEmpty(tags);
        if (autorizacao) {
            return autorizacao;
        }
        Toast.makeText(Session.getContext(), "selecione pelo menos um interesse", Toast.LENGTH_LONG).show();
        return autorizacao;
    }

    public void confirmar(View v){
        String nome =editTextNome.getText().toString();
        ArrayList<Tag> tags = new ArrayList<Tag>(Arrays.asList((Tag) tag1.getSelectedItem(),(Tag) tag2.getSelectedItem()));
        if(verificarNome(nome) && anyTagNot1(tags)) {
            PerfilNegocio perfilNegocio = new PerfilNegocio();
            perfilNegocio.cadastrarPerfil(Session.getUsuario().getID(), editTextBio.getText().toString(), editTextNome.getText().toString(),(Tag) tag1.getSelectedItem(),(Tag)tag2.getSelectedItem());
            chamarHome(v);
        }
    }

}

