package brokeerp_backend.service;

import brokeerp_backend.entity.TipoSeguro;
import brokeerp_backend.exception.ResourceNotFoundException;
import brokeerp_backend.repository.TipoSeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoSeguroService {

    @Autowired
    private TipoSeguroRepository tipoSeguroRepository;

    public List<TipoSeguro> listarTodos() {
        return tipoSeguroRepository.findByActivoTrue();
    }

    public TipoSeguro buscarPorId(Integer id) {
        return tipoSeguroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de seguro no encontrado con id: " + id));
    }

    public List<TipoSeguro> listarQueRequierenVehiculo() {
        return tipoSeguroRepository.findByRequiereVehiculoTrue();
    }

    public List<TipoSeguro> listarSctr() {
        return tipoSeguroRepository.findByEsSctrTrue();
    }

    public TipoSeguro guardar(TipoSeguro tipoSeguro) {
        return tipoSeguroRepository.save(tipoSeguro);
    }

    public void eliminar(Integer id) {
        TipoSeguro t = buscarPorId(id);
        t.setActivo(false);
        tipoSeguroRepository.save(t);
    }
}