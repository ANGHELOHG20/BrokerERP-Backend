package brokeerp_backend.service;

import brokeerp_backend.entity.Cuota;
import brokeerp_backend.entity.Notificacion;
import brokeerp_backend.entity.PolizaVigencia;
import brokeerp_backend.repository.CuotaRepository;
import brokeerp_backend.repository.NotificacionRepository;
import brokeerp_backend.repository.PolizaVigenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;
    @Autowired
    private PolizaVigenciaRepository vigenciaRepository;
    @Autowired
    private CuotaRepository cuotaRepository;

    public List<Notificacion> listarNoLeidas() {
        return notificacionRepository.findByLeidaFalse();
    }

    public List<Notificacion> listarTodas() {
        return notificacionRepository.findAll();
    }

    public Notificacion guardar(Notificacion n) {
        return notificacionRepository.save(n);
    }

    public void marcarComoLeida(Integer id) {
        Notificacion n = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        n.setLeida(true);
        notificacionRepository.save(n);
    }

    /**
     * Genera notificaciones automáticas para renovaciones y cuotas próximas.
     */
    @Transactional
    public void generarNotificaciones() {
        LocalDate hoy = LocalDate.now();
        LocalDate en30Dias = hoy.plusDays(30);

        // Renovaciones próximas
        List<PolizaVigencia> porVencer = vigenciaRepository.findPorVencerEntre(hoy, en30Dias);
        for (PolizaVigencia v : porVencer) {
            Notificacion n = new Notificacion();
            n.setTipo(Notificacion.TipoNotificacion.RENOVACION);
            n.setIdReferencia(v.getIdVigencia());
            n.setMensaje("Póliza " + v.getPoliza().getNumeroPoliza() + " vence el " + v.getFechaFin());
            notificacionRepository.save(n);
        }

        // Cuotas vencidas
        List<Cuota> cuotasVencidas = cuotaRepository.findVencidas(hoy);
        for (Cuota c : cuotasVencidas) {
            Notificacion n = new Notificacion();
            n.setTipo(Notificacion.TipoNotificacion.CUOTA_VENCIDA);
            n.setIdReferencia(c.getIdCuota());
            n.setMensaje("Cuota " + c.getNumeroCuota() + " vencida desde " + c.getFechaVencimiento());
            notificacionRepository.save(n);
        }
    }
}