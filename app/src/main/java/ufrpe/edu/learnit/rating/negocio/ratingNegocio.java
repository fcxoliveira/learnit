package ufrpe.edu.learnit.rating.negocio;

import android.media.Rating;

import java.util.ArrayList;

import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.rating.persistencia.RatingPersistencia;

/**
 * Created by joel_ on 02/02/2017.
 */

public class RatingNegocio {

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

    public ArrayList<Integer> retornarTodasAvaliacoesPerfil(int idPerfil){
        RatingPersistencia ratingPersistencia = new RatingPersistencia();
        ArrayList<Integer> resultado = ratingPersistencia.retornarTodasAvaliacoesPerfil(idPerfil);
        return resultado;
    }


}
