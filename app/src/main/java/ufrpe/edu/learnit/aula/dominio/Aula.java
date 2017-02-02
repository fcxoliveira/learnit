package ufrpe.edu.learnit.aula.dominio;

import ufrpe.edu.learnit.perfil.dominio.Perfil;

public class Aula {

    private int horas;
    private Perfil perfil;
    private String titulo, descricao;
    private int valor;
    private int id;
    private int avaliadores;
    private float avaliacao;

    public int getAvaliadores() {return avaliadores;}

    public void setAvaliadores(int avaliadores) {this.avaliadores = avaliadores;}

    public float getAvaliacao() {return avaliacao;}

    public void setAvaliacao(float avaliacao) {this.avaliacao = avaliacao;}

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return getTitulo() + "\tValor: " + getValor() + "\n\n" + getDescricao();
    }
}

