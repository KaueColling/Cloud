package br.senai.sc.cloud.controller.dto.task;

import br.senai.sc.cloud.model.File;
import lombok.Setter;

import java.util.List;


public record TaskRequestGetDto(String nome, List<File> files) {

}
