package ufrpe.edu.learnit.aula.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        TextViewNomeDaAula = (TextView)findViewById(R.id.textView4);
        TextViewDescricaoAula = (TextView)findViewById(R.id.TextViewDescricaoAula);
        TextViewTotalDeHoras = (TextView)findViewById(R.id.TextViewTotalDeHoras);
        TextViewPrecoPorHoraAula = (TextView)findViewById(R.id.TextViewPrecoPorHoraAula);
        TextViewTotalDaCompra = (TextView)findViewById(R.id.TextViewTotalDaCompra);
        TextViewNomeDaAula.setText(aula.getTitulo());
        TextViewDescricaoAula.setText(aula.getDescricao());
        TextViewPrecoPorHoraAula.setText(new StringBuilder().append(aula.getValor()).toString());
        TextViewTotalDeHoras.setText(new StringBuilder().append(aula.getDuracaoHorasAula()).toString());
        TextViewTotalDaCompra.setText("0");
        EditTextTotalDeHorasDesejadas=(EditText) findViewById(R.id.EditTextTotaDeHorasDesejadas);
        this.setOnUserInputValue();
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
                    double valorDesejado = Double.parseDouble(text);
                    double resultado = valorDesejado * aula.getValor();
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
                int aulaId =(int)Session.getAula().getId();
                String data = this.getDateTime();
                String TotalDeHoras = EditTextTotalDeHorasDesejadas.getText().toString();
                int horas = Math.round(Float.parseFloat(TotalDeHoras));
                String TotalMoedas =TextViewTotalDaCompra.getText().toString();
                int moedas = Math.round(Float.parseFloat(TotalMoedas));
                gerenciadorAulasAlunos.inscreverAlunoEmAula(usuarioId,aulaId,data,horas,moedas);
                Intent secondActivity = new Intent(this, HomeActivity.class);
                startActivity(secondActivity);
                this.finish();
            }

}
