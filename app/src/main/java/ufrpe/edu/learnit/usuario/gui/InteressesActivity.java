package ufrpe.edu.learnit.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Tag;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;

public class InteressesActivity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextBio;
    private Spinner interesse1, interesse2, interesse3, interesse4, interesse5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interesses);
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        ArrayList<Tag> tags = gerenciadorAulasTutor.retornarTodasTags();
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,tags);
        interesse1 = (Spinner) findViewById(R.id.spinner);
        interesse1.setAdapter(adapter);
        interesse2 = (Spinner) findViewById(R.id.spinner2);
        interesse2.setAdapter(adapter);
        interesse3 = (Spinner) findViewById(R.id.spinner3);
        interesse3.setAdapter(adapter);
        interesse4 = (Spinner) findViewById(R.id.spinner4);
        interesse4.setAdapter(adapter);
        interesse5 = (Spinner) findViewById(R.id.spinner5);
        interesse5.setAdapter(adapter);
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

    public void confirmar(View v){
        String nome =editTextNome.getText().toString();
        if(verificarNome(nome)){
            PerfilNegocio perfilNegocio = new PerfilNegocio();
            perfilNegocio.cadastrarPerfil(Session.getUsuario().getID(), editTextBio.getText().toString(), editTextNome.getText().toString(), interesse1.getSelectedItem().toString(), interesse2.getSelectedItem().toString(), interesse3.getSelectedItem().toString(), interesse4.getSelectedItem().toString(), interesse5.getSelectedItem().toString());
            chamarHome(v);
        }
    }
}
