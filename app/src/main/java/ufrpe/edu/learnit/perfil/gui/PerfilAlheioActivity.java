package ufrpe.edu.learnit.perfil.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.gui.AlunosCadastradosActivity;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.perfil.dominio.Perfil;

/**
 * Created by matheusc on 08/12/16.
 */

public class PerfilAlheioActivity extends AppCompatActivity{
    private TextView textViewAulaOferecida1, textViewAulaOferecida2;
    private TextView textViewRate, textViewAvaliadores, textViewHoras, textViewNome, textViewBiografia ;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alheio);
        Session.setContext(getApplicationContext());
        gerarItens();
        Perfil perfil = Session.getPerfilAlheio();
        String horas = new StringBuilder().append(perfil.getHoras()).toString();
        textViewHoras.setText(horas);
        textViewRate.setText(String.format("Nota "+"%.01f" , perfil.getAvaliacao()));
        String avaliadores = new StringBuilder().append(perfil.getAvaliadores()).toString();
        textViewAvaliadores.setText("Avaliadores " +avaliadores);
        textViewNome.setText(perfil.getNome());
        String bio = (perfil.getBio());
        textViewBiografia.setText(bio);
        ratingBar.setRating(perfil.getAvaliacao());
        ArrayList<Tag> interesses = perfil.getInteresses();
        Tag interesse1 = interesses.get(0);
        Tag interesse2 = interesses.get(1);
        if (verificarTag(interesse1)){textViewAulaOferecida1.setText(interesse1.getTitulo());}else{textViewAulaOferecida1.setText("        ");}
        if (verificarTag(interesse2)){textViewAulaOferecida2.setText(interesse2.getTitulo());}else{textViewAulaOferecida2.setText("        ");}

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

    public boolean verificarTag(Tag tag){
        Boolean result = true;
        if(tag.getID()==1){
            result = false;
        }
        return result;
    }

    public void onBackPressed(){
        chamarAlunosCadastrados();
    }

    public void chamarAlunosCadastrados() {
        Intent secondActivity = new Intent(this, AlunosCadastradosActivity.class);
        startActivity(secondActivity);
        finish();
    }

}