package ufrpe.edu.learnit.recomendacao;
import java.util.HashMap;
import java.util.Map;


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
            frequencies.put(j,0);
            predictions.put(0.0f,j);
        }
        for (Integer j : user.keySet()) {
            for (Integer k : matrizDiferenca.keySet()) {
                try {
                    float newval = ( matrizDiferenca.get(k).get(j).floatValue() + user.get(j).floatValue() ) * matrizFrequencia.get(k).get(j).intValue();
                    predictions.put(predictions.get(k)+newval,k);
                    frequencies.put(k, frequencies.get(k)+matrizFrequencia.get(k).get(j).intValue());
                } catch(NullPointerException e) {}
            }
        }
        HashMap<Float,Integer> cleanpredictions = new HashMap<Float,Integer>();
        for (Integer j : predictions.values()) {
            if (frequencies.get(j)>0) {
                cleanpredictions.put(predictions.get(j).floatValue()/frequencies.get(j).intValue(),j);
            }
        }
        for (Integer j : user.keySet()) {
            cleanpredictions.put(user.get(j),j);
        }
        return cleanpredictions;
    }

}