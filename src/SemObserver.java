/**
 * ANTES da refatoracao: as notificacoes ficam acopladas ao registro.
 * Exemplo simples para comparar com o Observer.
 */
public class SemObserver {
    static class RegistroAusencia {
        void registrar(String aluno) {
            System.out.println("Ausencia registrada: " + aluno);
            enviarEmail(aluno);
            avisarCoordenacao(aluno);
        }

        private void enviarEmail(String aluno) {
            System.out.println("E-mail simulado: " + aluno + " faltou.");
        }

        private void avisarCoordenacao(String aluno) {
            System.out.println("Coordenacao avisada sobre " + aluno + ".");
        }
    }

    public static void main(String[] args) {
        new RegistroAusencia().registrar("Joao Silva");
    }
}
