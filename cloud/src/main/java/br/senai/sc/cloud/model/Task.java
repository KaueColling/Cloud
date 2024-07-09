package br.senai.sc.cloud.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "tarefa")
    List<File> arquivos;

}
