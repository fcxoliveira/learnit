package ufrpe.edu.learnit.infra.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.infra.persistencia.TagPersistencia;

public class TagNegocio {

    public static boolean anyTagNotIsEmpty(ArrayList<Tag> tags){
        boolean anyTrue = false;
        for (Tag tag: tags ){
            if(tag.getID()!=1){
                anyTrue=true;
                return anyTrue;
            }
        }
        return anyTrue;
    }
    public ArrayList<Tag> retornarTodasTags(){
       TagPersistencia tagPersistencia = new TagPersistencia();
        return tagPersistencia.retornarTodasTags();
    }

    public ArrayList<Tag> retornarTagsPorTexto(String texto){
        TagPersistencia tagPersistencia = new TagPersistencia();
        return tagPersistencia.retornarTagPorTexto(texto);
    }

    public ArrayList<Tag> retornarTagsAula(int id){
        TagPersistencia tagPersistencia = new TagPersistencia();
        return tagPersistencia.retornarTagsAula(id);
    }
}
