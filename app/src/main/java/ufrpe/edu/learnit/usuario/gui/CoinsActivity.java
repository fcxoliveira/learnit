package ufrpe.edu.learnit.usuario.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.infra.negocio.SessionNegocio;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.gui.PerfilActivity;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;
import ufrpe.edu.learnit.usuario.dominio.Usuario;

public class CoinsActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton,radioButton1,radioButton2,radioButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioButton = (RadioButton)findViewById(R.id.RadioButton1);
        radioButton1 = (RadioButton)findViewById(R.id.RadioButton2);
        radioButton2 = (RadioButton)findViewById(R.id.RadioButton3);
        radioButton3 = (RadioButton)findViewById(R.id.RadioButton4);
        radioButton.setId(0+3);
        radioButton1.setId(0+2);
        radioButton2.setId(0+1);
        radioButton3.setId(0+0);
    }
    public void carregarMoedas(View v){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        int moedas = getMoedasValor(selectedId);
        perfilNegocio.addMoedas(moedas);
        carregarHome();
    }

    public void carregarHome() {
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        finish();
    }

    private int getMoedasValor(int id) {
        int moedas = 0;
        if (id==0){
            moedas = 500;
        }else if(id==1){
            moedas = 2000;
        }else if(id==2){
            moedas = 10000;
        }else if(id==3){
            moedas = 30000;
        }
        return moedas;
    }

    @Override
    public void onBackPressed(){
        carregarHome();
    }

}
