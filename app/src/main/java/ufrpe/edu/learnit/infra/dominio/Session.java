package ufrpe.edu.learnit.infra.dominio;


import ufrpe.edu.learnit.dominio.Usuario;

public class Session {
    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Session.usuario = usuario;
    }

    private static Usuario usuario;
}
