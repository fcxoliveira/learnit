package ufrpe.edu.learnit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import ufrpe.edu.learnit.aula.gui.CadastrarAulaTutorActivity;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.perfil.gui.PerfilActivity;
import ufrpe.edu.learnit.usuario.gui.CoinsActivity;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;
import ufrpe.edu.learnit.usuario.gui.LoginActivity;


public class NavegarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegar);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();

        if (i == R.id.home){
            Intent secondActivity = new Intent(this, HomeActivity.class);
            startActivity(secondActivity);
            return true;
        }
        else if (i == R.id.minhaaulas){
            Toast.makeText(this, "Minhas aulas selecionado",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (i == R.id.cadastraraulas){
            Intent secondActivity = new Intent(this, CadastrarAulaTutorActivity.class);
            startActivity(secondActivity);
            return true;
        }
        else if (i == R.id.editarperfil){
            Intent secondActivity = new Intent(this, PerfilActivity.class);
            startActivity(secondActivity);
            return true;
        }
        else if (i == R.id.carregarcoins){
            Intent secondActivity = new Intent(this, CoinsActivity.class);
            startActivity(secondActivity);
            return true;
        }
        else if (i == R.id.logout){
            SessionNegocio sessionNegocio = new SessionNegocio();
            sessionNegocio.deslogarUsuario();
            Session.setUsuario(null);
            Intent secondActivity = new Intent(this, LoginActivity.class);
            startActivity(secondActivity);
            this.finish();
            return true;
        }
        else {
            return false;
        }
    }
}
