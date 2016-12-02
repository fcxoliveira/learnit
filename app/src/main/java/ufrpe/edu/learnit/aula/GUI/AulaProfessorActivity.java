package ufrpe.edu.learnit.aula.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.negocio.AulaNegocio;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.dominio.Tag;


public class AulaProfessorActivity extends AppCompatActivity {
    private TextView  textView7, textView104,textView105;
    private TextView  textView101, textView100, textView102,textView108;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula_professor);
        Session.setContext(getApplicationContext());
        gerarItens();
        Aula aula = retornarAula((int) Session.getAula().getId());
        String horas = new StringBuilder().append(aula.getHoras()).toString();
        textView101.setText(horas);
        textView100.setText(aula.getNome());
        textView7.setText(aula.getNome());
        String descricao = (aula.getDescricao());
        textView102.setText(descricao);
        ArrayList<Tag> interesses = aula.getTags();
        Tag interesse1 = interesses.get(0);
        Tag interesse2 = interesses.get(1);
        String tag1 = new StringBuilder().append(interesse1).toString();
        String tag2 = new StringBuilder().append(interesse2).toString();
        textView108.setText(tag1);
        textView104.setText(tag2);
        String preco = new StringBuilder().append(aula.getValor()).toString();
        textView105.setText(preco);

    }
    private void gerarItens() {
        textView7 = (TextView)findViewById(R.id.textView7);
        textView101 = (TextView)findViewById(R.id.textView101);
        textView100 = (TextView) findViewById(R.id.textView100);
        textView7 = (TextView) findViewById(R.id.textView7);
        textView102=(TextView) findViewById(R.id.textView102);
        textView104=(TextView) findViewById(R.id.textView4);
        textView105=(TextView) findViewById(R.id.textView105);
        textView108=(TextView) findViewById(R.id.textView8);
    }
    public Aula retornarAula(int id){
        AulaNegocio aulaNegocio = new AulaNegocio();
        return aulaNegocio.retornarAula(id);
    }
}