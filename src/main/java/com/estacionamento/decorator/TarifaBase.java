package com.estacionamento.decorator;

import com.estacionamento.model.Permanencia;

import java.util.Locale;

/**
 * Componente concreto do padrão Decorator que representa a tarifa base
 * calculada a partir das horas de permanência no estacionamento.
 */
public class TarifaBase implements ServicoPermanencia {

    public static final double VALOR_HORA_PADRAO = 10.0;
    private static final Locale LOCALE_PT_BR = Locale.forLanguageTag("pt-BR");

    private final Permanencia permanencia;
    private final long horasFixas;
    private final double valorHora;

    /**
     * Cria a tarifa base vinculada a uma permanência com o valor/hora padrão.
     */
    public TarifaBase(Permanencia permanencia) {
        this(permanencia, VALOR_HORA_PADRAO);
    }

    /**
     * Cria a tarifa base vinculada a uma permanência com valor/hora personalizado.
     */
    public TarifaBase(Permanencia permanencia, double valorHora) {
        if (permanencia == null) {
            throw new IllegalArgumentException("A permanencia nao pode ser nula.");
        }
        if (valorHora <= 0) {
            throw new IllegalArgumentException("O valor da hora deve ser maior que zero.");
        }
        this.permanencia = permanencia;
        this.horasFixas = 0;
        this.valorHora = valorHora;
    }

    /**
     * Cria a tarifa base para uma quantidade fixa de horas com o valor/hora padrão.
     */
    public TarifaBase(long horas) {
        this(horas, VALOR_HORA_PADRAO);
    }

    /**
     * Cria a tarifa base para uma quantidade fixa de horas com valor/hora personalizado.
     */
    public TarifaBase(long horas, double valorHora) {
        if (horas <= 0) {
            throw new IllegalArgumentException("A quantidade de horas deve ser maior que zero.");
        }
        if (valorHora <= 0) {
            throw new IllegalArgumentException("O valor da hora deve ser maior que zero.");
        }
        this.permanencia = null;
        this.horasFixas = horas;
        this.valorHora = valorHora;
    }

    private long obterHoras() {
        return (permanencia != null) ? permanencia.calcularHoras() : horasFixas;
    }

    @Override
    public double calcularValor() {
        return obterHoras() * valorHora;
    }

    @Override
    public String getDescricao() {
        long horas = obterHoras();
        String unidade = (horas == 1) ? "hora" : "horas";
        return String.format(LOCALE_PT_BR, "Tarifa base (%d %s x R$ %.2f)", horas, unidade, valorHora);
    }

    public Permanencia getPermanencia() {
        return permanencia;
    }

    public double getValorHora() {
        return valorHora;
    }
}
