package ufrpe.edu.learnit.perfil.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Session;

public class PerfilActivity extends AppCompatActivity {
    private TextView textViewAulaOferecida1, textViewAulaOferecida2,textViewAulaOferecida3,textViewAulaOferecida4,textViewAulaOferecida5;
    private TextView textViewRate, textViewAvaliadores, textViewHoras, textViewNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Session.setContext(getApplicationContext());
        gerarItens();
    }

    private void gerarItens() {
        textViewAulaOferecida1 = (TextView)findViewById(R.id.textViewAulaOferecida1);
        textViewAulaOferecida2 = (TextView)findViewById(R.id.textViewAulaOferecida2);
        textViewAulaOferecida3 = (TextView)findViewById(R.id.textViewAulaOferecida3);
        textViewAulaOferecida4 = (TextView)findViewById(R.id.textViewAulaOferecida4);
        textViewAulaOferecida5 = (TextView)findViewById(R.id.textViewAulaOferecida5);
        textViewRate = (TextView)findViewById(R.id.textViewRate);
        textViewAvaliadores = (TextView)findViewById(R.id.textViewAvaliadores);
        textViewHoras = (TextView)findViewById(R.id.textViewHoras);
        textViewNome = (TextView) findViewById(R.id.textViewNome);
    }


}
