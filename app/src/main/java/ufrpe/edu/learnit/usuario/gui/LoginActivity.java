package ufrpe.edu.learnit.usuario.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.Populador;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
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
        Session.setContext(getApplicationContext());
        Usuario usuarioLogado = this.usuarioLogado();
        try {
            backupDatabase();
        } catch (IOException e1) {
        }
        logarAuto(usuarioLogado);
        setContentView(R.layout.activity_login);
        findEditableItens();

    }

    public static void backupDatabase() throws IOException {
        //Open your local db as the input stream
        String inFileName = "/data/data/ufrpe.edu.learnit/databases/learnit.db";
        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);

        String outFileName = Environment.getExternalStorageDirectory()+"/MYDB";
        //Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer))>0){
            output.write(buffer, 0, length);
        }
        //Close the streams
        output.flush();
        output.close();
        fis.close();
    }



    private void logarAuto(Usuario usuarioLogado) {
        if(usuarioLogado != null){
            Session.setUsuario(usuarioLogado);
            View v = new View(Session.getContext());
            chamarTelaInicial(v);
        }
    }
    public void populate(View v){
        Populador populador = new Populador();
        populador.popularBancoDeDados();
        Toast toast = Toast.makeText(Session.getContext(), "Sucesso", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void findEditableItens() {
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        textViewForgotPassWord = (TextView)findViewById(R.id.textViewForgotPassword);
        textViewSignup = (TextView)findViewById(R.id.textViewSignUp);
        editTextLogin = (EditText)findViewById(R.id.editTextUsername);
        editTextSenha = (EditText)findViewById(R.id.editTextPassword);
    }

    public void chamarTelaCadastro(View view){
        Intent secondActivity = new Intent(this, RegisterActivity.class);
        startActivity(secondActivity);
        this.finish();
    }

    public void chamarTelaInicial(View view){
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        this.finish();
    }

    public boolean verificarLogin(String login){
        boolean autorizacao=false;
        if(TextUtils.isEmpty(login)){
            editTextLogin.requestFocus();
            editTextLogin.setError("Login é um campo necessario");

        }else if (login.indexOf(" ")!=-1){
            editTextLogin.requestFocus();
            editTextLogin.setError("Login não pode conter espaços");

        }else{
           autorizacao=true;
        }
        return autorizacao;
    }

    public  boolean verificarSenha(String senha){
        boolean autorizacao=false;
        if (senha.length() == 0) {
            editTextSenha.requestFocus();
            editTextSenha.setError("Senha é um campo obrigatório");
        }else if(senha.indexOf(" ")!=-1){
            editTextSenha.requestFocus();
            editTextSenha.setError("Senha não pode conter espaços");
        }else{
            autorizacao=true;
        }
        return autorizacao;
    }

    public void logar(View v){
        Context context = getApplicationContext();
        String login = editTextLogin.getText().toString();
        String senha = editTextSenha.getText().toString();
        if(verificarLogin(login) && verificarSenha(senha)){
            UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
            Usuario usuario=usuarioNegocio.retornarUsuario(login,senha);
            if (usuario == null){
                Toast.makeText(context, "Usuario ou senha incorretos", Toast.LENGTH_LONG).show();
            }else{
                Session.setUsuario(usuario);
                SessionNegocio sessionNegocio = new SessionNegocio();
                sessionNegocio.cadastrarUsuarioLogado(usuario);
                chamarTelaInicial(v);
            }
        }
    }

    public Usuario usuarioLogado(){
        SessionNegocio sessionNegociogocio = new SessionNegocio();
        return sessionNegociogocio.retornarUsuarioLogado();
    }
}