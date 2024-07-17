package br.senai.sc.cloud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String file;

    private Date date;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Task task;

    private String reference;

}
