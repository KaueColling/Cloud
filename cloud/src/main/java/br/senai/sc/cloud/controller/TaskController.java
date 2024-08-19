/**
 * Controlador responsável por gerenciar arquivos no sistema.
 *
 * @author Kaue Correa
 */
package br.senai.sc.cloud.controller;

import br.senai.sc.cloud.controller.dto.task.TaskRequestGetDto;
import br.senai.sc.cloud.controller.dto.task.TaskRequestPostDto;
import br.senai.sc.cloud.service.TaskService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/task")
public class TaskController {

    @NonNull
    private TaskService taskService;

    @PostMapping
    public String criarTask(@RequestBody TaskRequestPostDto taskPostDTO) {
        taskService.adicionarTask(taskPostDTO);
        return "Task de nome '" + taskPostDTO.nomeTask() + "' foi criada.";
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskRequestGetDto> buscarTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.buscarTask(taskId));
    }

    @GetMapping
    public ResponseEntity<List<TaskRequestGetDto>> buscarTarefas() {
        return ResponseEntity.ok(taskService.buscarTarefas());
    }

    @DeleteMapping("/{taskId}")
    public String deletarTask(@PathVariable Long taskId) {
        taskService.deletarTask(taskId);
        return "Task de id " + taskId + " deletado com sucesso";
    }

}
