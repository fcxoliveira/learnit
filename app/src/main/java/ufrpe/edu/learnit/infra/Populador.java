package ufrpe.edu.learnit.infra;

import java.util.Random;

import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;
import ufrpe.edu.learnit.perfil.persistencia.PerfilPersistencia;
import ufrpe.edu.learnit.tag.persistencia.TagPersistencia;
import ufrpe.edu.learnit.usuario.persistencia.UsuarioPersistencia;

/**
 * Created by silva on 30/01/2017.
 */

public class Populador {

    private String[] nomesLogin = {"lokaine", "setoper", "masterhunterxx1", "gcmaia", "blackprincess",
            "blackburn", "lokisora", "azren", "ramonrcm", "kingstank",
            "catfelina", "mastermoon", "igordragonblack", "goenji", "leonel",
            "tsunade", "jiraya", "orochimaru", "naruto", "sasuke",
            "kabuto", "moraisbsi", "princessgod", "ragnarl", "godrumala",
            "silva", "oliveirax", "barbosinha", "geeholiveira", "guylima"};
    private String[] nomesPerfis = {"Filipe Carlos","Felipe Morais","Matheus Campos","Hugo Teixeira","Igor Gomes",
            "Isabel Santiago","João Victor","André Araújo","Monaliza Albuquerque","Lisandra Aragão",
            "Aurinez Araújo","Jorge Matheus","Cassandra Villar","Cassia Oliveira","Caique Silva",
            "Carlos Eduardo","Afonso Rodolfo","Mateus Silva","Felipe Mei","José Romilson",
            "Gabriel Alves","Michael Jackson","Manoel Betmann","Angela Costa","João José Antônio",
            "Antonio Henrique","Flávio Daniel","Ícaro César","Íthalo José","José Maria"};
    private final String SENHA = "123456";
//    private String[] tags = {"Informatica", "Matematica", "Musica", "Biologia", "Linguas",
//            "Cultura", "Física", "Química", "Política", "Ética",
//            "Culinária", "Calistenia", "Esportes", "Cantoria", "Computação"};

    public void cadastrarUsuario(String login, String senha, String email){
        UsuarioPersistencia usuarioPersistencia = new UsuarioPersistencia();
        usuarioPersistencia.cadastrarUsuario(login, senha, email);
    }

    public void cadastrarPerfil(int id, String bio, String nome){
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        perfilPersistencia.cadastrarPerfil(id, bio, nome);
    }

    public void cadastrarAula(String titulo, String descricao, int duracao, int valor, int idPerfil){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        aulaPersistencia.cadastrarAulaPopulador(titulo, descricao, duracao, valor, idPerfil);
    }

//    public void inserirTag(String tag){
//        TagPersistencia tagPersistencia = new TagPersistencia();
//        tagPersistencia.inserirTag(tag);
//    }

    public void inserirRelacaoTagAula(int idTag, int idAula){
        TagPersistencia tagPersistencia = new TagPersistencia();
        tagPersistencia.inserirRelacaoTagAula(idTag, idAula);
    }

    public void inserirRelacaoTagPerfil(int idTag, int idPerfil){
        TagPersistencia tagPersistencia = new TagPersistencia();
        tagPersistencia.inserirRelacaoTagPerfil(idTag, idPerfil);
    }

//    public void trabalharTags(){
//        for (String tag : tags) {
//            inserirTag(tag);
//        }
//    }

    public void popularBancoDeDados(){
        //trabalharTags();
        Random gerador = new Random();
        int idAula = 1;
        int auxiliar = 0;
        int contador = 0;
        for (int i = 1; i<=nomesLogin.length; i++){
            String email = nomesLogin[i-1]+"@"+nomesLogin[i-1]+".com";
            cadastrarUsuario(nomesLogin[i-1], SENHA, email);
            String bio = "Teste de descrição para Perfil de "+nomesPerfis[i-1];
            cadastrarPerfil(i,bio,nomesPerfis[i-1]);
            int numInteresses = gerador.nextInt((3 - 1) + 1) + 1;

            for (int l = 0; l<numInteresses; l++){
                if (auxiliar > 8){
                    auxiliar = 0;
                }
                inserirRelacaoTagPerfil(auxiliar+1, i);
                auxiliar++;
            }

            for (int j = 1; j<6; j++) {
                String titulo = nomesPerfis[i-1]+" Aula " + j;
                String descricao = "Teste de descrição para Aula " + j +" de "+nomesPerfis[i-1];
                int valor = gerador.nextInt((50 - 20) + 1) + 20;
                int duracao = gerador.nextInt((100 - 5) + 1) + 5;
                cadastrarAula(titulo, descricao, duracao, valor, i);
                int numTags = gerador.nextInt((5 - 2) + 1) + 2;

                for (int k = 0; k<numTags; k++){
                    if (contador > 8) {
                        contador = 0;
                    }
                    inserirRelacaoTagAula(contador+1,idAula);
                    contador++;
                }
                idAula++;
            }
        }

    }
}
