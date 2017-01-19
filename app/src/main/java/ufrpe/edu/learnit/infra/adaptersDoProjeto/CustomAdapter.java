package ufrpe.edu.learnit.infra.adaptersDoProjeto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;

public class CustomAdapter extends ArrayAdapter<Aula> implements View.OnClickListener{

    private ArrayList<Aula> dataSet;
    Context mContext;
    private static class ViewHolder {
        TextView name;
        TextView description;
        TextView price;
        TextView time;
    }

    public CustomAdapter(ArrayList<Aula> data, Context context) {
        super(context, R.layout.textview_adapter, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aula aula = getItem(position);
        ViewHolder viewHolder;


        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.textview_adapter, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.horasCompradas);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.time = (TextView) convertView.findViewById(R.id.horasTotaisCompradas);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        String titulo = ajustarTitulo(aula.getTitulo());
        String descricao = ajustarDescricao(aula.getDescricao());
        viewHolder.name.setText(titulo);
        viewHolder.price.setText(String.valueOf(aula.getValor()));
        viewHolder.description.setText(descricao);
        viewHolder.time.setText(String.valueOf(aula.getHoras()));
        return convertView;
    }

    public String ajustarTitulo(String string){
        if (string.length()>25){
            string = string.substring(0,24)+"...";
        }
        return string;
    }

    public String ajustarDescricao(String string){
        if (string.length()>30){
//            if (string.substring(23,23)!= " "){
//                string = string.substring(0,23)+"-\n"+string.substring(23);
//            }else {
//
//
//                string = string.substring(0, 24) + "\n" + string.substring(25);
//            }
            string = string.substring(0,29)+"...";
        }
        return string;
    }

}