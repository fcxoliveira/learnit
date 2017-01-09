package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


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
    int horas;


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
        TextViewDescricaoAula.setMovementMethod(new ScrollingMovementMethod());
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
                int valorPago = Math.round(Float.parseFloat(TextViewTotalDaCompra.getText().toString()));
                if(verificarValorDeHoras()||verificarHorasDesejadas()||verificarMoedas() ) {
                    gerenciadorAulasAlunos.inscreverAlunoEmAula(usuarioId, aulaId, data, horas, valorPago);
                    Intent secondActivity = new Intent(this, HomeActivity.class);
                    startActivity(secondActivity);
                    this.finish();
                }
    }

    private boolean verificarValorDeHoras() {
        String totalDeHoras = EditTextTotalDeHorasDesejadas.getText().toString();
        boolean result = true;
        try {
            horas = Math.round(Float.parseFloat(totalDeHoras));
        }catch (NumberFormatException exception){
            EditTextTotalDeHorasDesejadas.requestFocus();
            EditTextTotalDeHorasDesejadas.setError("Colocar um valor para horas é obrigatório");
            result = false;
        }
        return result;
    }


    private boolean verificarMoedas(){
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        boolean result = true;
        int moedas =(int) Float.parseFloat(TextViewTotalDaCompra.getText().toString());
        if(moedas>perfilNegocio.retornarPerfil(Session.getUsuario().getID()).getMoedas()){
            TextViewTotalDaCompra.requestFocus();
            TextViewTotalDaCompra.setError("Moedas insuficientes para realizar a compra, insira mais moedas");
            result = false;
        }
        return result;
    }

    public boolean verificarHorasDesejadas(){
        boolean result = true;
        int horas = Integer.parseInt(EditTextTotalDeHorasDesejadas.getText().toString());
        if(horas>Session.getAula().getHoras()||horas==0){
            EditTextTotalDeHorasDesejadas.requestFocus();
            EditTextTotalDeHorasDesejadas.setError("total de horas não disponiveis, insira um valor menor");
            result = false;
        }
        return result;
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

}
