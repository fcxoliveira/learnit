package ufrpe.edu.learnit.infra.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.dominio.Tag;

/**
 * Created by joel_ on 28/10/2016.
 */

public class TagNegocio {

    public static boolean anyTagNotIs1(ArrayList<Tag> tags){
        boolean anyTrue = false;
        for (Tag tag: tags ){
            if(tag.getID()!=1){
                anyTrue=true;
                return anyTrue;
            }
        }
        return anyTrue;
    }
}