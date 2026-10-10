package brokeerp_backend.service;

import brokeerp_backend.entity.Poliza;
import brokeerp_backend.entity.PolizaVigencia;
import brokeerp_backend.exception.BadRequestException;
import brokeerp_backend.exception.ResourceNotFoundException;
import brokeerp_backend.repository.PolizaRepository;
import brokeerp_backend.repository.PolizaVigenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class PolizaVigenciaService {

    @Autowired
    private PolizaVigenciaRepository vigenciaRepository;
    @Autowired
    private PolizaRepository polizaRepository;

    public List<PolizaVigencia> listarTodas() {
        return vigenciaRepository.findAll();
    }

    public PolizaVigencia buscarPorId(Integer id) {
        return vigenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vigencia no encontrada con id: " + id));
    }

    public List<PolizaVigencia> listarPorPoliza(Integer idPoliza) {
        return vigenciaRepository.findByPolizaIdPoliza(idPoliza);
    }

    @Transactional
    public PolizaVigencia guardar(PolizaVigencia vigencia) {
        if (vigencia.getFechaInicio() == null || vigencia.getFechaFin() == null) {
            throw new BadRequestException("Las fechas son obligatorias");
        }
        if (!vigencia.getFechaFin().isAfter(vigencia.getFechaInicio())) {
            throw new BadRequestException("La fecha fin debe ser posterior a la fecha inicio");
        }
        return vigenciaRepository.save(vigencia);
    }

    /**
     * Renueva una póliza creando una nueva vigencia manteniendo el mismo número de
     * póliza.
     */
    @Transactional
    public PolizaVigencia renovar(Integer idVigenciaActual, LocalDate nuevaFechaFin, java.math.BigDecimal nuevaPrima) {
        PolizaVigencia actual = buscarPorId(idVigenciaActual);
        actual.setEstado(PolizaVigencia.EstadoVigencia.RENOVADA);
        vigenciaRepository.save(actual);

        PolizaVigencia nueva = new PolizaVigencia();
        nueva.setPoliza(actual.getPoliza());
        nueva.setFechaInicio(actual.getFechaFin());
        nueva.setFechaFin(nuevaFechaFin);
        nueva.setPrimaTotal(nuevaPrima);
        nueva.setEstado(PolizaVigencia.EstadoVigencia.VIGENTE);
        nueva.setFechaRenovacion(actual.getFechaFin());
        return vigenciaRepository.save(nueva);
    }

    public List<PolizaVigencia> porVencerEn30Dias() {
        LocalDate hoy = LocalDate.now();
        return vigenciaRepository.findPorVencerEntre(hoy, hoy.plusDays(30));
    }

    public List<PolizaVigencia> vencidas() {
        return vigenciaRepository.findVencidas(LocalDate.now());
    }
}