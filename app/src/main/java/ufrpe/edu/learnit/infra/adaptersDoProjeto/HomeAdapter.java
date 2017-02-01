package ufrpe.edu.learnit.infra.adaptersDoProjeto;

/**
 * Created by silva on 01/02/2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ufrpe.edu.learnit.R;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.confirmacao.dominio.Confirmacao;
import ufrpe.edu.learnit.confirmacao.negocio.ConfirmacaoNegocio;


public class HomeAdapter  extends ArrayAdapter<Aula> implements View.OnClickListener {
    private ArrayList<Aula> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView name;
        TextView description;
        TextView price;
        TextView time;

    }

    public HomeAdapter(ArrayList<Aula> data, Context context) {
        super(context, R.layout.textview_adapter, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

    }






}
