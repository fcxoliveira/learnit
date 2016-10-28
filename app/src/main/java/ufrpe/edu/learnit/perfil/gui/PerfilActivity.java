package ufrpe.edu.learnit.perfil.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;

public class PerfilActivity extends AppCompatActivity {
    private TextView textViewAulaOferecida1, textViewAulaOferecida2;
    private TextView textViewRate, textViewAvaliadores, textViewHoras, textViewNome, textViewBiografia ;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Session.setContext(getApplicationContext());
        gerarItens();
        Perfil perfil = retornarPerfil(Session.getUsuario().getID());
        String horas = new StringBuilder().append(perfil.getHoras()).toString();
        textViewHoras.setText(horas);
        textViewRate.setText(String.format("Nota "+"%.01f" , perfil.getAvaliacao()));
        String avaliadores = new StringBuilder().append(perfil.getAvaliadores()).toString();
        textViewAvaliadores.setText("Avaliadores " +avaliadores);
        textViewNome.setText(perfil.getNome());
        String bio = (perfil.getBio());
        textViewBiografia.setText(bio);
        ratingBar.setRating(perfil.getAvaliacao());
        textViewAulaOferecida1.setText(perfil.getInteresses().get(0));
        textViewAulaOferecida2.setText(perfil.getInteresses().get(1));
    }

    private void gerarItens() {
        textViewAulaOferecida1 = (TextView)findViewById(R.id.textViewAulaOferecida1);
        textViewAulaOferecida2 = (TextView)findViewById(R.id.textViewAulaOferecida2);
        textViewRate = (TextView)findViewById(R.id.textViewRate);
        textViewAvaliadores = (TextView)findViewById(R.id.textViewAvaliadores);
        textViewHoras = (TextView)findViewById(R.id.textViewHoras);
        textViewNome = (TextView) findViewById(R.id.textViewNome);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar2);
        textViewBiografia=(TextView) findViewById(R.id.textViewBiografia);
    }

    public Perfil retornarPerfil(int id){
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        return perfilNegocio.retornarPerfil(id);
    }

    public void chamarEditarPerfil(View view){
        Intent secondActivity = new Intent(this, EditarPerfil.class);
        startActivity(secondActivity);
    }


}
