package br.senai.sc.cloud.controller;

import br.senai.sc.cloud.model.Usuario;
import br.senai.sc.cloud.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Define que a classe é gerenciada pela Spring, do esteriótipo RestController.

// endPoint são interfaces de Comunição
// Como especificar um endPoint precisa ter uma rota (/Usuario) e método (GET).
// PathVariable define que é uma variavel.

@RequestMapping("/usuario")
// Define a url "base" para os endPoint da controller.

public class UsuarioController {

    public UsuarioController (UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private final UsuarioService usuarioService;

    @PostMapping
    // Recebe informação para salver em algum local.
    public String cadastroUsuario(@RequestBody Usuario usuario) {
        usuarioService.cadastroUsuario(usuario);
        return "Olá " + usuario.getNome() + ".\n" + usuario;
    }

    // Não pode ter body.
    // Ele retornara o que tiver dentro da lógica do método, como por exemplo uma lista (List).
    // Ele retorna os objetos para quem esta consumindo, pode retornar todos os elementos sem filtro.
    @GetMapping("/{id}")
    public Usuario buscarUsuario(@PathVariable Integer id) throws Exception {
        return usuarioService.buscarUsuario(id);
    }

    @GetMapping
    public List<Usuario> buscarTodosUsuarios() {
        return usuarioService.buscarTodosUsuarios();
    }




}
