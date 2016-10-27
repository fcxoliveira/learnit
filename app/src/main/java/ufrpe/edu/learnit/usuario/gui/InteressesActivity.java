package ufrpe.edu.learnit.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;

public class InteressesActivity extends AppCompatActivity {
    private EditText nome;
    private EditText bio;
    private Spinner interesse1,interesse2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interesses);
        String[] interesses = new String[]{"Informatica", "Musica", "Portugues", "Matematica", "Biologia", "Fisica", "Quimica", "Ed. Fisica"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, interesses);
        interesse1 = (Spinner) findViewById(R.id.spinner);
        interesse1.setAdapter(adapter);
        interesse2 = (Spinner) findViewById(R.id.spinner2);
        interesse2.setAdapter(adapter);
        Session.setContext(getApplicationContext());
        nome = (EditText)findViewById(R.id.editText5);
        bio = (EditText)findViewById(R.id.editText4);
    }

    public void chamarHome(View view) {
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        perfilNegocio.cadastrarPerfil(Session.getUsuario().getID(),bio.getText().toString(),nome.getText().toString(),interesse1.getSelectedItem().toString(),interesse2.getSelectedItem().toString());
        this.finish();
    }


}
