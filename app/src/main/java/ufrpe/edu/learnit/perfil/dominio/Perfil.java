package ufrpe.edu.learnit.perfil.dominio;

import java.util.ArrayList;

import ufrpe.edu.learnit.tag.dominio.Tag;

public class Perfil {

    private int id;
    private String bio;
    private String nome;
    private int moedas;
    private ArrayList<Tag> interesses;
    private float avaliacao;
    private int horas;
    private int avaliadores;
    private boolean selected = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getAvaliadores() {
        return avaliadores;
    }

    public void setAvaliadores(int avaliadores) {
        this.avaliadores = avaliadores;
    }

    public String getBio() {return bio; }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMoedas() {
        return moedas;
    }

    public void setMoedas(int moedas) {
        this.moedas = moedas;
    }

    public ArrayList<Tag> getInteresses() {
        return interesses;
    }

    public void setInteresses(ArrayList<Tag> interesses) {
        this.interesses = interesses;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
    }

}
