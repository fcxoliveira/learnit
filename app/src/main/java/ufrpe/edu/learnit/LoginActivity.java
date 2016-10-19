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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        textViewForgotPassWord = (TextView)findViewById(R.id.textViewForgotPassword);
        textViewSignup = (TextView)findViewById(R.id.textViewSignUp);
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

}
