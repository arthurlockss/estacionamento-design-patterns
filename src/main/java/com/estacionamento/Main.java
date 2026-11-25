package com.estacionamento;

import com.estacionamento.decorator.LavagemDecorator;
import com.estacionamento.decorator.SeguroDecorator;
import com.estacionamento.decorator.ServicoPermanencia;
import com.estacionamento.decorator.TarifaBase;
import com.estacionamento.model.Permanencia;
import com.estacionamento.model.Vaga;
import com.estacionamento.model.Veiculo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Classe executável que demonstra o fluxo completo do estacionamento:
 * entrada de veículos (State), tentativa de operações inválidas,
 * contratação de serviços adicionais (Decorator) e saída com comprovante.
 *
 * Os horários de entrada e saída são fixos para que a saída do programa
 * seja sempre a mesma e fácil de conferir.
 */
public class Main {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        titulo("1. Inicializando o estacionamento");
        List<Vaga> vagas = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            vagas.add(new Vaga(i));
        }
        listarVagas(vagas);

        LocalDateTime hoje = LocalDateTime.of(2026, 11, 25, 0, 0);

        titulo("2. Entrada de veiculos");
        Veiculo gol = new Veiculo("ABC-1234", "VW Gol");
        Veiculo onix = new Veiculo("XYZ-9876", "Chevrolet Onix");

        Permanencia permanenciaGol = registrarEntrada(vagas.get(0), gol, hoje.withHour(8));
        Permanencia permanenciaOnix = registrarEntrada(vagas.get(1), onix, hoje.withHour(9).withMinute(15));
        listarVagas(vagas);

        titulo("3. Operacoes invalidas (o State impede)");
        Veiculo hb20 = new Veiculo("DEF-5678", "Hyundai HB20");
        tentar("Ocupar a vaga #1, que ja esta ocupada", () -> vagas.get(0).ocupar(hb20));
        tentar("Liberar a vaga #3, que ja esta livre", () -> vagas.get(2).liberar());

        titulo("4. Saida do VW Gol (somente tarifa base)");
        ServicoPermanencia servicoGol = new TarifaBase(permanenciaGol);
        registrarSaida(permanenciaGol, servicoGol, hoje.withHour(10).withMinute(30));

        titulo("5. Saida do Chevrolet Onix (tarifa + lavagem + seguro)");
        ServicoPermanencia servicoOnix = new TarifaBase(permanenciaOnix);
        servicoOnix = new LavagemDecorator(servicoOnix);
        servicoOnix = new SeguroDecorator(servicoOnix);
        registrarSaida(permanenciaOnix, servicoOnix, hoje.withHour(14));

        titulo("6. Vaga #1 liberada pode receber um novo veiculo");
        registrarEntrada(vagas.get(0), hb20, hoje.withHour(15));
        listarVagas(vagas);
    }

    private static Permanencia registrarEntrada(Vaga vaga, Veiculo veiculo, LocalDateTime horaEntrada) {
        vaga.ocupar(veiculo);
        System.out.println("Entrada: " + veiculo + " na vaga #" + vaga.getNumero()
                + " as " + horaEntrada.format(FORMATO_HORA));
        return new Permanencia(veiculo, vaga, horaEntrada);
    }

    private static void registrarSaida(Permanencia permanencia, ServicoPermanencia servico, LocalDateTime horaSaida) {
        permanencia.finalizarPermanencia(horaSaida);
        permanencia.getVaga().liberar();
        imprimirComprovante(permanencia, servico);
        System.out.println("Vaga #" + permanencia.getVaga().getNumero()
                + " agora esta: " + permanencia.getVaga().getEstado().getNome());
    }

    private static void imprimirComprovante(Permanencia permanencia, ServicoPermanencia servico) {
        System.out.println("+---------------- COMPROVANTE ----------------");
        System.out.println("| Veiculo : " + permanencia.getVeiculo());
        System.out.println("| Vaga    : #" + permanencia.getVaga().getNumero());
        System.out.println("| Entrada : " + permanencia.getHoraEntrada().format(FORMATO_HORA));
        System.out.println("| Saida   : " + permanencia.getHoraSaida().format(FORMATO_HORA));
        System.out.println("| Itens   : " + servico.getDescricao());
        System.out.println(String.format(PT_BR, "| TOTAL   : R$ %.2f", servico.calcularValor()));
        System.out.println("+---------------------------------------------");
    }

    private static void tentar(String descricao, Runnable operacao) {
        System.out.println(descricao + "...");
        try {
            operacao.run();
            System.out.println("  -> Operacao realizada.");
        } catch (IllegalStateException e) {
            System.out.println("  -> Bloqueado: " + e.getMessage());
        }
    }

    private static void listarVagas(List<Vaga> vagas) {
        for (Vaga vaga : vagas) {
            System.out.println("  " + vaga);
        }
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }
}
