package ufrpe.edu.learnit.infra.dominio;


import android.content.Context;

import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.usuario.dominio.Usuario;

public class Session {
    private static Usuario usuario;

    private static Context context;

    private static Aula aula;

    public static Aula getAula() {
        return aula;
    }

    public static void setAula(Aula aula) {
        Session.aula = aula;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Session.context = context;
    }


    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Session.usuario = usuario;
    }
}