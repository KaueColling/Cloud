package br.senai.sc.cloud.service;

import br.senai.sc.cloud.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileService {

    @NonNull
    private FileRepository fileRepository;



}
