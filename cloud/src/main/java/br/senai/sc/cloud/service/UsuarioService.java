package br.senai.sc.cloud.service;

import br.senai.sc.cloud.model.Usuario;
import br.senai.sc.cloud.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public Usuario cadastroUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuario(Integer id) throws Exception {
        Optional<Usuario> optional = usuarioRepository.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new Exception("Usuario nao encontrado");
        }
    }

    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }



}
