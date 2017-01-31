package ufrpe.edu.learnit.infra.adaptersDoProjeto;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.confirmacao.dominio.Confirmacao;
import ufrpe.edu.learnit.confirmacao.negocio.ConfirmacaoNegocio;
import ufrpe.edu.learnit.perfil.dominio.Perfil;

/**
 * Created by Filipe on 08/12/2016.
 */

public class CustomAdapterPerfil extends ArrayAdapter<Perfil> implements View.OnClickListener {

    private  int idAula;
    private ArrayList<Perfil> perfis;
    Context mContext;
    int layout, view;

    @Override
    public void onClick(View view) {

    }

    private static class ViewHolder {
        TextView nome;

    }
    public CustomAdapterPerfil(ArrayList<Perfil> perfis, Context context, int idAula) {
        super(context, R.layout.textview_adapter_perfil, perfis);
        this.perfis = perfis;
        this.mContext=context;
        this.idAula = idAula;
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
        ConfirmacaoNegocio confirmacaoNegocio = new ConfirmacaoNegocio();
        ArrayList<Confirmacao> confirmacaos = confirmacaoNegocio.retornarConfimacoesCanceladas(idAula);

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.textview_adapter_perfil, parent, false);
            viewHolder.nome = (TextView) convertView.findViewById(R.id.nome);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        if (confirmacaos==null){
            String nomePerfil = perfil.getNome();
            viewHolder.nome.setText(nomePerfil);
        }
        else {
            String nomePerfil = perfil.getNome();
            viewHolder.nome.setText(nomePerfil);
            for (Confirmacao confirmacao:confirmacaos) {
                if (confirmacao.getIdAluno()== perfil.getId()){
                    viewHolder.nome.setError("Confirmação Cancelada");
                }
            }
        }
        return convertView;
    }

    public View getView(int position, View convertView){
        return convertView;
    }
}