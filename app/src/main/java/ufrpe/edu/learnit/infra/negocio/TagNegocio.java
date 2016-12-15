package ufrpe.edu.learnit.infra.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.dominio.Session;
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

    public Tag retornarTag(String texto){
        TagPersistencia tagPersistencia = new TagPersistencia();
        return tagPersistencia.retornarTagTexto(texto);
    }

    public void inserirTag(String tag){
        TagPersistencia tagPersistencia = new TagPersistencia();
        tagPersistencia.inserirTag(tag);
    }

    public boolean existeTag(String tag){
        boolean result = true;
        TagPersistencia tagPersistencia = new TagPersistencia();
        if(tagPersistencia.retornarTagTexto(tag).getID()==0){
            result = false;
        }
        return  result;
    }

    public boolean existeRelacaoTagAula(int tagId){
        boolean result = true;
        TagPersistencia tagPersistencia = new TagPersistencia();
        if(tagPersistencia.existeRealcaoTagAula(Session.getAula().getId(),tagId)){
            result = false;
        }
        return  result;
    }

    public void inserirRelacaoTagAula(int tagId){
        TagPersistencia tagPersistencia = new TagPersistencia();
        tagPersistencia.inserirRelacaoTagAula(tagId,Session.getAula().getId());
    }
}
