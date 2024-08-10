/**
 * Serviço responsável por gerenciar tarefas no sistema.
 *
 * @author Kaue Correa
 */
package br.senai.sc.cloud.service;

import br.senai.sc.cloud.controller.dto.task.TaskRequestGetDto;
import br.senai.sc.cloud.controller.dto.task.TaskRequestPostDto;
import br.senai.sc.cloud.model.Task;
import br.senai.sc.cloud.repository.TaskRepository;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class TaskService {


    @NonNull
    private TaskRepository taskRepository;

    public TaskRequestGetDto buscarTask(Long tarefaId) {
        Task task = taskRepository.findById(tarefaId).get();
        return new TaskRequestGetDto(task.getNomeCriador(), task.getNomeTask(), task.getDescricao(), task.getFiles());
    }

    public List<TaskRequestGetDto> buscarTarefas() {
        List<TaskRequestGetDto> tarefas = new ArrayList<>();
        taskRepository.findAll().forEach(tarefa -> {
            tarefas.add(tarefa.toGetDTO());
        });
        return tarefas;
    }

    public void adicionarTask(TaskRequestPostDto taskPostDto) {
        Task task = new Task();
        task.setNomeCriador(taskPostDto.nomeCriador());
        task.setNomeTask(taskPostDto.nomeTask());
        task.setDescricao(taskPostDto.descricao());
        System.out.println(task);
        taskRepository.save(task);
    }


    public void deletarTask(Long tarefaId) {
        if (taskRepository.existsById(tarefaId)) {
            taskRepository.deleteById(tarefaId);
        }
    }
}