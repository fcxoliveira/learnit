package ufrpe.edu.learnit.perfil.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.tag.dominio.Tag;
import ufrpe.edu.learnit.tag.negocio.TagNegocio;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class PerfilActivity extends AppCompatActivity {
    private TextView textViewRate, textViewAvaliadores, textViewHoras, textViewNome, textViewBiografia ;
    private RatingBar ratingBar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Session.setContext(getApplicationContext());
        Perfil perfil = retornarPerfil(Session.getUsuario().getID());
        findEditableItens();
        editItens(perfil);
        newListViewTags();
    }
    private void newListViewTags(){
        listView=(ListView) findViewById(R.id.ListViewTags);
        TagNegocio tagNegocio = new TagNegocio();
        ArrayList<Tag> arrayTags = tagNegocio.retornarTagsPerfil(Session.getUsuario().getID());
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayTags);
        listView.setAdapter(adapter);
    }

    private void editItens(Perfil perfil) {
        textViewHoras.setText(perfil.getHoras()+"");
        textViewRate.setText(String.format("Nota "+"%.01f" , perfil.getAvaliacao()));
        String avaliadores = perfil.getAvaliadores()+"";
        textViewAvaliadores.setText("Avaliadores " +avaliadores);
        textViewNome.setText(perfil.getNome());
        textViewBiografia.setText(perfil.getBio());
        ratingBar.setRating(perfil.getAvaliacao());
        textViewBiografia.setMovementMethod(new ScrollingMovementMethod());
    }

    private void findEditableItens() {
        textViewRate = (TextView)findViewById(R.id.textViewRate);
        textViewAvaliadores = (TextView)findViewById(R.id.textViewAvaliadores);
        textViewHoras = (TextView)findViewById(R.id.textViewHoras);
        textViewNome = (TextView) findViewById(R.id.textViewNome);
        ratingBar = (RatingBar)findViewById(R.id.ratingBarMediaGeral);
        textViewBiografia=(TextView) findViewById(R.id.textViewBiografia);
    }

    public Perfil retornarPerfil(int id){
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        return perfilNegocio.retornarPerfil(id);
    }

    public void chamarEditarPerfil(View view){
        Intent secondActivity = new Intent(this, EditarPerfilActivity.class);
        startActivity(secondActivity);
        this.finish();
    }

    @Override
    public void onBackPressed(){
        carregarHome();
    }

    public void carregarHome() {
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        this.finish();
    }



}
