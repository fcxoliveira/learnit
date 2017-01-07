package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.tag.dominio.Tag;
import ufrpe.edu.learnit.tag.negocio.TagNegocio;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

import static ufrpe.edu.learnit.aula.gui.ComprarAulaAlunoActivity.aula;


public class AulaProfessorActivity extends AppCompatActivity {
    private TextView   textViewHoras, textViewTitulo, textViewDescricaoAula, textViewPrecoAula;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula_professor);
        Session.setContext(getApplicationContext());
        aula = Session.getAula();
        findEditableItens();
        editItens();
        newListViewTags();
    }

    private void newListViewTags(){
        listView=(ListView) findViewById(R.id.ListViewTags);
        TagNegocio tagNegocio = new TagNegocio();
        ArrayList<Tag> arrayTags = tagNegocio.retornarTagsAula(Session.getAula().getId());
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayTags);
        listView.setAdapter(adapter);
    }

    private void editItens() {
        textViewHoras.setText(aula.getHoras()+"");
        textViewTitulo.setText(aula.getTitulo());
        textViewDescricaoAula.setText(aula.getDescricao());
        textViewPrecoAula.setText(aula.getValor()+"");
    }

    private void findEditableItens() {

        textViewHoras = (TextView)findViewById(R.id.textViewHorasFornecidas);
        textViewTitulo = (TextView) findViewById(R.id.textViewNomeAula);
        textViewDescricaoAula =(TextView) findViewById(R.id.textViewDescricaoAula);
        textViewPrecoAula =(TextView) findViewById(R.id.textViewPrecoAula);
        textViewDescricaoAula.setMovementMethod(new ScrollingMovementMethod());
    }


    public void chamarEditarAula(View view){
        Intent secondActivity = new Intent(this, EditarAulaActivity.class);
        startActivity(secondActivity);
        finish();
    }

    public void confirmarAula(View view){
        Intent secondActivity = new Intent(this, ConfirmarAulaProfessorActivity.class);
        startActivity(secondActivity);
        finish();
    }

    public void chamarAlunosCadastrados(View view){
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