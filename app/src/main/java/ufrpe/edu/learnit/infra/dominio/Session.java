package ufrpe.edu.learnit.infra.dominio;


import android.content.Context;

import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.usuario.dominio.Usuario;

public class Session {
    private static Usuario usuario;

    private static Context context;

    private static Perfil perfil;

    public static Perfil getPerfil() {
        return perfil;
    }

    public static void setPerfil(Perfil perfil) {
        Session.perfil = perfil;
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
