package ufrpe.edu.learnit.aula.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.infra.dominio.Session;

public class AulaAlunoActivity extends AppCompatActivity {
    private TextView nomeAula, descricaoAula, horasPagas, nomeProfessor, avaliadores, nota;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Aula aula= Session.getAula();
        setContentView(R.layout.activity_aula_aluno);
        Session.setContext(getApplicationContext());
        findEditableItens();
        editItens(aula);
    }
    private void findEditableItens() {
        nomeAula = (TextView)findViewById(R.id.textViewNome);
        nomeProfessor = (TextView)findViewById(R.id.textViewNomeDoProfessor);
        avaliadores = (TextView)findViewById(R.id.textViewAvaliadores);
        nota = (TextView)findViewById(R.id.textViewRate);
        descricaoAula = (TextView)findViewById(R.id.textViewDescricao);
        horasPagas = (TextView)findViewById(R.id.textViewHoras);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar2);
    }
    private void editItens(Aula aula) {
        nomeAula.setText(aula.getTitulo());
        nomeProfessor.setText(aula.getPerfil().getNome());
        avaliadores.setText(String.valueOf(aula.getPerfil().getAvaliadores()));
        nota.setText(String.valueOf(aula.getPerfil().getAvaliadores()));
        descricaoAula.setText(aula.getDescricao());
        horasPagas.setText(String.valueOf(aula.getDuracaoHorasAula()));
        ratingBar.setRating(aula.getPerfil().getAvaliacao());
    }




}
