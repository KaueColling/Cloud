package br.senai.sc.cloud.controller;

import br.senai.sc.cloud.controller.dto.file.FileRequestPostDto;
import br.senai.sc.cloud.service.FileService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileAwsController {

    @NonNull
    FileService fileService;

    @PostMapping
    public void adicionarFile(@RequestBody FileRequestPostDto fileDto){
        fileService.adicionarFile(fileDto);
    }


}
