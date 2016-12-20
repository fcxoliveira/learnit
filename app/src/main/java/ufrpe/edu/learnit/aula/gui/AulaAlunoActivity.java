package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.AlunoAula;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasAlunos;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class AulaAlunoActivity extends AppCompatActivity {
    private TextView nomeAula, descricaoAula, horasPagas, nomeProfessor, avaliadores, nota;
    RatingBar ratingBar;
    Button buttonConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlunoAula aula= Session.getAlunoAula();
        setContentView(R.layout.activity_aula_aluno);
        Session.setContext(getApplicationContext());
        GerenciadorAulasAlunos gerenciadorAulasAlunos = new GerenciadorAulasAlunos();
        if(gerenciadorAulasAlunos.existeConfirmacaoRecebida()){
            buttonConfirmar.setVisibility(View.VISIBLE);
        }
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

    private void editItens(AlunoAula aula) {
        nomeAula.setText(aula.getAula().getTitulo());
        nomeProfessor.setText("Professor:"+"\n"+aula.getAula().getPerfil().getNome());
        avaliadores.setText("Avaliadores:"+aula.getAula().getPerfil().getAvaliadores()+"");
        nota.setText("Nota:"+"\n"+aula.getAula().getPerfil().getAvaliacao()+"");
        descricaoAula.setText("Descrição:"+"\n"+aula.getAula().getDescricao());
        horasPagas.setText("Horas compradas:"+aula.getHorasTotal()+"     "+"Horas confirmadas:"+aula.getHorasConfirmadas());
        ratingBar.setRating(aula.getAula().getPerfil().getAvaliacao());
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
