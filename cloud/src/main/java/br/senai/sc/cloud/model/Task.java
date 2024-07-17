package br.senai.sc.cloud.model;

import br.senai.sc.cloud.controller.dto.file.FileRequestGetDto;
import br.senai.sc.cloud.controller.dto.task.TaskRequestGetDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL)
    List<File> files;

    @Column(nullable = false)
    @NonNull
    private String nome;

    public TaskRequestGetDto toGetDTO() {
        List<FileRequestGetDto> arquivos = new ArrayList<>();
        return new TaskRequestGetDto(this.getNome(), files);
    }

}
