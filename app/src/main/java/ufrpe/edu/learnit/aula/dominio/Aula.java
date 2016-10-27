package ufrpe.edu.learnit.aula.dominio;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;

/**
 * Created by silva on 26/10/2016.
 */

public class Aula {
    private Perfil perfil;
    private String titulo, descricao;
    private double valor;
    private long id;
    private boolean ativa;
    private int duracaoHorasAula;
    private ArrayList<Tag> tags = new ArrayList<Tag>();

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public int getDuracaoHorasAula() {
        return duracaoHorasAula;
    }

    public void setDuracaoHorasAula(int duracaoHorasAula) {
        this.duracaoHorasAula = duracaoHorasAula;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }


//    public void inscrever(){
//        Session.getUsuario().getPerfil().inscreverAula(this);
//    }
}
