package ufrpe.edu.learnit.perfil.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;

public class EditarPerfilActivity extends AppCompatActivity {
    private EditText editTextNome,editTextBio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        SessionNegocio sessionNegocio = new SessionNegocio();
        int idUsuario = sessionNegocio.retornarUsuarioLogado().getID();
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        Perfil perfil = perfilNegocio.retornarPerfil(idUsuario);
        Session.setContext(getApplicationContext());
        findEditablesItens();
        editItens(perfil);
    }

    private void editItens(Perfil perfil) {
        editTextNome.setText(perfil.getNome());
        editTextBio.setText(perfil.getBio());
    }

    private void findEditablesItens() {
        editTextNome = (EditText) findViewById(R.id.editText5);
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

