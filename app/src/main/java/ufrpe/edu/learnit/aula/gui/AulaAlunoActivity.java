package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.AlunoAula;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class AulaAlunoActivity extends AppCompatActivity {
    private TextView nomeAula, descricaoAula, horasPagas, nomeProfessor, avaliadores, nota;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlunoAula aula= Session.getAlunoAula();
        setContentView(R.layout.activity_aula_aluno);
        Session.setContext(getApplicationContext());
        nomeAula = (TextView)findViewById(R.id.textViewNome);
        nomeProfessor = (TextView)findViewById(R.id.textViewNomeDoProfessor);
        avaliadores = (TextView)findViewById(R.id.textViewAvaliadores);
        nota = (TextView)findViewById(R.id.textViewRate);
        descricaoAula = (TextView)findViewById(R.id.textViewDescricao);
        horasPagas = (TextView)findViewById(R.id.textViewHoras);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar2);
        editItens(aula);
    }

    private void editItens(AlunoAula aula) {
        nomeAula.setText(aula.getAula().getTitulo());
        nomeProfessor.setText(aula.getAula().getPerfil().getNome());
        avaliadores.setText(String.valueOf(aula.getAula().getPerfil().getAvaliadores()));
        nota.setText(String.valueOf(aula.getAula().getPerfil().getAvaliadores()));
        descricaoAula.setText(aula.getAula().getDescricao());
        horasPagas.setText(String.valueOf(aula.getHorasTotal()));
        ratingBar.setRating(aula.getAula().getPerfil().getAvaliacao());
    }

    public void confirmarAulaAluno(View view){
        Intent intent = new Intent(this, ConfirmarAulaAlunoActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed(){
        carregarHome();
    }
    public void carregarHome() {
        Intent secondActivity = new Intent(this, AulasCompradasActivity.class);
        startActivity(secondActivity);
        finish();
    }



}
