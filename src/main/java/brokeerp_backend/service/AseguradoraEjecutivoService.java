package brokeerp_backend.service;

import brokeerp_backend.entity.AseguradoraEjecutivo;
import brokeerp_backend.exception.ResourceNotFoundException;
import brokeerp_backend.repository.AseguradoraEjecutivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AseguradoraEjecutivoService {

    @Autowired
    private AseguradoraEjecutivoRepository ejecutivoRepository;

    public List<AseguradoraEjecutivo> listarTodos() {
        return ejecutivoRepository.findByActivoTrue();
    }

    public AseguradoraEjecutivo buscarPorId(Integer id) {
        return ejecutivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ejecutivo no encontrado con id: " + id));
    }

    public List<AseguradoraEjecutivo> listarPorAseguradora(Integer idAseguradora) {
        return ejecutivoRepository.findByAseguradoraIdAseguradora(idAseguradora);
    }

    public List<AseguradoraEjecutivo> buscarPorAseguradoraYArea(Integer idAseguradora, String area) {
        return ejecutivoRepository.findByAseguradoraIdAseguradoraAndAreaEspecialidad(idAseguradora, area);
    }

    public AseguradoraEjecutivo guardar(AseguradoraEjecutivo ejecutivo) {
        return ejecutivoRepository.save(ejecutivo);
    }

    public void eliminar(Integer id) {
        AseguradoraEjecutivo e = buscarPorId(id);
        e.setActivo(false);
        ejecutivoRepository.save(e);
    }
}