package ufrpe.edu.learnit.perfil.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Session;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Session.setContext(getApplicationContext());
    }
}
