package ufrpe.edu.learnit.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import ufrpe.edu.learnit.R;


import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;

public class InteressesActivity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_perfil);
        Session.setContext(getApplicationContext());
        findEditableItens();
    }

    private void findEditableItens() {
        editTextNome = (EditText) findViewById(R.id.editText5);
        editTextBio = (EditText) findViewById(R.id.editTextDescricao);
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
            PerfilNegocio perfilNegocio = new PerfilNegocio();
            perfilNegocio.cadastrarPerfil(Session.getUsuario().getID(), editTextBio.getText().toString(), editTextNome.getText().toString());
            chamarHome(v);
        }
    }

}

