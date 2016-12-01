package ufrpe.edu.learnit.usuario.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.usuario.dominio.Usuario;

public class CoinsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins);
        RadioButton moedas500 = (RadioButton)findViewById(R.id.RadioButton4);
        RadioButton moedas2000 = (RadioButton)findViewById(R.id.RadioButton3);
        RadioButton moedas10000 = (RadioButton)findViewById(R.id.RadioButton2);
        RadioButton moedas30000 = (RadioButton)findViewById(R.id.RadioButton1);
        moedas500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarMoedas(500);
            }
        });
        moedas2000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarMoedas(2000);
            }
        });
        moedas10000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarMoedas(10000);
            }
        });
        moedas30000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarMoedas(30000);
            }
        });

    }
    public void carregarMoedas(int moedas){
        SessionNegocio sessionNegocio = new SessionNegocio();
        Usuario usuarioLogado= sessionNegocio.retornarUsuarioLogado();
        Perfil perfilUsuarioLogado = usuarioLogado.getPerfil();
        int moedasCarregadasUsuario= perfilUsuarioLogado.getMoedas() + moedas;
        perfilUsuarioLogado.setMoedas(moedasCarregadasUsuario);
    }



}
