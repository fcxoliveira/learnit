package ufrpe.edu.learnit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextLogin,editTextEmail,editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        buttonRegister = (Button)findViewById(R.id.button);
        editTextLogin = (EditText)findViewById(R.id.editText);
        editTextEmail = (EditText)findViewById(R.id.editText3);
        editTextPassword = (EditText)findViewById(R.id.editText2);

    }

    public boolean verificarEmail(String email){
        Context context = getApplicationContext();
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(context, "Email é um campo necessario", Toast.LENGTH_LONG).show();
            return false;
        }else{
            if( Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
            }else {
                Toast.makeText(context, "Email em formato invalido", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
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

    public void cadastrar(View v){
        Context context = getApplicationContext();
        String login = editTextLogin.getText().toString();
        String email = editTextEmail.getText().toString();
        String senha = editTextPassword.getText().toString();
        if(verificarLogin(login)){
            if(verificarSenha(senha)){
                if(verificarEmail(email)){
                    LoginDataBaseAdapter adapter = new LoginDataBaseAdapter(context);
                    adapter.open();
                    adapter.insertEntry(login,senha,email);
                    adapter.close();
                }
            }

        }
    }
}
