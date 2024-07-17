package br.senai.sc.cloud.service.interfaces;

import br.senai.sc.cloud.controller.dto.file.FileRequestPostDto;
import br.senai.sc.cloud.model.File;

public interface FileServiceInt {

    public Boolean adicionarFile(FileRequestPostDto fileRequestPostDto);


}
