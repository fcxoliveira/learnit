package ufrpe.edu.learnit.negocio;

import android.content.Context;
import ufrpe.edu.learnit.dominio.Usuario;
import ufrpe.edu.learnit.persistencia.UsuarioPersistencia;

/**
 * Created by Filipe on 20/10/2016.
 */

public class UsuarioNegocio {
    private Context context;
    public UsuarioNegocio(Context context){

    }
    public Usuario pesquisarUsuario(String login, String senha){
        UsuarioPersistencia adapter = new UsuarioPersistencia(context);
        adapter.open();
        Usuario usuario = adapter.retornarUsuario(login,senha);
        adapter.close();
        return usuario;
    }
}
