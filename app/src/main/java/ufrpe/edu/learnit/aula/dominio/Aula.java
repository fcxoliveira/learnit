package ufrpe.edu.learnit.aula.dominio;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.perfil.dominio.Perfil;

/**
 * Created by silva on 26/10/2016.
 */

public class Aula {

    private String nome;
    private int horas;
    private ArrayList<Tag> interesses;
    private Perfil perfil;
    private String titulo, descricao;
    private double valor;
    private long id;
    private boolean ativa;
    private int duracaoHorasAula;
    private ArrayList<Tag> tags = new ArrayList<Tag>();

    public ArrayList<Tag> getInteresses() {return interesses;}

    public void setInteresses(ArrayList<Tag> interesses) {this.interesses = interesses;}

    public int getHoras() {return horas;}

    public void setHoras(int horas) {this.horas = horas;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}
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

    @Override
    public String toString(){
        return getTitulo()+"\tValor: "+getValor()+"\n\n"+getDescricao();
    }

//    public void inscrever(){
//        Session.getUsuario().getPerfil().inscreverAula(this);
//    }
}
