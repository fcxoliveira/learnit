package ufrpe.edu.learnit.rating.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.gui.AulaAlunoActivity;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.rating.negocio.RatingNegocio;

public class RatingActivityProfessor extends AppCompatActivity {
    TextView textViewNome; TextView textViewMediaGeral; TextView textViewSuaNota;
    RatingBar ratingBarMediaGeral; RatingBar ratingBarSuaNota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_professor);
        Session.setContext(getApplicationContext());
        RatingNegocio ratingNegocio = new RatingNegocio();
        int idPerfilAvaliador = Session.getUsuario().getID();
        int idItemPerfil= Session.getAlunoAula().getAula().getPerfil().getId();
        Perfil perfilAlheio = Session.getAlunoAula().getPerfil();
        float avaliacaoIndividualPerfil = ratingNegocio.retornarAvaliacaoPerfil(idPerfilAvaliador,idItemPerfil);
        float avaliacaoMedia = AvaliacaoMediaPerfil(perfilAlheio);
        Aula aula=Session.getAlunoAula().getAula();
        findItens();
        editItens(avaliacaoIndividualPerfil, avaliacaoMedia, aula);

    }

    private void editItens(float avaliacaoIndividualPerfil, float avaliacaoMedia, Aula aula) {
        ratingBarMediaGeral.setRating(avaliacaoMedia);
        textViewNome.setText(aula.getTitulo());
        if(avaliacaoIndividualPerfil==0){
            ratingBarSuaNota.setRating(0);
        }else{
            ratingBarSuaNota.setRating(avaliacaoIndividualPerfil);
        }
    }

    private float AvaliacaoMediaPerfil(Perfil perfilAlheio) {
        float avaliacaoGeralPerfil = perfilAlheio.getAvaliacao();
        int avaliadoresPerfil = perfilAlheio.getAvaliadores();
        return avaliacaoGeralPerfil/avaliadoresPerfil;
    }

    private void findItens() {
        ratingBarMediaGeral=(RatingBar) findViewById(R.id.ratingBarMediaGeral);
        textViewNome=(TextView) findViewById(R.id.textViewNome);
        ratingBarSuaNota=(RatingBar) findViewById(R.id.ratingBarSuaNota);
    }

    public void confirmarAvalicaoProfessor(View view){
        float avaliacao = ratingBarSuaNota.getRating();
        RatingNegocio ratingNegocio = new RatingNegocio();
        int idPerfilAvaliador = Session.getUsuario().getID();
        int idItemPerfil= Session.getAlunoAula().getAula().getPerfil().getId();
        ratingNegocio.novaAvaliacaoPerfil(idPerfilAvaliador,idItemPerfil,avaliacao);
        Intent secondActivity = new Intent(this, AulaAlunoActivity.class);
        startActivity(secondActivity);
        this.finish();
    }

}
