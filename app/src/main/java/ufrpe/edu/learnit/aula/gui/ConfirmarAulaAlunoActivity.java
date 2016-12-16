package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasAlunos;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

/**
 * Created by matheusc on 15/12/16.
 */

public class ConfirmarAulaAlunoActivity extends AppCompatActivity {

    private TextView textViewFrase;
    String frase;
    Aula aula = Session.getAula();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_aula_aluno);
        textViewFrase = (TextView) findViewById(R.id.textViewFrase);
        frase = construirFrase();
        textViewFrase.setText(frase);
    }

    private String construirFrase(){
        String string;
        string = aula.getPerfil().getNome()+" nos informou que você assistiu "
                +aula.getHoras()+" horas da aula "
                +aula.getTitulo()+". Esta informação é verdadeira?";
        return string;
    }

    public void chamarHome(View view){
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        finish();
    }

    public void confirmarAulaAluno(View view){
        GerenciadorAulasAlunos gerenciadorAulasAlunos = new GerenciadorAulasAlunos();
        gerenciadorAulasAlunos.descontarHoras(aula.getId());
    }
}
