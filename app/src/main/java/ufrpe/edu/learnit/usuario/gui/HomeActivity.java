package ufrpe.edu.learnit.usuario.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Session;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView textView = (TextView)findViewById(R.id.textView2);
        textView.setText("Bem vindo "+ Session.getUsuario().getLogin());
        textView.setVisibility(View.VISIBLE);
        Session.setContext(getApplicationContext());
    }

    @Override
    public void onBackPressed()
    {

    }
}
