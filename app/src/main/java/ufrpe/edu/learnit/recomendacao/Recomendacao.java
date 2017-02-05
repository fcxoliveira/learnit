package ufrpe.edu.learnit.recomendacao;
import java.util.HashMap;
import java.util.Map;

import ufrpe.edu.learnit.perfil.dominio.Perfil;


public class Recomendacao {
    Map<Perfil, Map<Perfil, Float>> diferenca = new HashMap<>();
    Map<Perfil, Map<Perfil, Integer>> frequencia = new HashMap<>();

    public void atualizar(Map<Perfil, Map<Perfil, Float>> userData) {
        for (Map<Perfil, Float> ratings : userData.values()) {

            for (Perfil usuario1 : ratings.keySet()) {

                float valor1 = ratings.get(usuario1);
                frequencia.put(usuario1, new HashMap<Perfil, Integer>());
                diferenca.put(usuario1, new HashMap<Perfil, Float>());

                for (Perfil usuario2 : ratings.keySet()) {

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

        for (Perfil usuario3 : diferenca.keySet()) {
            Map<Perfil, Float> valor = diferenca.get(usuario3);
            for (Perfil usuario4 : valor.keySet()) {
                Float calculo = valor.get(usuario4);
                calculo /= frequencia.get(usuario3).get(usuario4);
                valor.put(usuario4, calculo);

            }
        }
    }

    public Map<Float, Perfil> predizer(HashMap<Perfil, Float> avaliacaoUsuario, Map<Perfil, Map<Perfil, Float>> dadosDeTodosUsuarios) {
        this.atualizar(dadosDeTodosUsuarios);
        Map<Perfil, Float> preds = new HashMap<>();
        Map<Perfil, Integer> freqs = new HashMap<>();
        for (Perfil usuario : avaliacaoUsuario.keySet()) {
            Float nota = avaliacaoUsuario.get(usuario);
            for (Perfil usuario2 : this.diferenca.keySet()) {
                Map<Perfil, Float> valor = this.diferenca.get(usuario2);
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
        Map<Float, Perfil> resultado = new HashMap<>();
        for (Perfil usuario :
                preds.keySet()) {
            float nota = preds.get(usuario);
            if (!preds.containsKey(usuario) && (freqs.get(usuario) > 0)) {
                resultado.put((nota / freqs.get(usuario)), usuario);
            }
        }
        return resultado;
    }
}