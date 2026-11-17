import java.util.ArrayList;
import java.util.List;

/** Teste sem bibliotecas externas: execute ObserverSmokeTest.main(). */
public class ObserverSmokeTest {
    public static void main(String[] args) {
        Main.RegistroAusencia registro = new Main.RegistroAusencia();
        List<String> chamadas = new ArrayList<>();
        Main.Observador email = aluno -> chamadas.add("email:" + aluno);
        Main.Observador coordenacao = aluno -> chamadas.add("coord:" + aluno);

        registro.inscrever(email);
        registro.inscrever(email); // nao deve duplicar
        registro.inscrever(coordenacao);
        registro.registrar("Joao");
        verificar(chamadas.equals(List.of("email:Joao", "coord:Joao")), "Dois inscritos");

        registro.cancelarInscricao(email);
        registro.registrar("Maria");
        verificar(chamadas.equals(List.of("email:Joao", "coord:Joao", "coord:Maria")), "Um inscrito");

        registro.cancelarInscricao(coordenacao);
        registro.registrar("Ana");
        verificar(chamadas.size() == 3 && registro.totalAusencias() == 3, "Zero inscritos e estado");

        registro.inscrever(email);
        registro.registrar("Carlos");
        verificar(chamadas.size() == 4 && "email:Carlos".equals(chamadas.get(3))
                && registro.totalAusencias() == 4, "Reinscricao");

        System.out.println("PASS: inscricao, cancelamento, zero observadores, reinscricao e estado.");
    }

    private static void verificar(boolean condicao, String cenario) {
        if (!condicao) throw new AssertionError("Falha: " + cenario);
    }
}
