package ufrpe.edu.learnit.rating.negocio;

import android.media.Rating;

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
        if (resultado != -1) {
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
}
