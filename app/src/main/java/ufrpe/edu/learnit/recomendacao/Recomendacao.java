package ufrpe.edu.learnit.recomendacao;

import android.test.mock.MockApplication;

import java.util.HashMap;
import java.util.Map;

import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.usuario.dominio.Usuario;

/**
 * Created by Filipe on 26/01/2017.
 */

public class Recomendacao {
    Map<Usuario, Map<Usuario, Float>> diferenca = new HashMap<>();
    Map<Usuario, Map<Usuario, Integer>> frequencia = new HashMap<>();

    public void atualizar(Map<Usuario, Map<Usuario, Float>> userData) {
        for (Map<Usuario, Float> ratings : userData.values()) {

            for (Usuario usuario1 : ratings.keySet()) {

                float valor1 = ratings.get(usuario1);
                frequencia.put(usuario1, new HashMap<Usuario, Integer>());
                diferenca.put(usuario1, new HashMap<Usuario, Float>());

                for (Usuario usuario2 : ratings.keySet()) {

                    float valor2 = ratings.get(usuario2);
                    frequencia.get(usuario1).put(usuario2, 0);
                    diferenca.get(usuario1).put(usuario2, 0.0f);
                    Integer quantidade = frequencia.get(usuario1).get(usuario2);
                    quantidade++;
                    frequencia.get(usuario1).put(usuario2, quantidade);
                    Float diferencas = diferenca.get(usuario1).get(usuario2);
                    diferencas += valor1 - valor2;
                    diferenca.get(usuario1).put(usuario2, diferencas);
                }
            }

        }

        for (Usuario usuario3: diferenca.keySet()) {
            Map<Usuario, Float> valor = diferenca.get(usuario3);
            for (Usuario usuario4: valor.keySet()) {
                Float calculo = valor.get(usuario4);
                calculo/= frequencia.get(usuario3).get(usuario4);
            }
        }
    }

}