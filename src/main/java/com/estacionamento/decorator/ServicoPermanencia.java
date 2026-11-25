package com.estacionamento.decorator;

/**
 * Contrato do padrão Decorator para os serviços de cobrança de permanência.
 * Define as operações comuns ao componente base e aos decoradores adicionais.
 */
public interface ServicoPermanencia {

    /**
     * Calcula o valor total do serviço de permanência em reais.
     *
     * @return valor total calculado
     */
    double calcularValor();

    /**
     * Retorna a descrição detalhada da composição do serviço.
     *
     * @return descrição textual dos itens cobrados
     */
    String getDescricao();
}
