package ufrpe.edu.learnit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import ufrpe.edu.learnit.aula.dominio.AlunoAula;
import android.widget.ArrayAdapter;

/**
 * Created by joel_ on 02/12/2016.
 */

public class CustomAdapterAlunoAula extends ArrayAdapter<AlunoAula> implements View.OnClickListener {

    private ArrayList<AlunoAula> dataSet;
    Context mContext;

    private static class ViewHolder{
        TextView name;
        TextView description;
        TextView horasCompradas;
        TextView horasTotaisCompradas;
    }

    public CustomAdapterAlunoAula(ArrayList<AlunoAula> data, Context context) {
        super(context, R.layout.aulas_compradas_adapter, data);
        this.dataSet = data;
        this.mContext=context;

    }
    @Override
    public void onClick(View v) {

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlunoAula aulaAluno = getItem(position);
        ViewHolder viewHolder;


        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.aulas_compradas_adapter, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.horasCompradas = (TextView) convertView.findViewById(R.id.horasCompradas);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.horasTotaisCompradas = (TextView) convertView.findViewById(R.id.horasTotaisCompradas);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        String titulo = ajustarTitulo(aulaAluno.getAula().getTitulo());
        String descricao = ajustarDescricao(aulaAluno.getAula().getDescricao());
        viewHolder.name.setText(titulo);
        viewHolder.horasCompradas.setText(aulaAluno.getValorTotal()+"");
        viewHolder.description.setText(descricao);
        viewHolder.horasTotaisCompradas.setText(aulaAluno.getHorasTotal()+"");
        return convertView;
    }
    public String ajustarTitulo(String string){
        if (string.length()>25){
            string = string.substring(0,24)+"...";
        }
        return string;
    }

    public String ajustarDescricao(String string){
        if (string.length()>25){
            if (string.substring(23,23)!= " "){
                string = string.substring(0,23)+"-\n"+string.substring(23);
            }else {


                string = string.substring(0, 24) + "\n" + string.substring(25);
            }
        }
        return string;
    }
}
