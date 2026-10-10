package brokeerp_backend.service;

import brokeerp_backend.entity.Rol;
import brokeerp_backend.exception.ResourceNotFoundException;
import brokeerp_backend.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    public Rol buscarPorId(Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con id: " + id));
    }

    public Rol buscarPorNombre(String nombre) {
        return rolRepository.findByNombreRol(nombre)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + nombre));
    }

    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    public void eliminar(Integer id) {
        rolRepository.deleteById(id);
    }
}