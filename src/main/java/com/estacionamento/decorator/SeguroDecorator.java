package com.estacionamento.decorator;

import java.util.Locale;

/**
 * Decorador concreto que adiciona o serviço de seguro à permanência.
 */
public class SeguroDecorator extends ServicoDecorator {

    public static final double VALOR_PADRAO = 15.0;
    private static final Locale LOCALE_PT_BR = Locale.forLanguageTag("pt-BR");

    private final double valorSeguro;

    /**
     * Adiciona o serviço de seguro com o valor padrão.
     */
    public SeguroDecorator(ServicoPermanencia servicoDecorado) {
        this(servicoDecorado, VALOR_PADRAO);
    }

    /**
     * Adiciona o serviço de seguro com valor personalizado.
     */
    public SeguroDecorator(ServicoPermanencia servicoDecorado, double valorSeguro) {
        super(servicoDecorado);
        if (valorSeguro < 0) {
            throw new IllegalArgumentException("O valor do seguro nao pode ser negativo.");
        }
        this.valorSeguro = valorSeguro;
    }

    @Override
    public double calcularValor() {
        return super.calcularValor() + valorSeguro;
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + String.format(LOCALE_PT_BR, " + Seguro (R$ %.2f)", valorSeguro);
    }

    public double getValorSeguro() {
        return valorSeguro;
    }
}
