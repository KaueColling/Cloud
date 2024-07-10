package br.senai.sc.cloud.service;

import br.senai.sc.cloud.controller.dto.task.TaskRequestGetDto;
import br.senai.sc.cloud.controller.dto.task.TaskRequestPostDto;
import br.senai.sc.cloud.model.Task;
import br.senai.sc.cloud.repository.TaskRepository;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class TaskService {

    @NonNull
    private TaskRepository taskRepository;

    public TaskRequestGetDto buscarTask(Long tarefaId) {
        Task task = taskRepository.findById(tarefaId).get();
        return new TaskRequestGetDto(task.getNome());
    }

    public void adicionarTask(TaskRequestPostDto taskPostDto) {
        Task task = new Task();
        task.setNome(taskPostDto.nome());
        taskRepository.save(task);
    }

}
