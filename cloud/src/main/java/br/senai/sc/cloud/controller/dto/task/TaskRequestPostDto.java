package br.senai.sc.cloud.controller.dto.task;

import lombok.Setter;

public record TaskRequestPostDto(
        String nomeCriador,
        String nomeTask,
        String descricao
) {
}
