package ufrpe.edu.learnit.aula.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.AlunoAula;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasAlunos;
import ufrpe.edu.learnit.confirmacao.dominio.Confirmacao;
import ufrpe.edu.learnit.confirmacao.negocio.ConfirmacaoNegocio;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.tag.dominio.Tag;
import ufrpe.edu.learnit.tag.negocio.TagNegocio;

public class AulaAlunoActivity extends AppCompatActivity {
    private TextView nomeAula, descricaoAula, horasPagas, nomeProfessor, avaliadores, nota, avisoConfirmacao;
    RatingBar ratingBar;
    Confirmacao confirmacao;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlunoAula aula= Session.getAlunoAula();
        ConfirmacaoNegocio confirmacaoNegocio = new ConfirmacaoNegocio();
        setContentView(R.layout.activity_aula_aluno);
        Session.setContext(getApplicationContext());
        findEditableItens();
        editItens(aula);
        newListViewTags();
        if (confirmacaoNegocio.ConfirmacaoRecebida()){
            avisoConfirmacao.setVisibility(View.VISIBLE);
            avisoConfirmacao.setError("");
        }
    }
    private void newListViewTags(){
        listView=(ListView) findViewById(R.id.ListViewTags);
        TagNegocio tagNegocio = new TagNegocio();
        ArrayList<Tag> arrayTags = tagNegocio.retornarTagsAula(Session.getAula().getId());
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayTags);
        listView.setAdapter(adapter);
    }

    private void findEditableItens() {
        avisoConfirmacao=(TextView)findViewById(R.id.textViewConfimacao);
        nomeAula = (TextView)findViewById(R.id.textViewNome);
        nomeProfessor = (TextView)findViewById(R.id.textViewNomeDoProfessor);
        //avaliadores = (TextView)findViewById(R.id.textViewAvaliadores);
        nota = (TextView)findViewById(R.id.textViewRate);
        descricaoAula = (TextView)findViewById(R.id.textViewDescricaoDaAula);
        horasPagas = (TextView)findViewById(R.id.textViewHoras);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar2);
        descricaoAula.setMovementMethod(new ScrollingMovementMethod());
    }

    private void editItens(AlunoAula aula) {
        nomeAula.setText(aula.getAula().getTitulo());
        nomeProfessor.setText("Professor:"+"\n"+aula.getAula().getPerfil().getNome());
        //avaliadores.setText("Avaliadores:"+aula.getAula().getPerfil().getAvaliadores()+"");
        nota.setText("Nota:"+"\n"+aula.getAula().getPerfil().getAvaliacao()+"");
        descricaoAula.setText(aula.getAula().getDescricao());
        horasPagas.setText("Horas compradas:"+aula.getHorasTotal()+"     "+"Horas confirmadas:"+aula.getHorasConfirmadas());
        ratingBar.setRating(aula.getAula().getPerfil().getAvaliacao());
    }
    @Override
    public void onBackPressed(){
        carregarAulasCompradas();
    }

    public void comprarMaisHoras(View view){
        Intent intent = new Intent(this, ComprarAulaAlunoActivity.class);
        startActivity(intent);
        finish();
    }

    public void carregarAulasCompradas() {
        Intent secondActivity = new Intent(this, AulasCompradasActivity.class);
        startActivity(secondActivity);
        finish();
    }

    public void confirmar(View v){
        GerenciadorAulasAlunos gerenciadorAulasAlunos = new GerenciadorAulasAlunos();
        if (!gerenciadorAulasAlunos.existeConfirmacaoRecebida()){
            Toast.makeText(this, "Você não tem horas aula para confirmar", Toast.LENGTH_LONG).show();
        }else{
            confirmacao = gerenciadorAulasAlunos.retornarConfirmacaoRecebida();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Você confirma as "+confirmacao.getHorasConfirmadas()+" horas de aula, dadas pelo professor?");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    AlunoAula alunoAula= Session.getAlunoAula();
                    GerenciadorAulasAlunos gerenciadorAulasAlunos=new GerenciadorAulasAlunos();
                    gerenciadorAulasAlunos.aceitarConfirmacao(confirmacao);
                    avisoConfirmacao.setVisibility(View.INVISIBLE);
                    AlunoAula novoAlunoAula = gerenciadorAulasAlunos.retornarAlunoAula(alunoAula.getId());
                    Session.setAlunoAula(novoAlunoAula);
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
