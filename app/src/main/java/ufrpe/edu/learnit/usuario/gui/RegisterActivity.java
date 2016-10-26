package ufrpe.edu.learnit.usuario.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        editTextLogin = (EditText)findViewById(R.id.editText);
        editTextEmail = (EditText)findViewById(R.id.editText3);
        editTextPassword = (EditText)findViewById(R.id.editText2);
        Session.setContext(getApplicationContext());
    }

    public void chamarTelaInteresses(View view) {
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
    }

    public boolean verificarEmail(String email){
        if(TextUtils.isEmpty(email)) {
            editTextEmail.requestFocus();
            editTextEmail.setError("Email é um campo necessário");
            return false;
        }else if ( Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else {
            editTextEmail.requestFocus();
            editTextEmail.setError("E-mail em formato inválido");
            return false;
            }
        }

    public boolean verificarLogin(String login){
        if(TextUtils.isEmpty(login)){
            editTextLogin.requestFocus();
            editTextLogin.setError("Login é um campo necessário");
            return false;
        }else{
            return true;
        }
    }

    public  boolean verificarSenha(String senha){
        if (senha.length() <6) {
            editTextPassword.requestFocus();
            editTextPassword.setError("Senha fraca, minimo de 6 digitos");
            return false;
        }else{
            return true;
        }
    }

    public void cadastrar(View v){
        Context context = getApplicationContext();
        String login = editTextLogin.getText().toString();
        String email = editTextEmail.getText().toString();
        String senha = editTextPassword.getText().toString();
        if(verificarLogin(login) && verificarSenha(senha) && verificarEmail(email)){
            UsuarioNegocio negocio = new UsuarioNegocio();
            Usuario usuario = negocio.cadastrarUsuario(login, senha, email);
            if (usuario == null){
                Toast.makeText(context, "Login ou e-mail não disponível, tente novemente!", Toast.LENGTH_LONG).show();
            }else{
                Session.setUsuario(usuario);
                chamarTelaInteresses(v);
                this.finish();
            }
        }
    }
}