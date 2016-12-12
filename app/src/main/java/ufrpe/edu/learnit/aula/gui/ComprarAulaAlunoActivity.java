package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.negocio.GerenciadorAulasAlunos;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.negocio.PerfilNegocio;
import ufrpe.edu.learnit.usuario.gui.HomeActivity;

public class ComprarAulaAlunoActivity extends AppCompatActivity {
    static Aula aula;
    private TextView TextViewNomeDaAula;
    private TextView TextViewDescricaoAula;
    private TextView TextViewTotalDeHoras;
    private TextView TextViewPrecoPorHoraAula;
    private TextView TextViewTotalDaCompra;
    private EditText EditTextTotalDeHorasDesejadas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aula=Session.getAula();
        setContentView(R.layout.activity_comprar_aula_aluno);
        Session.setContext(getApplicationContext());
        findEditableItens();
        editItenss();
        this.setOnUserInputValue();
    }

    private void editItenss() {
        TextViewNomeDaAula.setText(aula.getTitulo());
        TextViewDescricaoAula.setText(aula.getDescricao());
        TextViewPrecoPorHoraAula.setText(aula.getValor()+"");
        TextViewTotalDeHoras.setText(aula.getHoras()+"");
        TextViewTotalDaCompra.setText("0");
    }

    private void findEditableItens() {
        EditTextTotalDeHorasDesejadas=(EditText) findViewById(R.id.EditTextTotaDeHorasDesejadas);
        TextViewNomeDaAula = (TextView)findViewById(R.id.textView4);
        TextViewDescricaoAula = (TextView)findViewById(R.id.TextViewDescricaoAula);
        TextViewTotalDeHoras = (TextView)findViewById(R.id.TextViewTotalDeHoras);
        TextViewPrecoPorHoraAula = (TextView)findViewById(R.id.TextViewPrecoPorHoraAula);
        TextViewTotalDaCompra = (TextView)findViewById(R.id.TextViewTotalDaCompra);
    }


    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void setOnUserInputValue() {
        EditTextTotalDeHorasDesejadas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                StringBuilder sb = new StringBuilder();
                sb.append(EditTextTotalDeHorasDesejadas.getText().toString());
                String text =sb.toString();
                if (text == "") {
                    TextViewTotalDaCompra.setText("0");
                }
                else{
                    int valorDesejado = Integer.parseInt(text);
                    int resultado = valorDesejado * aula.getValor();
                    TextViewTotalDaCompra.setText(String.valueOf(resultado));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void confirmar(View view){
                GerenciadorAulasAlunos gerenciadorAulasAlunos = new GerenciadorAulasAlunos();
                int usuarioId =Session.getUsuario().getID();
                int aulaId =Session.getAula().getId();
                String data = this.getDateTime();
                String TotalDeHoras = EditTextTotalDeHorasDesejadas.getText().toString();
                int horas = Math.round(Float.parseFloat(TotalDeHoras));
                String TotalMoedas =TextViewTotalDaCompra.getText().toString();
                int moedas = Math.round(Float.parseFloat(TotalMoedas));
                if(verificarHoras()) {
                    if (verificarMoedas()) {
                        gerenciadorAulasAlunos.inscreverAlunoEmAula(usuarioId, aulaId, data, horas, moedas);
                        Intent secondActivity = new Intent(this, HomeActivity.class);
                        startActivity(secondActivity);
                        this.finish();
                    }else{Toast toast = Toast.makeText(getApplicationContext(), "Moedas insuficientes", Toast.LENGTH_SHORT);
                    toast.show();}
                }else{Toast toast = Toast.makeText(getApplicationContext(), "Horas não disponiveis", Toast.LENGTH_SHORT);
                toast.show();}
    }

    @Override
    public void onBackPressed(){
        carregarHome();
    }

    public void carregarHome() {
        Intent secondActivity = new Intent(this, HomeActivity.class);
        startActivity(secondActivity);
        finish();
    }

    private boolean verificarMoedas(){
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        boolean result = true;
        int moedas =(int) Float.parseFloat(TextViewTotalDaCompra.getText().toString());
        if(moedas>perfilNegocio.retornarPerfil(Session.getUsuario().getID()).getMoedas()){
            result = false;
        }
        return result;
    }

    public boolean verificarHoras(){
        boolean result = true;
        int horas = Integer.parseInt(EditTextTotalDeHorasDesejadas.getText().toString());
        if(horas>Session.getAula().getHoras()||horas==0){
            result = false;
        }
        return result;
    }

}
