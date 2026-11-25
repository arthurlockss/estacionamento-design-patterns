package com.estacionamento.model;

import com.estacionamento.state.EstadoVaga;
import com.estacionamento.state.VagaLivreState;

/**
 * Representa uma vaga do estacionamento.
 * Atua como o contexto do padrão State: mantém o estado atual
 * e delega a ele o comportamento de ocupar e liberar.
 */
public class Vaga {
    private int numero;
    private Veiculo veiculoAtual;
    private EstadoVaga estado;

    public Vaga(int numero) {
        this.numero = numero;
        this.veiculoAtual = null;
        this.estado = new VagaLivreState();
    }

    /** Ocupa a vaga com o veículo (comportamento definido pelo estado atual). */
    public void ocupar(Veiculo veiculo) {
        estado.ocupar(this, veiculo);
    }

    /** Libera a vaga (comportamento definido pelo estado atual). */
    public void liberar() {
        estado.liberar(this);
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Veiculo getVeiculoAtual() {
        return veiculoAtual;
    }

    /** Uso interno dos estados; para ocupar/liberar prefira ocupar() e liberar(). */
    public void setVeiculoAtual(Veiculo veiculoAtual) {
        this.veiculoAtual = veiculoAtual;
    }

    public EstadoVaga getEstado() {
        return estado;
    }

    /** Uso interno dos estados para realizar a transição. */
    public void setEstado(EstadoVaga estado) {
        this.estado = estado;
    }

    public boolean estaOcupada() {
        return this.veiculoAtual != null;
    }

    @Override
    public String toString() {
        return "Vaga #" + numero + " [" + estado.getNome()
                + (estaOcupada() ? " por " + veiculoAtual : "") + "]";
    }
}