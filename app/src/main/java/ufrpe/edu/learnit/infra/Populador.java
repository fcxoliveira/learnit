package ufrpe.edu.learnit.infra;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;
import ufrpe.edu.learnit.perfil.persistencia.PerfilPersistencia;
import ufrpe.edu.learnit.tag.persistencia.TagPersistencia;
import ufrpe.edu.learnit.usuario.dominio.Usuario;
import ufrpe.edu.learnit.usuario.persistencia.UsuarioPersistencia;

/**
 * Created by silva and Felipe Morais on 30/01/2017.
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

    public void inserirRelacaoTagAula(int idTag, int idAula){
        TagPersistencia tagPersistencia = new TagPersistencia();
        tagPersistencia.inserirRelacaoTagAula(idTag, idAula);
    }

    public void inserirRelacaoTagPerfil(int idTag, int idPerfil){
        TagPersistencia tagPersistencia = new TagPersistencia();
        tagPersistencia.inserirRelacaoTagPerfil(idTag, idPerfil);
    }

    public void inscreverAlunoEmAula(int idAluno, int idAula, String date, int horas, int valorPago){
        AulaPersistencia aula = new AulaPersistencia();
        aula.inscreverAlunoEmAulaPopulador(idAluno, idAula, date, horas, valorPago);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void popularBancoDeDados(){
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
                if (auxiliar > 7){
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
                    if (contador > 7) {
                        contador = 0;
                    }
                    inserirRelacaoTagAula(contador+1,idAula);
                    contador++;
                }
                idAula++;
            }
        }

    }

    public void comprarAulas(){
        Random gerador = new Random();
        UsuarioPersistencia usuarioPersistencia = new UsuarioPersistencia();
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        for (int idAula = 1; idAula<151; idAula++) {
            for (int i = 1; i < 6; i++) {
                int horas = gerador.nextInt((10 - 3) + 1) + 3;
                inscreverAlunoEmAula((idAula%5)+1,idAula, getDateTime(), horas, aulaPersistencia.retornarAula(idAula).getValor()*horas);

            }
        }
    }
}
