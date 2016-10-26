package ufrpe.edu.learnit.aula.dominio;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.dominio.Session;

/**
 * Created by silva on 26/10/2016.
 */

public class Aula {
    private String titulo, descricao;
    private double valor;
    private long id;
    private boolean ativa;
    private ArrayList<String> tags = new ArrayList<String>();

//    public void inscrever(){
//        Session.getUsuario().getPerfil().inscreverAula(this);
//    }
}
