package ufrpe.edu.learnit.usuario.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.usuario.dominio.Usuario;
import ufrpe.edu.learnit.usuario.persistencia.UsuarioPersistencia;

public class UsuarioNegocio {

    public Usuario retornarUsuario(String login, String senha){
        UsuarioPersistencia persistencia = new UsuarioPersistencia();
        return persistencia.retornarUsuario(login,senha);
    }

    public Usuario cadastrarUsuario(String login, String senha, String email){
        UsuarioPersistencia persistencia = new UsuarioPersistencia();
        Usuario usuario;
        if(!persistencia.existeUsuario(login,email)){
            usuario = persistencia.cadastrarUsuario(login, senha, email);
        }else{
            usuario = null;
        }
        return usuario;
    }

    public void excluirUsuario(Usuario usuario){
        UsuarioPersistencia usuarioPersistencia = new UsuarioPersistencia();
        usuarioPersistencia.excluirUsuario(usuario.getLogin());
    }

    public Usuario retornarUsuarioPorEmail(String email){
        UsuarioPersistencia usuarioPersistencia = new UsuarioPersistencia();
        return usuarioPersistencia.retornarUsuarioPorEmail(email);
    }
    public ArrayList<Usuario> retornarTodosOsUsuarios(){
        UsuarioPersistencia usuarioPersistencia = new UsuarioPersistencia();
        return usuarioPersistencia.retornarTodosOsUsuarios();
    }
}