package ufrpe.edu.learnit.usuario.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.usuario.dominio.Usuario;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.usuario.negocio.UsuarioNegocio;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    TextView textViewForgotPassWord,textViewSignup;
    EditText editTextLogin,editTextSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Context context = getApplicationContext();
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        textViewForgotPassWord = (TextView)findViewById(R.id.textViewForgotPassword);
        textViewSignup = (TextView)findViewById(R.id.textViewSignUp);
        editTextLogin = (EditText)findViewById(R.id.editTextUsername);
        editTextSenha = (EditText)findViewById(R.id.editTextPassword);
    }

    public void chamarTelaCadastro(View view){
        Intent secondActivity = new Intent(this, RegisterActivity.class);
        startActivity(secondActivity);
    }

    public void chamarTelaInicial(View view){
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
    }

    public boolean verificarLogin(String login){
        Context context = getApplicationContext();
        if(TextUtils.isEmpty(login)){
            Toast.makeText(context, "Login é um campo necessario", Toast.LENGTH_LONG).show();
            editTextLogin.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    public  boolean verificarSenha(String senha){
        Context context = getApplicationContext();
        if (senha.length() == 0) {
            Toast.makeText(context,"Senha é um campo obrigatório",Toast.LENGTH_LONG).show();
            editTextSenha.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    public void logar(View v){
        Context context = getApplicationContext();
        String login = editTextLogin.getText().toString();
        String senha = editTextSenha.getText().toString();
        if(verificarLogin(login) && verificarSenha(senha)){
            UsuarioNegocio usuarioNegocio = new UsuarioNegocio(context);
            Usuario usuario=usuarioNegocio.retornarUsuario(login,senha);
            if (usuario == null){
                Toast.makeText(context, "Usuario ou senha incorretos", Toast.LENGTH_LONG).show();
            }else{
                Session.setUsuario(usuario);
                Toast.makeText(context, "Usuario logado com sucesso", Toast.LENGTH_LONG).show();
                chamarTelaInicial(v);
            }
        }
    }
}