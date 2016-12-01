package ufrpe.edu.learnit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;


/**
 * Created by matheusc on 29/11/16.
 */

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
            Toast.makeText(this, "Home selecionado",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (i == R.id.minhaaulas){
            Toast.makeText(this, "Minhas aulas selecionado",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (i == R.id.cadastraraulas){
            Toast.makeText(this, "Cadastrar aulas selecionado",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (i == R.id.editarperfil){
            Toast.makeText(this, "Editar perfil selecionado",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (i == R.id.carregarcoins){
            Toast.makeText(this, "Carregar Coins selecionado",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (i == R.id.logout){
            Toast.makeText(this, "Fazendo logoff",Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            return false;
        }
    }
}
