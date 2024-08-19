package br.senai.sc.cloud.controller.dto.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record FileRequestPostDto(
        Long tarefaID,
        MultipartFile files
) {
}