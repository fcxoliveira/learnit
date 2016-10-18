package ufrpe.edu.learnit;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

}
