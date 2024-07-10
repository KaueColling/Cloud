package br.senai.sc.cloud.controller;

import br.senai.sc.cloud.controller.dto.file.FileRequestPostDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileAwsController {

    @PostMapping
    public void adicionarArquivo(@RequestBody FileRequestPostDto fileDto){
    }

}
