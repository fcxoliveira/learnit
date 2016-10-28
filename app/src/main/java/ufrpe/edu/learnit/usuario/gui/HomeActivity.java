package ufrpe.edu.learnit.usuario.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.gui.CadastrarAulaTutorActivity;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasTutor;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.perfil.gui.PerfilActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GerenciadorAulasTutor gerenciadorAulasTutor = new GerenciadorAulasTutor();
        TextView textViewAulas = (TextView)findViewById(R.id.textView5);
        StringBuilder sb = new StringBuilder();
        sb.append(gerenciadorAulasTutor.retornarQuantidadeDeAulas(Session.getUsuario().getID()));
        textViewAulas.setText("Aulas oferecidas por voce: "+sb.toString());
        TextView textView = (TextView)findViewById(R.id.textView2);
        textView.setText("Bem vindo "+ Session.getUsuario().getLogin());
        textView.setVisibility(View.VISIBLE);
        Session.setContext(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tem certeza que quer sair do APP?");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                HomeActivity.super.finish();
            }
    });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void fazerLogoff(View v){
        SessionNegocio sessionNegocio = new SessionNegocio();
        sessionNegocio.deslogarUsuario();
        Session.setUsuario(null);
        Intent secondActivity = new Intent(this, LoginActivity.class);
        startActivity(secondActivity);
        this.finish();
    }

    public void chamarPerfil(View view){
        Intent secondActivity = new Intent(this, PerfilActivity.class);
        startActivity(secondActivity);
    }

    public void chamarCadastrarAula(View view){
        Intent secondActivity = new Intent(this, CadastrarAulaTutorActivity.class);
        startActivity(secondActivity);
    }
}
