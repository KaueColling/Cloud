/**
 * Controlador responsável por gerenciar arquivos no sistema.
 *
 * @author Kaue Correa
 */
package br.senai.sc.cloud.controller;

import br.senai.sc.cloud.controller.dto.file.FileRequestGetDto;
import br.senai.sc.cloud.controller.dto.file.FileRequestPostDto;
import br.senai.sc.cloud.service.FileService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/file")
public class FileAwsController {

    @NonNull
    FileService fileService;

    @PostMapping("/{idTask}")
    public ResponseEntity<Boolean> criarFile(@PathVariable Long idTask, @RequestBody MultipartFile file) {
        return new ResponseEntity<>(fileService.adicionarFile(idTask, file), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{idFile}")
    public ResponseEntity<String> buscarFile(@PathVariable Long idFile) {
        try {
            return new ResponseEntity<>(fileService.buscarFilePorID(idFile), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }
}