package ufrpe.edu.learnit.confirmacao.dominio;

/**
 * Created by joel_ on 16/12/2016.
 */

public class Confirmacao {
    private int idAula;
    private int idAluno;
    private int horasConfirmadas;
    private int status;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAula() {
        return idAula;
    }

    public void setIdAula(int idAula) {
        this.idAula = idAula;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public int getHorasConfirmadas() {
        return horasConfirmadas;
    }

    public void setHorasConfirmadas(int horasConfirmadas) {
        this.horasConfirmadas = horasConfirmadas;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
