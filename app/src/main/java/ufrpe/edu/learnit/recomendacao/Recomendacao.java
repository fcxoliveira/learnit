package ufrpe.edu.learnit.recomendacao;
import java.util.HashMap;
import java.util.Map;

import ufrpe.edu.learnit.perfil.dominio.Perfil;


public class Recomendacao {
    Map<Integer,Map<Integer,Float>> mData;
    Map<Integer,Map<Integer,Float>> matrizDiferenca;
    Map<Integer,Map<Integer,Integer>> matrizFrequencia;

    public void setUserData(Map<Integer,Map<Integer,Float>> data) {
        mData = data;
        atualizar();
    }

    private void atualizar() {
        matrizDiferenca = new HashMap<>();
        matrizFrequencia = new HashMap<>();
        for(Map<Integer,Float> user : mData.values()) {
            for(Map.Entry<Integer,Float> entry: user.entrySet()) {
                if(!matrizDiferenca.containsKey(entry.getKey())) {
                    matrizDiferenca.put(entry.getKey(), new HashMap<Integer,Float>());
                    matrizFrequencia.put(entry.getKey(), new HashMap<Integer,Integer>());
                }
                for(Map.Entry<Integer,Float> entry2: user.entrySet()) {
                    int oldcount = 0;
                    if(matrizFrequencia.get(entry.getKey()).containsKey(entry2.getKey()))
                        oldcount = matrizFrequencia.get(entry.getKey()).get(entry2.getKey()).intValue();
                    float olddiff = 0.0f;
                    if(matrizDiferenca.get(entry.getKey()).containsKey(entry2.getKey()))
                        olddiff = matrizDiferenca.get(entry.getKey()).get(entry2.getKey()).floatValue();
                    float observeddiff = entry.getValue() - entry2.getValue();
                    matrizFrequencia.get(entry.getKey()).put(entry2.getKey(),oldcount + 1);
                    matrizDiferenca.get(entry.getKey()).put(entry2.getKey(),olddiff+observeddiff);
                }
            }
        }
        for (Integer j : matrizDiferenca.keySet()) {
            for (Integer i : matrizDiferenca.get(j).keySet()) {
                float oldvalue = matrizDiferenca.get(j).get(i).floatValue();
                int count = matrizFrequencia.get(j).get(i).intValue();
                matrizDiferenca.get(j).put(i,oldvalue/count);
            }
        }
    }
    public Map<Float,Integer> predizer(Map<Integer,Float> user) {
        HashMap<Float,Integer> predictions = new HashMap<>();
        HashMap<Integer,Integer> frequencies = new HashMap<>();
        for (Integer j : matrizDiferenca.keySet()) {
            predictions.put(0.0f,j);
            frequencies.put(j,0);
        }
        for (Integer j : user.keySet()) {
            for (Integer k : matrizDiferenca.keySet()) {
                try {
                Float aFloat = user.get(j);
                Float aFloat1 = matrizDiferenca.get(k).get(j);
                float newval = ( aFloat1 + aFloat) ;
                    for(Float key:predictions.keySet()){
                        if(predictions.get(key)==k){
                            predictions.put(key+newval,k);
                        }
                    }

                } catch(NullPointerException e) {}
            }
        }
        for (Float j : predictions.keySet()) {
            predictions.put(j/user.size(),predictions.get(j));
        }
        for (Integer j : user.keySet()) {
            predictions.put(user.get(j),j);
        }
        return predictions;
    }

}
