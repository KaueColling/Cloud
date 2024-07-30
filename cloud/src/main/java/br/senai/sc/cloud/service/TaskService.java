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

/**
 * Implementação do serviço de tarefas.
 */
@Service
@RequiredArgsConstructor
@Data
public class TaskService {

    /**
     * Repositório de tarefas.
     */
    @NonNull
    private TaskRepository taskRepository;

    /**
     * Busca uma tarefa pelo seu ID.
     *
     * @param tarefaId ID da tarefa a ser buscada.
     * @return Tarefa encontrada, representada como um objeto TaskRequestGetDto.
     */
    public TaskRequestGetDto buscarTask(Long tarefaId) {
        Task task = taskRepository.findById(tarefaId).get();
        return new TaskRequestGetDto(task.getNome(), task.getFiles());
    }

    /**
     * Busca todas as tarefas existentes no sistema.
     *
     * @return Lista de tarefas encontradas, representadas como objetos TaskRequestGetDto.
     */
    public List<TaskRequestGetDto> buscarTarefas() {
        List<TaskRequestGetDto> tarefas = new ArrayList<>();
        taskRepository.findAll().forEach(tarefa -> {
            tarefas.add(tarefa.toGetDTO());
        });
        return tarefas;
    }

    /**
     * Adiciona uma nova tarefa ao sistema.
     *
     * @param taskPostDto Objeto TaskRequestPostDto contendo as informações da tarefa a ser adicionada.
     */
    public void adicionarTask(TaskRequestPostDto taskPostDto) {
        Task task = new Task();
        task.setNome(taskPostDto.nome());
        taskRepository.save(task);
    }

    /**
     * Deleta uma tarefa existente no sistema.
     *
     * @param tarefaId ID da tarefa a ser deletada.
     */
    public void deletarTask(Long tarefaId) {
        if (taskRepository.existsById(tarefaId)) {
            taskRepository.deleteById(tarefaId);
        }
    }
}