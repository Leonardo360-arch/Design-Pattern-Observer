import java.util.concurrent.atomic.AtomicInteger;

/** Teste automatico sem JUnit. Execute TesteObserver.main() no IntelliJ. */
public class TesteObserver {
    public static void main(String[] args) {
        Main.RegistroAusencia registro = new Main.RegistroAusencia();
        AtomicInteger a = new AtomicInteger();
        AtomicInteger b = new AtomicInteger();
        Main.Observador primeiro = aluno -> a.incrementAndGet();
        Main.Observador segundo = aluno -> b.incrementAndGet();

        registro.inscrever(primeiro);
        registro.inscrever(segundo);
        registro.inscrever(primeiro); // Inscricao repetida nao duplica aviso
        registro.registrar("Joao");
        verificar(registro.totalAusencias() == 1 && a.get() == 1 && b.get() == 1, "dois inscritos");

        registro.cancelarInscricao(primeiro);
        registro.registrar("Maria");
        verificar(registro.totalAusencias() == 2 && a.get() == 1 && b.get() == 2, "cancelamento");

        registro.cancelarInscricao(segundo);
        registro.registrar("Ana");
        verificar(registro.totalAusencias() == 3 && a.get() == 1 && b.get() == 2, "nenhum inscrito");

        registro.inscrever(primeiro);
        registro.registrar("Pedro");
        verificar(registro.totalAusencias() == 4 && a.get() == 2 && b.get() == 2, "reinscricao");

        System.out.println("OK: 4 cenarios Observer passaram.");
    }

    private static void verificar(boolean condicao, String cenario) {
        if (!condicao) throw new AssertionError("Falha no teste: " + cenario);
    }
}
