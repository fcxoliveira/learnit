package ufrpe.edu.learnit.recomendacao;

import java.util.HashMap;
import java.util.Map;

import ufrpe.edu.learnit.usuario.dominio.Usuario;

/**
 * Created by Filipe on 26/01/2017.
 */

public class Recomendacao {
    Map<Usuario, Map<Usuario, Float>> diferenca = new HashMap<>();
    Map<Usuario, Map<Usuario, Integer>> frequencia = new HashMap<>();

    public void atualizar(Map<Usuario, Map<Usuario, Float>> todosUsuariosESuasAvaliacoesProfessor) {
        for (Map<Usuario, Float> ratings : todosUsuariosESuasAvaliacoesProfessor.values()) {

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
                calculo /= frequencia.get(usuario3).get(usuario4);
                valor.put(usuario4, calculo);
            }
        }
    }

    public Map<Usuario, Float> predizer(Map<Usuario, Float> avaliacaoUsuario){
        Map<Usuario, Float> preds = new HashMap<>();
        Map<Usuario, Integer> freqs = new HashMap<>();
        for (Usuario usuario : avaliacaoUsuario.keySet()) {
            Float nota = avaliacaoUsuario.get(usuario);
            for (Usuario usuario2 : this.diferenca.keySet()) {
                Map<Usuario, Float> valor = this.diferenca.get(usuario2);
                int freq = this.frequencia.get(usuario2).get(usuario);
                preds.put(usuario2, 0.0f);
                freqs.put(usuario2, 0);
                Float aux1 = preds.get(usuario2);
                aux1 += freq * (valor.get(usuario) + nota);
                preds.put(usuario2, aux1);
                Integer aux2 = freqs.get(usuario2);
                aux2++;
                freqs.put(usuario2, aux2);
            }
        }
        Map<Usuario, Float> resultado = new HashMap<>();
        for (Usuario usuario :
                preds.keySet()) {
            float nota = preds.get(usuario);
            if (!preds.containsKey(usuario) && (freqs.get(usuario) > 0)){
                resultado.put(usuario, (nota/freqs.get(usuario)));
            }
        }
        return resultado;
    }

}