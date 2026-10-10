package brokeerp_backend.service;

import brokeerp_backend.entity.Cuota;
import brokeerp_backend.entity.PolizaVigencia;
import brokeerp_backend.exception.BadRequestException;
import brokeerp_backend.exception.ResourceNotFoundException;
import brokeerp_backend.repository.CuotaRepository;
import brokeerp_backend.repository.PolizaVigenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class CuotaService {

    @Autowired
    private CuotaRepository cuotaRepository;
    @Autowired
    private PolizaVigenciaRepository vigenciaRepository;

    public List<Cuota> listarTodas() {
        return cuotaRepository.findAll();
    }

    public Cuota buscarPorId(Integer id) {
        return cuotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuota no encontrada con id: " + id));
    }

    public List<Cuota> listarPorVigencia(Integer idVigencia) {
        return cuotaRepository.findByVigenciaIdVigencia(idVigencia);
    }

    @Transactional
    public Cuota guardar(Cuota cuota) {
        if (cuota.getVigencia() == null || cuota.getVigencia().getIdVigencia() == null) {
            throw new BadRequestException("Debe asociar una vigencia");
        }
        if (cuota.getImporte() == null || cuota.getImporte().signum() <= 0) {
            throw new BadRequestException("El importe debe ser mayor a cero");
        }
        if (cuota.getFechaVencimiento() == null) {
            throw new BadRequestException("La fecha de vencimiento es obligatoria");
        }
        if (cuota.getNumeroCuota() == null || cuota.getNumeroCuota() <= 0) {
            throw new BadRequestException("El número de cuota es obligatorio");
        }
        return cuotaRepository.save(cuota);
    }

    @Transactional
    public Cuota marcarComoPagada(Integer idCuota, LocalDate fechaPago) {
        Cuota cuota = buscarPorId(idCuota);
        cuota.setEstado(Cuota.EstadoCuota.PAGADA);
        cuota.setFechaPago(fechaPago != null ? fechaPago : LocalDate.now());
        return cuotaRepository.save(cuota);
    }

    @Transactional
    public void actualizarEstadosVencidos() {
        List<Cuota> vencidas = cuotaRepository.findVencidas(LocalDate.now());
        for (Cuota c : vencidas) {
            c.setEstado(Cuota.EstadoCuota.VENCIDA);
            cuotaRepository.save(c);
        }
    }

    public List<Cuota> porVencerEn7Dias() {
        LocalDate hoy = LocalDate.now();
        return cuotaRepository.findPorVencerEntre(hoy, hoy.plusDays(7));
    }

    public List<Cuota> vencidas() {
        return cuotaRepository.findVencidas(LocalDate.now());
    }
}