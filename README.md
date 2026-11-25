# Sistema de Estacionamento - State e Decorator

Trabalho da Parte 1 do projeto final de Padrões de Projeto. O tema sorteado foi **Estacionamento** (entidades Vaga, Veículo e Permanência), com os padrões **State** e **Decorator**.

## Descrição

Aplicação de console em Java que simula um estacionamento pequeno. Tudo fica em memória, sem banco de dados.

- Cada vaga está livre ou ocupada. O que ela aceita fazer depende do estado atual (State).
- Na saída, o valor é calculado a partir das horas de permanência, e serviços extras como lavagem e seguro podem ser somados à tarifa (Decorator).

## Tecnologias e pré-requisitos

- Java 17 ou superior
- Maven 3.8 ou superior (opcional, dá para compilar só com o `javac`)

Não há dependências externas.

## Como executar

Com Maven, na raiz do projeto:

```bash
mvn clean compile exec:java
```

Sem Maven:

```bash
javac -d bin $(find src/main/java -name "*.java")
java -cp bin com.estacionamento.Main
```

## Exemplo de uso

A classe `Main` roda um roteiro fixo, com horários definidos no código para que o resultado seja sempre o mesmo:

1. Cria três vagas livres.
2. Um Gol entra na vaga 1 às 08:00 e um Onix entra na vaga 2 às 09:15.
3. Tenta colocar outro carro na vaga 1 e liberar a vaga 3, que está vazia. As duas operações são recusadas pelo estado da vaga.
4. O Gol sai às 10:30 e paga só a tarifa base.
5. O Onix sai às 14:00 com lavagem e seguro.
6. A vaga 1, já liberada, recebe um novo carro.

Trecho da saída:

```
=== 3. Operacoes invalidas (o State impede) ===
Ocupar a vaga #1, que ja esta ocupada...
  -> Bloqueado: Vaga #1 ja esta ocupada por VW Gol (Placa: ABC-1234).
Liberar a vaga #3, que ja esta livre...
  -> Bloqueado: Vaga #3 ja esta livre e nao pode ser liberada novamente.

=== 5. Saida do Chevrolet Onix (tarifa + lavagem + seguro) ===
+---------------- COMPROVANTE ----------------
| Veiculo : Chevrolet Onix (Placa: XYZ-9876)
| Vaga    : #2
| Entrada : 25/11/2026 09:15
| Saida   : 25/11/2026 14:00
| Itens   : Tarifa base (5 horas x R$ 10,00) + Lavagem (R$ 35,00) + Seguro (R$ 15,00)
| TOTAL   : R$ 100,00
+---------------------------------------------
```

A cobrança é por hora cheia, arredondada para cima: 4h45 vira 5 horas. Valores padrão: R$ 10,00 por hora, lavagem a R$ 35,00 e seguro a R$ 15,00.

## Padrões utilizados

### State

Sem o padrão, a classe `Vaga` precisaria de `if` em cada operação para saber se pode ser ocupada ou liberada. Com o State, cada estado é uma classe e a vaga só repassa a chamada para o estado atual.

- `EstadoVaga`: interface com `ocupar`, `liberar` e `getNome`.
- `VagaLivreState`: aceita um veículo e muda a vaga para ocupada. Liberar lança exceção.
- `VagaOcupadaState`: recusa outro veículo e, ao liberar, volta a vaga para livre.
- `Vaga`: o contexto. Guarda o estado atual e delega `ocupar()` e `liberar()` a ele.

### Decorator

Os serviços extras podem ser combinados de várias formas (só lavagem, só seguro, os dois, nenhum). Criar uma subclasse para cada combinação não escala. Com o Decorator, cada serviço embrulha o anterior e soma seu valor e sua descrição.

- `ServicoPermanencia`: interface com `calcularValor()` e `getDescricao()`.
- `TarifaBase`: componente concreto, calcula horas x valor da hora a partir da `Permanencia`.
- `ServicoDecorator`: classe abstrata que guarda o serviço embrulhado e repassa as chamadas.
- `LavagemDecorator` e `SeguroDecorator`: somam o próprio valor ao do serviço embrulhado.

Exemplo de composição:

```java
ServicoPermanencia servico = new SeguroDecorator(new LavagemDecorator(new TarifaBase(permanencia)));
```

## Uso de IA

- Ferramentas: ChatGPT, Gemini e Claude.
- Onde foram usadas: discussão inicial de como aplicar os dois padrões ao tema, configuração do `pom.xml`, apoio na redação e organização deste README, ajuda na escrita da classe `Main` e revisão do código em busca de erros e inconsistências, com os ajustes conferidos pelo grupo.

## Contribuição dos integrantes

| Integrante | GitHub | Contribuição |
|---|---|---|
| Arthur Locks | [@arthurlockss](https://github.com/arthurlockss) | Criação do repositório, `pom.xml`, `.gitignore`, entidades `Veiculo`, `Vaga` e `Permanencia` e primeira versão do README. |
| Luiz Coral | [@Luizcoral](https://github.com/Luizcoral) | Padrão State: `EstadoVaga`, `VagaLivreState`, `VagaOcupadaState` e ligação com a classe `Vaga`. |
| Bernardo | [@bernardosvent](https://github.com/bernardosvent) | Padrão Decorator: `ServicoPermanencia`, `TarifaBase`, `ServicoDecorator`, `LavagemDecorator` e `SeguroDecorator`. |
| Mateus | [@mateusinacion](https://github.com/mateusinacion) | Classe `Main` com o fluxo completo, testes manuais no console e revisão final do README. |
