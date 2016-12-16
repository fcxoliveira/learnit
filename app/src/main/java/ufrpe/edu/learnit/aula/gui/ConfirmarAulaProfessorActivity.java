package ufrpe.edu.learnit.aula.gui;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import ufrpe.edu.learnit.ListaAlunoCheckboxAdapter;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

/**
 * Created by matheusc on 08/12/16.
 */

public class ConfirmarAulaProfessorActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ListView listView;
    private EditText editText;
    private ArrayList<Perfil> perfis;
    private ListaAlunoCheckboxAdapter lacAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_aula_professor);
        Session.setContext(getApplicationContext());
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        perfis = gerenciadorAulasTutor.retornarAlunosCadastrados(Session.getAula().getId());
        lacAdapter=new ListaAlunoCheckboxAdapter(perfis,getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(lacAdapter);
        editText = (EditText)findViewById(R.id.editTextHorasDadas);
    }

    public void confirmar(View view){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.coinicon)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(Session.getAula().getId(), mBuilder.build());
        chamarHome(view);
    }

    public void chamarHome(View view){
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int posicao = listView.getPositionForView(buttonView);
        if (posicao!= listView.INVALID_POSITION){
            Perfil perfil = perfis.get(posicao);
            perfil.setSelect(isChecked);
        }

    }
}
