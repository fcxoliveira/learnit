package ufrpe.edu.learnit.perfil.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.persistencia.PerfilPersistencia;

public class PerfilNegocio {

    public Perfil retornarPerfil(int id){
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        return perfilPersistencia.retornarPerfil(id);
    }

    public void cadastrarPerfil(int id, String bio, String nome){
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        perfilPersistencia.cadastrarPerfil(id,bio,nome);
    }

    public void editarPerfil(int id, String bio, String nome){
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        perfilPersistencia.editarPerfil(id,bio,nome);
    }

    public void addMoedas(int moedas) {
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        perfilPersistencia.addMoeda(moedas);
    }
    public void setNota(int idPerfil,float avaliacao){
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        perfilPersistencia.setAvaliacao(idPerfil,avaliacao);
    }
    public ArrayList<Integer> retornarTodosOsPerfis(){
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        return perfilPersistencia.retornarTodosOsPerfis();
    }
}
