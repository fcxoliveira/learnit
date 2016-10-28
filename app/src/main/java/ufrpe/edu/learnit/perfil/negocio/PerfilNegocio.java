package ufrpe.edu.learnit.perfil.negocio;

import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.persistencia.PerfilPersistencia;

public class PerfilNegocio {

    public Perfil retornarPerfil(int id){
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        return perfilPersistencia.retornarPerfil(id);
    }

    public void cadastrarPerfil(int id, String bio, String nome, String interesse1, String interesse2, String interesse3, String interesse4, String interesse5){
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        perfilPersistencia.cadastrarPerfil(id,bio,nome,interesse1,interesse2, interesse3, interesse4, interesse5);
    }
}
