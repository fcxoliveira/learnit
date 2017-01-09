package ufrpe.edu.learnit.aula.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.AlunoAula;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasAlunos;
import ufrpe.edu.learnit.confirmacao.dominio.Confirmacao;
import ufrpe.edu.learnit.infra.dominio.Session;

public class AulaAlunoActivity extends AppCompatActivity {
    private TextView nomeAula, descricaoAula, horasPagas, nomeProfessor, avaliadores, nota;
    RatingBar ratingBar;
    Button buttonConfirmar;
    Confirmacao confirmacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlunoAula aula= Session.getAlunoAula();
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
        descricaoAula = (TextView)findViewById(R.id.textViewDescricaoDaAula);
        horasPagas = (TextView)findViewById(R.id.textViewHoras);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar2);
        descricaoAula.setMovementMethod(new ScrollingMovementMethod());
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
    public void confirmar(View v){
        GerenciadorAulasAlunos gerenciadorAulasAlunos = new GerenciadorAulasAlunos();
        if (!gerenciadorAulasAlunos.existeConfirmacaoRecebida()){
            Toast.makeText(this, "Você não tem horas para confirmar", Toast.LENGTH_LONG).show();
            return;
        }else{
            confirmacao = gerenciadorAulasAlunos.retornarConfirmacaoRecebida();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Você confirma as "+confirmacao.getHorasConfirmadas()+" horas de aula, dita(s) pelo professor?");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    GerenciadorAulasAlunos gerenciadorAulasAlunos=new GerenciadorAulasAlunos();
                    gerenciadorAulasAlunos.aceitarConfirmacao(confirmacao);
                    Intent secondActivity = new Intent(getApplicationContext(),AulaAlunoActivity.class);
                    startActivity(secondActivity);
                    AulaAlunoActivity.super.finish();



                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }




}
