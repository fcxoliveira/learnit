package ufrpe.edu.learnit.usuario.negocio;

import android.content.Context;

import ufrpe.edu.learnit.usuario.dominio.Usuario;
import ufrpe.edu.learnit.usuario.persistencia.UsuarioPersistencia;

public class UsuarioNegocio {

    public Usuario retornarUsuario(String login, String senha){
        Usuario usuario;
        UsuarioPersistencia persistencia = new UsuarioPersistencia();
        usuario = persistencia.retornarUsuario(login,senha);
        return usuario;
    }

    public Usuario cadastrarUsuario(String login, String senha, String email){
        UsuarioPersistencia persistencia = new UsuarioPersistencia();
        Usuario usuario;
        if(!persistencia.existeUsuario(login,email)){
            usuario = persistencia.inserirUsuario(login, senha, email);
        }else{
            usuario = null;
        }
        return usuario;
    }
}