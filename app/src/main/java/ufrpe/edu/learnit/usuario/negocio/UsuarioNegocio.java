package ufrpe.edu.learnit.usuario.negocio;

import android.content.Context;

import ufrpe.edu.learnit.usuario.dominio.Usuario;
import ufrpe.edu.learnit.usuario.persistencia.UsuarioPersistencia;


public class UsuarioNegocio {
    private Context context;

    public UsuarioNegocio(Context context){
        this.context = context;
    }

    public Usuario pesquisarUsuario(String login, String senha){
        UsuarioPersistencia persistencia = new UsuarioPersistencia(context);
        persistencia.open();
        if(this.existeUsuario(login)==false){
            persistencia.close();
            return null;
        }
        Usuario usuario = persistencia.retornarUsuario(login,senha);
        persistencia.close();
        return usuario;
    }

    private boolean existeUsuario(String userName) {
        UsuarioPersistencia persistencia = new UsuarioPersistencia(context);
        persistencia.open();
        if (persistencia.existeUsuario(userName)==false){
            persistencia.close();
            return false;
        }else{
            persistencia.close();
            return true;
        }
    }



    public Usuario cadastrarUsuario(String login, String senha, String email){
        UsuarioPersistencia persistencia = new UsuarioPersistencia(context);
        persistencia.open();
        if(persistencia.existeUsuario(login,email)==false){
            Usuario usuario = persistencia.inserirUsuario(login,senha,email);
            persistencia.close();
            return usuario;
        }else{
            persistencia.close();
            return null;
        }

    }
}
