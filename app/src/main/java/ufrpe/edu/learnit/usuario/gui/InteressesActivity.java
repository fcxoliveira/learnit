package ufrpe.edu.learnit.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Session;

/**
 * Created by matheus on 26/10/16.
 */

public class InteressesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interesses);
        String[] interesses = new String[]{"Informatica", "Musica", "Portugues", "Matematica", "Biologia", "Fisica", "Quimica", "Ed. Fisica"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, interesses);
        Spinner interesse1 = (Spinner) findViewById(R.id.spinner);
        interesse1.setAdapter(adapter);
        Spinner interesse2 = (Spinner) findViewById(R.id.spinner2);
        interesse2.setAdapter(adapter);
        Session.setContext(getApplicationContext());
    }

    public void chamarHome(View view) {
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
    }


}
