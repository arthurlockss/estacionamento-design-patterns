package com.estacionamento.state;

import com.estacionamento.model.Vaga;
import com.estacionamento.model.Veiculo;

public interface EstadoVaga {
    void ocupar(Vaga vaga, Veiculo veiculo);
    void liberar(Vaga vaga);
    String getNome();
}
