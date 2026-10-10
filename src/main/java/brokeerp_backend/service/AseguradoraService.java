package brokeerp_backend.service;

import brokeerp_backend.entity.Aseguradora;
import brokeerp_backend.exception.BadRequestException;
import brokeerp_backend.exception.ResourceNotFoundException;
import brokeerp_backend.repository.AseguradoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AseguradoraService {

    @Autowired
    private AseguradoraRepository aseguradoraRepository;

    public List<Aseguradora> listarTodas() {
        return aseguradoraRepository.findByActivoTrue();
    }

    public Aseguradora buscarPorId(Integer id) {
        return aseguradoraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aseguradora no encontrada con id: " + id));
    }

    public Aseguradora guardar(Aseguradora aseguradora) {
        if (aseguradora.getIdAseguradora() == null
                && aseguradoraRepository.existsByNombre(aseguradora.getNombre())) {
            throw new BadRequestException("Ya existe una aseguradora con ese nombre");
        }
        return aseguradoraRepository.save(aseguradora);
    }

    public void eliminar(Integer id) {
        Aseguradora a = buscarPorId(id);
        a.setActivo(false);
        aseguradoraRepository.save(a);
    }
}