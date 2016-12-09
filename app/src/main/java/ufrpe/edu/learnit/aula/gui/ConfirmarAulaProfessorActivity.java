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
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import ufrpe.edu.learnit.CustomAdapterPerfil;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.gui.PerfilAlheioActivity;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

/**
 * Created by matheusc on 08/12/16.
 */

public class ConfirmarAulaProfessorActivity extends AppCompatActivity {
    ListView listView;
    private EditText editText;
    private CustomAdapterPerfil adapter;
    private ArrayList<Perfil> perfis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_aula_professor);
        Session.setContext(getApplicationContext());
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        perfis = gerenciadorAulasTutor.retornarAlunosCadastrados((int)Session.getAula().getId());
        adapter = new CustomAdapterPerfil(perfis, getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        editText = (EditText)findViewById(R.id.editTextHorasDadas);
        setOnItemClickListener();
    }

    public void setOnItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object= adapter.getItem(position);
                Perfil perfil = (Perfil)object;
                Session.setPerfilAlheio(perfil);
                Intent secondActivity = new Intent(Session.getContext(), PerfilAlheioActivity.class);
                startActivity(secondActivity);
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
