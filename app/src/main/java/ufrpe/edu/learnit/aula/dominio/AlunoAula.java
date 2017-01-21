package ufrpe.edu.learnit.aula.dominio;

import ufrpe.edu.learnit.perfil.dominio.Perfil;

public class AlunoAula {

    private int id;
    private Aula aula;
    private Perfil perfil;
    private String date;
    private int horasTotal;
    private int horasConfirmadas;
    private int valorTotal;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getHorasConfirmadas() {return horasConfirmadas;}

    public void setHorasConfirmadas(int horasConfirmadas) {this.horasConfirmadas = horasConfirmadas;}

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getHorasTotal() {
        return horasTotal;
    }

    public void setHorasTotal(int horasTotal) {
        this.horasTotal = horasTotal;
    }
}
