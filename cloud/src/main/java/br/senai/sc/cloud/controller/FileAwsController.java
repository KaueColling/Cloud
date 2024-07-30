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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador de arquivos que utiliza a AWS para armazenamento.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileAwsController {

    /**
     * Serviço de arquivos que será utilizado para realizar operações de arquivo.
     */
    @NonNull
    FileService fileService;

    /**
     * Cria um novo arquivo associado a uma tarefa.
     *
     * @param idTask ID da tarefa associada ao arquivo.
     * @param file   Arquivo a ser criado.
     * @return Resposta HTTP com o status de criação do arquivo (200 se sucesso, outro código caso contrário).
     */
    @PostMapping("/{idTask}")
    public ResponseEntity<Boolean> criarFile(@PathVariable Long idTask, @RequestBody MultipartFile file) {
        return new ResponseEntity<>(fileService.adicionarFile(idTask, file), HttpStatusCode.valueOf(200));
    }

    /**
     * Controlador responsável por lidar com requisições GET para buscar arquivos.
     *
     * @author [Seu nome]
     * @since [Versão do sistema]
     */
    @RestController
    @RequestMapping("/files")
    public class FileController {
        /**
         * Serviço responsável por buscar arquivos.
         */
        @Autowired
        private FileService fileService;

        /**
         * Busca um arquivo com base no ID do arquivo.
         *
         * @param idFile ID do arquivo a ser buscado.
         * @return Resposta HTTP com a URL do arquivo ou mensagem de erro.
         */
        @GetMapping("/{idFile}")
        public ResponseEntity<String> buscarFile(@PathVariable Long idFile) {
            try {
                /**
                 * Chama o serviço para buscar o arquivo e retorna a URL do arquivo.
                 */
                return new ResponseEntity<>(fileService.buscarFilePorID(idFile), HttpStatusCode.valueOf(200));
            } catch (Exception e) {
                /**
                 * Retorna uma resposta de erro com a mensagem de exceção.
                 */
                return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
            }
        }
    }
}