package ufrpe.edu.learnit;

import android.app.Dialog;
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

public class LoginActivity extends AppCompatActivity {
    Button buttonLogin;
    TextView textViewForgotPassWord,textViewSignup;
    EditText editTextLogin,editTextSenha;
    private static User user;


    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LoginActivity.user = user;
    }


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

    public void callSignUp(View view) {

        Intent secondActivity = new Intent(this, RegisterActivity.class);
        startActivity(secondActivity);
    }

    public boolean verificarLogin(String login){
        Context context = getApplicationContext();
        if(TextUtils.isEmpty(login)){
            Toast.makeText(context, "Login é um campo necessario", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    public  boolean verificarSenha(String senha){
        Context context = getApplicationContext();
        if (senha.length() <6) {
            Toast.makeText(context,"Senha fraca, minimo de 6 digitos",Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;

        }
    }

    public void logar(View v){
        Context context = getApplicationContext();
        String login = editTextLogin.getText().toString();
        String senha = editTextSenha.getText().toString();
        if(verificarLogin(login)){
            if(verificarSenha(senha)){
                    LoginDataBaseAdapter adapter = new LoginDataBaseAdapter(context);
                    adapter.open();
                    User result = adapter.getUser(login,senha);
                    setUser(result);
                    adapter.close();
                }
            }
    }

}
