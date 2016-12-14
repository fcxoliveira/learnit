package ufrpe.edu.learnit.perfil.gui;

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
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.infra.negocio.TagNegocio;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;

public class EditarPerfilActivity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextBio;
    private Spinner tag1, tag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        SessionNegocio sessionNegocio = new SessionNegocio();
        int idUsuario = sessionNegocio.retornarUsuarioLogado().getID();
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        Perfil perfil = perfilNegocio.retornarPerfil(idUsuario);
        TagNegocio tagNegocio = new TagNegocio();
        ArrayList<Tag> tags = tagNegocio.retornarTodasTags();
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tags);
        tag1 = (Spinner) findViewById(R.id.spinner);
        tag1.setAdapter(adapter);
        tag2 = (Spinner) findViewById(R.id.spinner2);
        tag2.setAdapter(adapter);
        tag1.setSelection((perfil.getInteresses().get(0).getID())-1);
        tag2.setSelection((perfil.getInteresses().get(1).getID())-1);
        Session.setContext(getApplicationContext());
        editTextNome = (EditText) findViewById(R.id.editText5);
        editTextBio = (EditText) findViewById(R.id.editTextDescricao);
        editTextNome.setText(perfil.getNome());
        editTextBio.setText(perfil.getBio());

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

    public boolean anyTagNotIsEmpty(ArrayList<Tag> tags) {
        boolean autorizacao = ufrpe.edu.learnit.infra.negocio.TagNegocio.anyTagNotIsEmpty(tags);
        if (autorizacao) {
            return autorizacao;
        }
        Toast.makeText(Session.getContext(), "selecione pelo menos um interesse", Toast.LENGTH_LONG).show();
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
            chamarPerfil(v);
        }
    }
    @Override
    public void onBackPressed() {
        Intent activity = new Intent(this,PerfilActivity.class);
        startActivity(activity);
        this.finish();
    }

}

