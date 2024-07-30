package br.senai.sc.cloud.service.interfaces;

import br.senai.sc.cloud.controller.dto.file.FileRequestPostDto;
import br.senai.sc.cloud.model.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceInt {

    public Boolean adicionarFile(Long id, MultipartFile file);


}
