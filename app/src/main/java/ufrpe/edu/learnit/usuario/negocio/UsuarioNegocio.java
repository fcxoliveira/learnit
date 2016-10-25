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
        Usuario usuario;
        UsuarioPersistencia persistencia = new UsuarioPersistencia(context);
        if(persistencia.existeUsuario(login)){
            usuario = persistencia.retornarUsuario(login,senha);
        }else{
            usuario = null;
        }
        return usuario;
    }

    public Usuario cadastrarUsuario(String login, String senha, String email){
        UsuarioPersistencia persistencia = new UsuarioPersistencia(context);
        Usuario usuario;
        if(!persistencia.existeUsuario(login,email)){
            usuario = persistencia.inserirUsuario(login, senha, email);
        }else{
            usuario = null;
        }
        return usuario;
    }
}