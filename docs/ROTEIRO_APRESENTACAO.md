# Roteiro de apresentacao — Observer (dupla 9)

**Tema:** Notificar automaticamente objetos interessados em mudancas de estado.

**Duracao total: 15 minutos por dupla** — 3 + 2 + 6 + 4 minutos.

## 0:00–3:00 — Introducao e definicao
- Design Patterns sao solucoes de projeto reutilizaveis, nao codigos prontos.
- O Observer e um padrao GoF **comportamental**.
- Relacao um-para-muitos: Subject mantem observers inscritos e os notifica em mudancas relevantes.
- Inscrever e cancelar inscricao sao partes do mecanismo; consentimento e uma regra de negocio distinta.

## 3:00–5:00 — Problema pratico
- No SAA, uma falta registrada precisa avisar responsavel e coordenacao.
- Em `src/SemObserver.java`, `RegistroAusencia` chama diretamente `enviarEmail` e `avisarCoordenacao`.
- Para adicionar uma terceira notificacao, seria necessario alterar essa classe.
- O problema e o **acoplamento** do registro aos canais de comunicacao.

## 5:00–11:00 — Dois exemplos Java e demonstracao
1. Abra `SemObserver.java` e execute `main`: as notificacoes estao codificadas no registro.
2. Abra `Main.java` e execute `main`: interface Swing para testar o comportamento refatorado.
3. Mostre os papeis: `RegistroAusencia` = Subject; `Observador` = interface; `email` e `avisoCoordenacao` = observers concretos (lambdas).
4. Digite `Joao Silva`, com ambas inscricoes marcadas: sao exibidas duas notificacoes simuladas.
5. Desmarque responsavel e registre `Maria Souza`: somente coordenacao e avisada.
6. Desmarque ambos, registre `Ana Lima`: nenhuma notificacao. Reinscreva para demonstrar a reversibilidade.
7. Explique: o botao registra o evento, o Subject percorre a lista de inscritos e chama `atualizar()` em cada Observer.

## 11:00–15:00 — Vantagens, cuidados e recomendacoes
- **Vantagens:** baixo acoplamento com os canais; inscricao dinamica; extensao com novos observadores sem reescrever o disparo.
- **Desvantagens:** fluxo de eventos menos explicito; ordem de notificacoes pode importar; falhas de um observador podem impactar outros, caso nao sejam isoladas.
- **Use quando:** diversos componentes precisam reagir ao mesmo evento ou mudanca de estado.
- **Evite quando:** ha apenas uma chamada simples e fixa, sem expectativa de novos assinantes.
- **Exemplo real:** interfaces de usuario baseadas em eventos (listeners Swing) e sistemas de notificacao de pedidos.
- **Conclusao:** Observer coordena notificar quem se inscreveu; nao exige e-mail real nem consentimento como parte formal do padrao.

## Testes sugeridos
- Campo vazio: bloqueia registro na interface.
- Dois inscritos: duas notificacoes.
- Um inscrito: uma notificacao.
- Zero inscritos: nenhuma notificacao.
- Reinscricao: volta a notificar sem duplicatas.

## Referencias bibliograficas
1. GAMMA, Erich et al. *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley, 1994.
2. FREEMAN, Eric; ROBSON, Elisabeth. *Head First Design Patterns*, 2nd ed. O'Reilly Media, 2020.
3. ORACLE. *How to Write an Action Listener*. Java Tutorials. https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
4. REFACTORING.GURU. *Observer*. https://refactoring.guru/design-patterns/observer

> Material didatico. Nao use dados reais de alunos no exemplo.
