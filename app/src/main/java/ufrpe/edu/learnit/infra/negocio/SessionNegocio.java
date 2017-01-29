package ufrpe.edu.learnit.infra.negocio;

import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.persistencia.SessionPersistencia;
import ufrpe.edu.learnit.usuario.dominio.Usuario;
import ufrpe.edu.learnit.usuario.persistencia.UsuarioPersistencia;

public class SessionNegocio {

    public Usuario retornarUsuarioLogado(){
        Usuario usuario;
        SessionPersistencia sessionPersistencia = new SessionPersistencia();
        int id = sessionPersistencia.idLogado();
        if(id==0) {
            usuario = null;
        }else{
            UsuarioPersistencia usuarioPersistencia = new UsuarioPersistencia();
            usuario =usuarioPersistencia.retornarUsuario(id);
        }
        return usuario;
    }

    public void cadastrarUsuarioLogado(Usuario usuario){
        int id = usuario.getID();
        SessionPersistencia sessionPersistencia = new SessionPersistencia();
        sessionPersistencia.cadastrarIdLogado(id);
    }

    public void deslogarUsuario(){
        SessionPersistencia sessionPersistencia = new SessionPersistencia();
        sessionPersistencia.excluirUsuarioLogado(Session.getUsuario().getID());
    }

    public void newRating(int idPerfil ,int idItem ,float rate){
        SessionPersistencia sessionPersistencia = new SessionPersistencia();
        sessionPersistencia.newRating(idPerfil,idItem,rate);
    }
    public void updateRating(int idPerfil ,int idItem ,float rate){
        SessionPersistencia sessionPersistencia = new SessionPersistencia();
        sessionPersistencia.updateRating(idPerfil,idItem,rate);
    }
    public float retornarRating(int idPerfil ,int idItem ,float rate){
        SessionPersistencia sessionPersistencia = new SessionPersistencia();
        float rating = sessionPersistencia.retornarRating(idPerfil,idItem,rate);
        return rating;
    }

}
