package com.estacionamento.decorator;

/**
 * Decorador abstrato do padrão Decorator.
 * Mantém uma referência para o {@link ServicoPermanencia} encapsulado e delega
 * as operações base para que subclasses agreguem custos e descrições adicionais.
 */
public abstract class ServicoDecorator implements ServicoPermanencia {

    protected final ServicoPermanencia servicoDecorado;

    /**
     * Cria um decorador envolvendo o serviço informado.
     *
     * @param servicoDecorado serviço de permanência a ser decorado
     * @throws IllegalArgumentException se o serviço decorado for nulo
     */
    public ServicoDecorator(ServicoPermanencia servicoDecorado) {
        if (servicoDecorado == null) {
            throw new IllegalArgumentException("O servico decorado nao pode ser nulo.");
        }
        this.servicoDecorado = servicoDecorado;
    }

    @Override
    public double calcularValor() {
        return servicoDecorado.calcularValor();
    }

    @Override
    public String getDescricao() {
        return servicoDecorado.getDescricao();
    }
}
