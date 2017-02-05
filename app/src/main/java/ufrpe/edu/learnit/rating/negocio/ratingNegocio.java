package ufrpe.edu.learnit.rating.negocio;

import android.media.Rating;

import java.util.ArrayList;

import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.rating.persistencia.RatingPersistencia;

/**
 * Created by joel_ on 02/02/2017.
 */

public class RatingNegocio {

    public void novaAvaliacaoAula(int idPerfilAvaliador , int idItemAula , float avaliacao){
        RatingPersistencia ratingPersistencia = new RatingPersistencia();
        float resultado=ratingPersistencia.retornarAvaliacaoAula(idPerfilAvaliador ,idItemAula);
        if(resultado!=-1){
            ratingPersistencia.updateAvaliacaoAula(idPerfilAvaliador ,idItemAula ,avaliacao,resultado);
        }else{
            ratingPersistencia.novaAvaliacaoAula(idPerfilAvaliador,idItemAula,avaliacao);
        }
    }
    public void novaAvaliacaoPerfil(int idPerfilAvaliador , int idItemPerfil , float avaliacao) {
        RatingPersistencia ratingPersistencia = new RatingPersistencia();
        float resultado = ratingPersistencia.retornarAvaliacaoPerfil(idPerfilAvaliador, idItemPerfil);
        if (resultado !=0) {
            ratingPersistencia.updateAvaliacaoPerfil(idPerfilAvaliador, idItemPerfil, avaliacao, resultado);
        } else {
            ratingPersistencia.novaAvaliacaoPerfil(idPerfilAvaliador, idItemPerfil, avaliacao);
        }
    }

    public float retornarAvaliacaoPerfil(int idPerfilAvaliador , int idItemPerfil){
        RatingPersistencia ratingPersistencia = new RatingPersistencia();
        float resultado = ratingPersistencia.retornarAvaliacaoPerfil(idPerfilAvaliador, idItemPerfil);
        return resultado;
    }

    public float retornarAvaliacaoAula(int idPerfilAvaliador , int idItemAula){
        RatingPersistencia ratingPersistencia = new RatingPersistencia();
        float resultado = ratingPersistencia.retornarAvaliacaoAula(idPerfilAvaliador, idItemAula);
        return resultado;
    }
    public ArrayList<Perfil> retornarTodasAvaliacoesPerfil(int idPerfil){
        RatingPersistencia ratingPersistencia = new RatingPersistencia();
        ArrayList<Perfil> resultado = ratingPersistencia.retornarTodasAvaliacoesPerfil(idPerfil);
        return resultado;
    }

    public ArrayList<Perfil> retornarRatesIguais(int idItemPerfil){
        RatingPersistencia ratingPersistencia = new RatingPersistencia();
        return ratingPersistencia.retornarRatesIguais(idItemPerfil);
    }
}
