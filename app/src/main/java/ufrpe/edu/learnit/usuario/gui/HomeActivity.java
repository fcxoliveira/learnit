package ufrpe.edu.learnit.usuario.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.dominio.Session;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        Session.setUsuario(null);
        Intent secondActivity = new Intent(this, LoginActivity.class);
        startActivity(secondActivity);
    }
}
