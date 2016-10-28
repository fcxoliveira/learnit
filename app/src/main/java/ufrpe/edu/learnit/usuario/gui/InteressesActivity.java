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


import ufrpe.edu.learnit.aula.dominio.Tag;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;

public class InteressesActivity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextBio;
    private Spinner tag1, tag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interesses);
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        ArrayList<Tag> tags = gerenciadorAulasTutor.retornarTodasTags();
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

    public boolean anyTag(ArrayList<Tag> tags){
        boolean anyTrue = false;
        for (Tag tag: tags ){
            if(tag.getID()!=1){
                anyTrue=true;
                return anyTrue;
            }
        }
        Toast.makeText(Session.getContext(), "selecione pelo menos um interesse", Toast.LENGTH_LONG).show();
        return anyTrue;
    }

    public void confirmar(View v){
        String nome =editTextNome.getText().toString();
        ArrayList<Tag> tags = new ArrayList<Tag>(Arrays.asList((Tag) tag1.getSelectedItem(),(Tag) tag2.getSelectedItem()));
        if(verificarNome(nome) && anyTag(tags)) {
            PerfilNegocio perfilNegocio = new PerfilNegocio();
            perfilNegocio.cadastrarPerfil(Session.getUsuario().getID(), editTextBio.getText().toString(), editTextNome.getText().toString(), tag1.getSelectedItem().toString(), tag2.getSelectedItem().toString());
            chamarHome(v);
        }
    }

}

