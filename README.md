# Design Pattern Observer — SAA (Java Swing)

Exemplo **pequeno e didático** do padrão comportamental **Observer**, inspirado no PI **Sistema de Ausência de Alunos (SAA)**.

Quando uma ausência é registrada, o objeto observado (`RegistroAusencia`) avisa os observadores inscritos. A tela possui duas inscrições independentes: **responsável** (e-mail simulado) e **coordenação**. Desmarque uma opção e registre outra ausência para verificar que aquele observador deixa de receber o evento.

**Limitações:** não envia e-mails reais, não utiliza banco de dados, não possui login e não persiste histórico. Os nomes digitados são apenas dados de demonstração; prefira nomes fictícios.

## Arquivos para a apresentação

- [src/SemObserver.java](src/SemObserver.java) — exemplo inicial com chamadas diretas e alto acoplamento.
- [src/Main.java](src/Main.java) — solução Observer com interface Swing, estado em memória e inscrição dinâmica.
- [src/ObserverSmokeTest.java](src/ObserverSmokeTest.java) — testes automáticos da lógica, sem dependências externas.
- [docs/ROTEIRO_APRESENTACAO.md](docs/ROTEIRO_APRESENTACAO.md) — roteiro de 15 minutos, análise e referências.

**Ordem recomendada:** explique e execute `SemObserver.main()`, depois abra `Main.main()`, compare as responsabilidades e demonstre os checkboxes.

## Requisitos

- IntelliJ IDEA Community ou Ultimate.
- JDK **17 ou superior** configurado no projeto.
- Nenhuma dependência externa, Maven ou Gradle.

## Executar no IntelliJ IDEA (passo a passo)

1. Abra o IntelliJ e clique em **File > New > Project**.
2. Selecione **Java**, informe um nome como `ObserverSAA` e escolha o **JDK 17 ou superior**. Se não houver um JDK instalado, use a opção **Download JDK** do IntelliJ.
3. Crie o projeto. Na pasta **src**, crie um arquivo Java chamado **Main.java**.
4. Copie para esse arquivo o conteúdo de [Main.java](src/Main.java), disponível neste repositório. **Não adicione a linha `package`**.
5. Aguarde a indexação e clique no triângulo verde ao lado de `public static void main`; escolha **Run 'Main.main()'**.
6. Digite **João Silva** e clique em **Registrar ausência**. As duas notificações simuladas aparecerão no histórico.
7. Desmarque **Responsável inscrito** e registre **Maria Souza**. Agora somente a coordenação receberá a notificação.
8. Desmarque as duas opções e registre outra ausência. O registro aparecerá, mas nenhum observador será notificado.
9. Marque novamente as opções para reinscrever os observadores.

Se o IntelliJ não localizar o Java, abra **File > Project Structure > Project > SDK** e selecione ou baixe um JDK. Se a execução não aparecer, confirme que o arquivo se chama **Main.java** e contém a classe `public class Main`.

## Executar pelo terminal (opcional)

Na raiz do repositório, com JDK 17+:

```bash
javac -encoding UTF-8 -d out src/Main.java
java -cp out Main
```

## Executar todos os exemplos e testes no terminal (opcional)

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out SemObserver
java -cp out ObserverSmokeTest
java -cp out Main
```

O teste imprime `PASS` quando inscrição, cancelamento, ausência de observadores, reinscrição e estado em memória estão corretos. No IntelliJ, você pode executar separadamente `SemObserver.main()`, `ObserverSmokeTest.main()` e `Main.main()` pelos ícones verdes.

## Relação com SOLID e justificativa

- **SRP:** o registro de ausências não implementa o envio de e-mails ou avisos; os observadores assumem essas reações.
- **OCP:** é possível adicionar novos tipos de observadores sem alterar o laço que notifica os inscritos.
- **DIP (parcial):** `RegistroAusencia` depende da abstração `Observador`, não das implementações concretas.
- **Quando usar:** múltiplos componentes precisam reagir a mudanças; **quando evitar:** um único fluxo simples e estável pode dispensar o padrão.

Para demonstração, as notificações são síncronas e simuladas. O estado é a lista de ausências mantida em memória e se perde ao fechar a aplicação.

## Classes e papéis

| Elemento | Papel no Observer |
| --- | --- |
| `Observador` | Interface com o método `atualizar(aluno)` |
| `RegistroAusencia` | Subject: mantém a lista de inscritos e notifica |
| `email` e `avisoCoordenacao` | Observadores concretos, implementados por expressões lambda |
| `JCheckBox` | Controla a inscrição e o cancelamento dos observadores |
| Botão **Registrar ausência** | Dispara o evento e a notificação |

**Observação:** pedir permissão não é requisito do padrão Observer. Inscrever ou desinscrever observadores é parte do mecanismo; a autorização do destinatário é uma regra de negócio que pode determinar quando inscrevê-lo.

## Testes manuais rápidos

1. Campo de aluno vazio: aparece uma mensagem e nenhuma ausência é registrada.
2. Ambos inscritos: surgem duas notificações para cada ausência.
3. Apenas responsável inscrito: surge somente o e-mail simulado.
4. Apenas coordenação inscrita: surge somente o aviso da coordenação.
5. Nenhum inscrito: a ausência é registrada, sem notificação.
6. Reinscrição: ao remarcar uma opção, o observador volta a receber eventos, sem duplicação.

## Explicação curta para apresentação

> O RegistroAusencia é o Subject. Ele mantém uma lista de observadores inscritos e, ao registrar uma falta, chama atualizar() em cada um. Os observadores cuidam de suas próprias notificações, sem que o registro precise conhecer os detalhes de e-mail ou coordenação.
