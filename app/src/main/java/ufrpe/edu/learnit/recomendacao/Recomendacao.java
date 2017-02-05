package ufrpe.edu.learnit.recomendacao;
import java.util.HashMap;
import java.util.Map;

import ufrpe.edu.learnit.perfil.dominio.Perfil;


public class Recomendacao {
    Map<Perfil,Map<Perfil,Float>> mData;
    Map<Perfil,Map<Perfil,Float>> matrizDiferenca;
    Map<Perfil,Map<Perfil,Integer>> matrizFrequencia;

    public void setUserData(Map<Perfil,Map<Perfil,Float>> data) {
        mData = data;
        atualizar();
    }

    private void atualizar() {
        matrizDiferenca = new HashMap<>();
        matrizFrequencia = new HashMap<>();
        for(Map<Perfil,Float> user : mData.values()) {
            for(Map.Entry<Perfil,Float> entry: user.entrySet()) {
                if(!matrizDiferenca.containsKey(entry.getKey())) {
                    matrizDiferenca.put(entry.getKey(), new HashMap<Perfil,Float>());
                    matrizFrequencia.put(entry.getKey(), new HashMap<Perfil,Integer>());
                }
                for(Map.Entry<Perfil,Float> entry2: user.entrySet()) {
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
        for (Perfil j : matrizDiferenca.keySet()) {
            for (Perfil i : matrizDiferenca.get(j).keySet()) {
                float oldvalue = matrizDiferenca.get(j).get(i).floatValue();
                int count = matrizFrequencia.get(j).get(i).intValue();
                matrizDiferenca.get(j).put(i,oldvalue/count);
            }
        }
    }
    public Map<Float,Perfil> predizer(Map<Perfil,Float> user) {
        HashMap<Float,Perfil> predictions = new HashMap<>();
        HashMap<Perfil,Integer> frequencies = new HashMap<>();
        for (Perfil j : matrizDiferenca.keySet()) {
            predictions.put(0.0f,j);
            frequencies.put(j,0);
        }
        for (Perfil j : user.keySet()) {
            for (Perfil k : matrizDiferenca.keySet()) {
                try {
                Float aFloat = user.get(j);
                Float aFloat1 = matrizDiferenca.get(k).get(j);
                float newval = ( aFloat1 + aFloat) ;
                    for(Float key:predictions.keySet()){
                        if(predictions.get(key).getId()==k.getId()){
                            predictions.put(key+newval,k);
                        }
                    }

                } catch(NullPointerException e) {}
            }
        }
        for (Float j : predictions.keySet()) {
            predictions.put(j/user.size(),predictions.get(j));
        }
        for (Perfil j : user.keySet()) {
            predictions.put(user.get(j),j);
        }
        return predictions;
    }

}
