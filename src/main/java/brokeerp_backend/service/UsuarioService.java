package brokeerp_backend.service;

import brokeerp_backend.entity.Usuario;
import brokeerp_backend.exception.BadRequestException;
import brokeerp_backend.exception.ResourceNotFoundException;
import brokeerp_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + email));
    }

    public Usuario guardar(Usuario usuario) {
        if (usuario.getIdUsuario() == null && usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new BadRequestException("Ya existe un usuario con ese email");
        }
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }
}