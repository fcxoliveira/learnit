package ufrpe.edu.learnit.usuario.gui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ufrpe.edu.learnit.infra.Populador;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.perfil.gui.PerfilActivity;
import ufrpe.edu.learnit.usuario.dominio.Usuario;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.usuario.negocio.UsuarioNegocio;
import ufrpe.edu.learnit.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextLogin,editTextEmail,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Session.getUsuario() != null){
            Session.setUsuario(null);
        }
        findEditableItens();
        Session.setContext(getApplicationContext());

    }

    private void findEditableItens() {
        editTextLogin = (EditText)findViewById(R.id.editText);
        editTextEmail = (EditText)findViewById(R.id.editText3);
        editTextPassword = (EditText)findViewById(R.id.editText2);
    }

    public void chamarTelaInteresses(View view) {
        Intent secondActivity = new Intent(this, CadastroPerfilActivity.class);
        startActivity(secondActivity);
    }

    public boolean verificarEmail(String email){
        boolean autorizacao=false;
        if(TextUtils.isEmpty(email)) {
            editTextEmail.requestFocus();
            editTextEmail.setError("Email é um campo necessário");

        }else if ( Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            autorizacao=true;
        }else {
            editTextEmail.requestFocus();
            editTextEmail.setError("E-mail em formato inválido");
            }
        return autorizacao;
        }

    public boolean verificarLogin(String login){
        boolean autorizacao=false;
        if(TextUtils.isEmpty(login)){
            editTextLogin.requestFocus();
            editTextLogin.setError("Login é um campo necessário");
        }else if(login.indexOf(" ")!=-1) {
            editTextLogin.requestFocus();
            editTextLogin.setError("Login não pode conter espaços");
        }else{
            autorizacao=true;
        }
        return autorizacao;
    }

    public  boolean verificarSenha(String senha){
        boolean autorizacao=false;
        if (senha.length() <6) {
            editTextPassword.requestFocus();
            editTextPassword.setError("Senha fraca, minimo de 6 digitos");
        }else if(senha.indexOf(" ")!=-1) {
            editTextPassword.requestFocus();
            editTextPassword.setError("Senha não pode conter espaços");
        }else{
            autorizacao=true;
        }
        return autorizacao;
    }

    public void cadastrar(View v){
        Context context = getApplicationContext();
        String login = editTextLogin.getText().toString();
        String email = editTextEmail.getText().toString();
        String senha = editTextPassword.getText().toString();
        UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
        if(verificarLogin(login) && verificarSenha(senha) && verificarEmail(email)){
            if ((usuarioNegocio.retornarUsuario(login, senha) != null) && (usuarioNegocio.retornarUsuarioPorEmail(email) != null)){
                Toast.makeText(context, "Login ou e-mail não disponível, tente novamente!", Toast.LENGTH_LONG).show();
            }else{
                Usuario usuario = new Usuario();
                usuario.setLogin(login);
                usuario.setSenha(senha);
                usuario.setEmail(email);
                Session.setUsuario(usuario);
                chamarTelaInteresses(v);
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent secondActivity = new Intent(this, LoginActivity.class);
        startActivity(secondActivity);
        finish();
    }
}