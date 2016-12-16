package ufrpe.edu.learnit.aula.gui;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

/**
 * Created by matheusc on 08/12/16.
 */

public class ConfirmarAulaProfessorActivity extends AppCompatActivity {
    ListView listView;
    private EditText editText;
    private ArrayAdapter<String> adapter;
    private ArrayList<Perfil> perfis;
    private ArrayList<String> nomesPerfis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_aula_professor);
        Session.setContext(getApplicationContext());
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        perfis = gerenciadorAulasTutor.retornarAlunosCadastrados((int)Session.getAula().getId());
        nomesPerfis = pegarNomesPerfil(perfis);
        adapter = new ArrayAdapter<>(this, R.layout.checked_list, R.id.textViewPerfis, nomesPerfis);
        listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        editText = (EditText)findViewById(R.id.editTextHorasDadas);
        setOnItemClickListener();
    }

    private ArrayList<String> pegarNomesPerfil(ArrayList<Perfil> perfis) {
        ArrayList<String> nomesPerfil = new ArrayList<>();
        for (int i = 0; i<perfis.size(); i++){
            nomesPerfil.add(perfis.get(i).getNome());
        }
        return nomesPerfil;
    }

    public void setOnItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String string = ((TextView)view).getText().toString();
                if (nomesPerfis.contains(string)){
                    nomesPerfis.remove(string);
                }else{
                    nomesPerfis.add(string);
                }
            }
        });
    }

    public void confirmar(View view){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.coinicon)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify((int)Session.getAula().getId(), mBuilder.build());
        chamarHome(view);
    }

    public void chamarHome(View view){
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        finish();
    }

}
