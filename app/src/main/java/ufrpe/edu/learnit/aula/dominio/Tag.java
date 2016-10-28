package ufrpe.edu.learnit.aula.dominio;


public class Tag {
    private String titulo;
    private int ID;

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return getTitulo();
    }
}
