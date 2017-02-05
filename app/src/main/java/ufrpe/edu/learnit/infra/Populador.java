package ufrpe.edu.learnit.infra;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import ufrpe.edu.learnit.aula.dominio.AlunoAula;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;
import ufrpe.edu.learnit.confirmacao.dominio.Confirmacao;
import ufrpe.edu.learnit.confirmacao.persistencia.ConfirmacaoPersistencia;
import ufrpe.edu.learnit.perfil.persistencia.PerfilPersistencia;
import ufrpe.edu.learnit.rating.persistencia.RatingPersistencia;
import ufrpe.edu.learnit.tag.persistencia.TagPersistencia;
import ufrpe.edu.learnit.usuario.dominio.Usuario;
import ufrpe.edu.learnit.usuario.persistencia.UsuarioPersistencia;

/**
 * Created by silva and Felipe Morais on 30/01/2017.
 */

public class Populador {

    private String[] nomesLogin = {"lokaine", "setoper", "masterhunterxx1", "gcmaia", "blackprincess",
            "blackburn", "lokisora", "azren", "ramonrcm"};
    private String[] nomesPerfis = {"Filipe Carlos","Felipe Morais","Matheus Campos","Hugo Teixeira","Igor Gomes",
            "Isabel Santiago","João Victor","André Araújo","Monaliza Albuquerque"};
    private final String SENHA = "123456";

    private void cadastrarUsuario(String login, String senha, String email){
        UsuarioPersistencia usuarioPersistencia = new UsuarioPersistencia();
        usuarioPersistencia.cadastrarUsuario(login, senha, email);
    }

    private void cadastrarPerfil(int id, String bio, String nome){
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        perfilPersistencia.cadastrarPerfil(id, bio, nome);

    }

    private void cadastrarAula(String titulo, String descricao, int duracao, int valor, int idPerfil){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        aulaPersistencia.cadastrarAulaPopulador(titulo, descricao, duracao, valor, idPerfil);
    }

    private void inserirRelacaoTagAula(int idTag, int idAula){
        TagPersistencia tagPersistencia = new TagPersistencia();
        tagPersistencia.inserirRelacaoTagAula(idTag, idAula);
    }

    private void inserirRelacaoTagPerfil(int idTag, int idPerfil){
        TagPersistencia tagPersistencia = new TagPersistencia();
        tagPersistencia.inserirRelacaoTagPerfil(idTag, idPerfil);
    }

    private void inscreverAlunoEmAula(int idAluno, int idAula, String date, int horas, int valorPago){
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

            for (int j = 1; j<3; j++) {
                String titulo = nomesPerfis[i-1]+" Aula " + j;
                String descricao = "Teste de descrição para Aula " + j +" de "+nomesPerfis[i-1];
                int valor = gerador.nextInt((50 - 20) + 1) + 20;
                int duracao = gerador.nextInt((100 - 30) + 1) + 5;
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
        comprarAulas();
    }

    private void comprarAulas(){
        Random gerador = new Random();
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        for (int idUsuario = 1; idUsuario<31; idUsuario++){
            ArrayList<Aula> aulas = aulaPersistencia.getAulasPorTexto("",idUsuario);
            ArrayList<Aula> aulasJaCompradas = new ArrayList<>();
            for (int i = 0; i<5; i++){
                int horas = gerador.nextInt((10 - 3) + 1) + 3;
                int index = gerador.nextInt(((aulas.size()-1)-1) + 1) + 1;
                Aula aula = aulas.get(index-1);
                boolean anticompra = false;
                for (Aula aux : aulasJaCompradas) {
                    if (aux.getId() == aula.getId()) {
                        anticompra = true;
                    }
                }
                if (anticompra){
                    continue;
                }
                inscreverAlunoEmAula(idUsuario, aula.getId(), getDateTime(), horas, aulaPersistencia.retornarAula(aulas.get(index).getId()).getValor()*horas);
                aulasJaCompradas.add(aula);
            }
        }
        confirmarAulasERatear();
    }

    private void confirmarAulasERatear(){
        UsuarioPersistencia usuarioPersistencia = new UsuarioPersistencia();
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        RatingPersistencia ratingPersistencia = new RatingPersistencia();
        Random gerador = new Random();
        for (int idUsuario = 1; idUsuario<10; idUsuario++){
            Usuario usuario = usuarioPersistencia.retornarUsuario(idUsuario);
            ArrayList<AlunoAula> aulasCompradas = aulaPersistencia.retornarAulasCompradas(idUsuario);
            for (AlunoAula alunoAula : aulasCompradas) {
                Aula aula = alunoAula.getAula();
                int max = alunoAula.getHorasTotal();
                int min = 1;
                confirmacaoPersistencia.enviarConfirmacao(aula.getId(), idUsuario, gerador.nextInt((max - min) + 1) + min, 0);
                Confirmacao confirmacao = confirmacaoPersistencia.retornarConfirmacaoRecebidaPopulador(aula.getId(), idUsuario);
                confirmacaoPersistencia.aceitarConfirmacao(confirmacao);
                ratingPersistencia.novaAvaliacaoPerfil(usuario.getID(), aula.getPerfil().getId(), gerador.nextInt((5 - 2) + 1) +1);
                ratingPersistencia.novaAvaliacaoAula(usuario.getID(), aula.getId(), gerador.nextInt((5 - 2) + 1) +1);
            }
        }
    }
}
