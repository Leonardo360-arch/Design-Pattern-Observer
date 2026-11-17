import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Exemplo didatico: Observer com interface Java Swing.
// Nenhum e-mail real e enviado e nenhum dado e salvo.
public class Main {
    interface Observador {
        void atualizar(String aluno);
    }

    static class RegistroAusencia {
        private final List<Observador> observadores = new ArrayList<>();
        private final List<String> ausencias = new ArrayList<>();

        int totalAusencias() {
            return ausencias.size();
        }

        void inscrever(Observador observador) {
            if (!observadores.contains(observador)) observadores.add(observador);
        }

        void cancelarInscricao(Observador observador) {
            observadores.remove(observador);
        }

        void registrar(String aluno) {
            ausencias.add(aluno); // Mudanca de estado antes das notificacoes
            for (Observador observador : List.copyOf(observadores)) {
                observador.atualizar(aluno);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::criarTela);
    }

    private static void criarTela() {
        JFrame janela = new JFrame("SAA - Demonstracao do Observer");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(630, 430);
        janela.setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout(12, 12));
        painel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JLabel titulo = new JLabel("Sistema de Ausencia de Alunos - Observer");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        painel.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        JPanel linhaAluno = new JPanel(new BorderLayout(8, 0));
        JTextField campoAluno = new JTextField();
        linhaAluno.add(new JLabel("Aluno:"), BorderLayout.WEST);
        linhaAluno.add(campoAluno, BorderLayout.CENTER);
        formulario.add(linhaAluno);
        formulario.add(Box.createVerticalStrut(12));

        JCheckBox responsavel = new JCheckBox("Responsavel inscrito (e-mail simulado)", true);
        JCheckBox coordenacao = new JCheckBox("Coordenacao inscrita", true);
        formulario.add(responsavel);
        formulario.add(coordenacao);
        formulario.add(Box.createVerticalStrut(12));

        JButton botao = new JButton("Registrar ausencia");
        formulario.add(botao);
        painel.add(formulario, BorderLayout.CENTER);

        JTextArea historico = new JTextArea(9, 45);
        historico.setEditable(false);
        historico.setLineWrap(true);
        historico.setWrapStyleWord(true);
        JPanel resultado = new JPanel(new BorderLayout(0, 6));
        resultado.add(new JLabel("Historico de eventos (somente nesta execucao):"), BorderLayout.NORTH);
        resultado.add(new JScrollPane(historico), BorderLayout.CENTER);
        resultado.setPreferredSize(new Dimension(550, 185));
        painel.add(resultado, BorderLayout.SOUTH);

        RegistroAusencia registro = new RegistroAusencia();
        Observador email = aluno -> historico.append("E-mail simulado ao responsavel: " + aluno + " faltou.\n");
        Observador avisoCoordenacao = aluno -> historico.append("Coordenacao avisada: ausencia de " + aluno + ".\n");

        // Inscricao e cancelamento mostram como o Observer funciona.
        registro.inscrever(email);
        registro.inscrever(avisoCoordenacao);
        responsavel.addActionListener(e -> {
            if (responsavel.isSelected()) registro.inscrever(email);
            else registro.cancelarInscricao(email);
        });
        coordenacao.addActionListener(e -> {
            if (coordenacao.isSelected()) registro.inscrever(avisoCoordenacao);
            else registro.cancelarInscricao(avisoCoordenacao);
        });

        botao.addActionListener(e -> {
            String aluno = campoAluno.getText().trim();
            if (aluno.isEmpty()) {
                JOptionPane.showMessageDialog(janela, "Informe o nome do aluno.");
                return;
            }
            registro.registrar(aluno);
            historico.append("Ausencia registrada: " + aluno
                    + " | Total em memoria: " + registro.totalAusencias() + ".\n");
            historico.append("---\n");
            campoAluno.setText("");
            campoAluno.requestFocusInWindow();
        });
        janela.setContentPane(painel);
        janela.setVisible(true);
    }
}
