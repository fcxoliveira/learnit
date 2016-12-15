package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.infra.negocio.TagNegocio;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;


public class AulaProfessorActivity extends AppCompatActivity {
    private TextView  textView7,textView101, textView100, textView102,textView108;
    private TextView tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula_professor);
        Session.setContext(getApplicationContext());
        findEditableItens();
        Aula aula = Session.getAula();
        editItens(aula);

    }

    private String exibirTags(){
        String tgs = "";
        TagNegocio tagNegocio = new TagNegocio();
        ArrayList<Tag> arrayTags = tagNegocio.retornarTagsAula(Session.getAula().getId());
        for (int i = 0; i< arrayTags.size(); i++){
            if (i == arrayTags.size()-1){
                tgs += arrayTags.get(i).getTitulo();
                break;
            }
            tgs += arrayTags.get(i).getTitulo() + ", ";
        }
        return tgs;
    }

    private void editItens(Aula aula) {
        textView101.setText(aula.getHoras()+"");
        textView100.setText(aula.getTitulo());
        textView7.setText(aula.getTitulo());
        textView102.setText(aula.getDescricao());
        textView108.setText(aula.getValor()+"");
        tags.setText(exibirTags());
    }

    private void findEditableItens() {
        textView7 = (TextView)findViewById(R.id.textViewNome);
        textView101 = (TextView)findViewById(R.id.textView101);
        textView100 = (TextView) findViewById(R.id.textView100);
        textView102=(TextView) findViewById(R.id.textView102);
        textView108=(TextView) findViewById(R.id.textView8);
    }
    public Aula retornarAula(int id){
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        return gerenciadorAulasTutor.retornarAula(id);
    }

    public void editarAula(View view){
        Intent secondActivity = new Intent(this, EditarAulaActivity.class);
        startActivity(secondActivity);
        finish();
    }

    public void confirmarAula(View view){
        Intent secondActivity = new Intent(this, ConfirmarAulaProfessorActivity.class);
        startActivity(secondActivity);
        finish();
    }

    public void mostrarAlunos(View view){
        Intent secondActivity = new Intent(this, AlunosCadastradosActivity.class);
        startActivity(secondActivity);
        finish();
    }

    @Override
    public void onBackPressed(){
        chamarHome();
    }

    public void chamarHome() {
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        finish();
    }
}