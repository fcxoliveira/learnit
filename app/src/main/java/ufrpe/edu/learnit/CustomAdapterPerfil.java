package ufrpe.edu.learnit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;
import ufrpe.edu.learnit.perfil.dominio.Perfil;

/**
 * Created by Filipe on 08/12/2016.
 */

public class CustomAdapterPerfil extends ArrayAdapter<Perfil> implements View.OnClickListener {

    private ArrayList<Perfil> perfis;
    Context mContext;
    int layout, view;

    @Override
    public void onClick(View view) {

    }

    private static class ViewHolder {
        TextView nome;

    }
    public CustomAdapterPerfil(ArrayList<Perfil> perfis, Context context) {
        super(context, R.layout.textview_adapter_perfil, perfis);
        this.perfis = perfis;
        this.mContext=context;
    }

    public CustomAdapterPerfil(ArrayList<Perfil> perfis, Context context, int layout, int view){
        super(context, layout, view, perfis);
        this.perfis = perfis;
        this.mContext = context;
        this.layout = layout;
        this.view = view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Perfil perfil = getItem(position);
        ViewHolder viewHolder;


        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.textview_adapter_perfil, parent, false);
            viewHolder.nome = (TextView) convertView.findViewById(R.id.nome);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        String nomePerfil = perfil.getNome();
        viewHolder.nome.setText(nomePerfil);
        return convertView;
    }

    public View getView(int position, View convertView){
        return convertView;
    }
}