package ufrpe.edu.learnit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import ufrpe.edu.learnit.aula.gui.ConfirmarAulaProfessorActivity;
import ufrpe.edu.learnit.perfil.dominio.Perfil;

public class ListaAlunoCheckboxAdapter extends ArrayAdapter {
    private List<Perfil> listaPerfis;
    private Context context;

    public ListaAlunoCheckboxAdapter(List<Perfil> listaPerfis, Context context){

        super(context, R.layout.activity_confirmar_aula_professor,listaPerfis);
        this.listaPerfis=listaPerfis;
        this.context=context;
    }
    private static class ListaAlunoCheckboxHolder{
        public TextView nomeAluno;
        public CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final Perfil perfil = listaPerfis.get(position);
        ListaAlunoCheckboxHolder holder = new ListaAlunoCheckboxHolder();

        if (convertView == null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.activity_main,null);
            holder.nomeAluno=(TextView) v.findViewById(R.id.nomeAluno);
            holder.checkbox =(CheckBox) v.findViewById(R.id.checkbox1);
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    perfil.setSelected(b);
                }
            });
            v.setTag(holder);
        }else{
            holder= (ListaAlunoCheckboxHolder) v.getTag();
        }
        holder.nomeAluno.setText(perfil.getNome());
        holder.checkbox.setChecked(perfil.isSelected());
        holder.checkbox.setTag(perfil);

        return v;
    }
}
