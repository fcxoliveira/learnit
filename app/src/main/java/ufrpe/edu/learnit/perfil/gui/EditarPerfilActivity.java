package ufrpe.edu.learnit.perfil.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.infra.negocio.TagNegocio;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;

public class EditarPerfilActivity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextBio;
    private Spinner tag1, tag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        SessionNegocio sessionNegocio = new SessionNegocio();
        int idUsuario = sessionNegocio.retornarUsuarioLogado().getID();
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        Perfil perfil = perfilNegocio.retornarPerfil(idUsuario);
        TagNegocio tagNegocio = new TagNegocio();
        ArrayList<Tag> tags = tagNegocio.retornarTodasTags();
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tags);
        tag1 = (Spinner) findViewById(R.id.spinner);
        tag1.setAdapter(adapter);
        tag2 = (Spinner) findViewById(R.id.spinner2);
        tag2.setAdapter(adapter);
        tag1.setSelection((perfil.getInteresses().get(0).getID())-1);
        tag2.setSelection((perfil.getInteresses().get(1).getID())-1);
        Session.setContext(getApplicationContext());
        editTextNome = (EditText) findViewById(R.id.editText5);
        editTextBio = (EditText) findViewById(R.id.editText4);
        editTextNome.setText(perfil.getNome());
        editTextBio.setText(perfil.getBio());

    }
}
