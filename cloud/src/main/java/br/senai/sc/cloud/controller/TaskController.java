package br.senai.sc.cloud.controller;

import br.senai.sc.cloud.controller.dto.task.TaskRequestGetDto;
import br.senai.sc.cloud.controller.dto.task.TaskRequestPostDto;
import br.senai.sc.cloud.service.TaskService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    @NonNull
    private TaskService taskService;

    @PostMapping
    public String criarTask(@RequestBody TaskRequestPostDto taskPostDTO) {
        taskService.adicionarTask(taskPostDTO);
        return "Task foi criada";
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskRequestGetDto> buscarTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.buscarTask(taskId));
    }

}
