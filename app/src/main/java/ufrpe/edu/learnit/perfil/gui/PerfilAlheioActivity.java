package ufrpe.edu.learnit.perfil.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.gui.AlunosCadastradosActivity;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;

/**
 * Created by matheusc on 08/12/16.
 */

public class PerfilAlheioActivity extends AppCompatActivity{
    private TextView textViewRate, textViewAvaliadores, textViewHoras, textViewNome, textViewBiografia ;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alheio);
        Session.setContext(getApplicationContext());
        Perfil perfil = Session.getPerfilAlheio();
        findEditableItens();
        editItens(perfil);

    }

    private void editItens(Perfil perfil) {
        textViewHoras.setText(perfil.getHoras()+"");
        textViewRate.setText(String.format("Nota "+"%.01f" , perfil.getAvaliacao()));
        String avaliadores = perfil.getAvaliadores()+"";
        textViewAvaliadores.setText("Avaliadores " +avaliadores);
        textViewNome.setText(perfil.getNome());
        textViewBiografia.setText(perfil.getBio());
        ratingBar.setRating(perfil.getAvaliacao());
    }

    private void findEditableItens() {
        textViewRate = (TextView)findViewById(R.id.textViewRate);
        textViewAvaliadores = (TextView)findViewById(R.id.textViewAvaliadores);
        textViewHoras = (TextView)findViewById(R.id.textViewHoras);
        textViewNome = (TextView) findViewById(R.id.textViewNome);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar2);
        textViewBiografia=(TextView) findViewById(R.id.textViewBiografia);
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
