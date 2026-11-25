package com.estacionamento.decorator;

import java.util.Locale;

/**
 * Decorador concreto que adiciona o serviço de lavagem à permanência.
 */
public class LavagemDecorator extends ServicoDecorator {

    public static final double VALOR_PADRAO = 35.0;
    private static final Locale LOCALE_PT_BR = Locale.forLanguageTag("pt-BR");

    private final double valorLavagem;

    /**
     * Adiciona o serviço de lavagem com o valor padrão.
     */
    public LavagemDecorator(ServicoPermanencia servicoDecorado) {
        this(servicoDecorado, VALOR_PADRAO);
    }

    /**
     * Adiciona o serviço de lavagem com valor personalizado.
     */
    public LavagemDecorator(ServicoPermanencia servicoDecorado, double valorLavagem) {
        super(servicoDecorado);
        if (valorLavagem < 0) {
            throw new IllegalArgumentException("O valor da lavagem nao pode ser negativo.");
        }
        this.valorLavagem = valorLavagem;
    }

    @Override
    public double calcularValor() {
        return super.calcularValor() + valorLavagem;
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + String.format(LOCALE_PT_BR, " + Lavagem (R$ %.2f)", valorLavagem);
    }

    public double getValorLavagem() {
        return valorLavagem;
    }
}
