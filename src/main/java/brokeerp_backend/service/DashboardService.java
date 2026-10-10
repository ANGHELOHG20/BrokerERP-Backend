package brokeerp_backend.service;

import brokeerp_backend.entity.Cuota;
import brokeerp_backend.entity.Poliza;
import brokeerp_backend.entity.PolizaVigencia;
import brokeerp_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private PolizaRepository polizaRepository;
    @Autowired
    private PolizaVigenciaRepository vigenciaRepository;
    @Autowired
    private CuotaRepository cuotaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private AseguradoraRepository aseguradoraRepository;

    public Map<String, Object> obtenerMetricas() {
        Map<String, Object> metricas = new HashMap<>();

        try {
            LocalDate hoy = LocalDate.now();

            List<Poliza> polizasActivas = polizaRepository.findByActivoTrue();
            metricas.put("totalPolizas", polizasActivas.size());

            List<PolizaVigencia> porVencer = vigenciaRepository.findPorVencerEntre(hoy, hoy.plusDays(30));
            metricas.put("polizasPorVencer30Dias", porVencer.size());

            List<PolizaVigencia> vencidas = vigenciaRepository.findVencidas(hoy);
            metricas.put("polizasVencidas", vencidas.size());

            List<Cuota> cuotasPendientes = cuotaRepository.findByEstado(Cuota.EstadoCuota.PENDIENTE);
            List<Cuota> cuotasVencidas = cuotaRepository.findVencidas(hoy);
            metricas.put("cuotasPendientes", cuotasPendientes.size());
            metricas.put("cuotasVencidas", cuotasVencidas.size());

            metricas.put("totalClientes", clienteRepository.findByActivoTrue().size());
            metricas.put("totalAseguradoras", aseguradoraRepository.findByActivoTrue().size());

        } catch (Exception e) {
            System.out.println(">>> ERROR EN DASHBOARD: " + e.getClass().getSimpleName());
            System.out.println(">>> MENSAJE: " + e.getMessage());
            e.printStackTrace();
            metricas.put("error", e.getMessage());
            metricas.put("tipoError", e.getClass().getSimpleName());
        }

        return metricas;
    }

    public Map<String, Object> polizasPorAseguradora() {
        Map<String, Object> resultado = new HashMap<>();
        List<Poliza> polizas = polizaRepository.findByActivoTrue();
        Map<String, Long> conteo = new HashMap<>();
        for (Poliza p : polizas) {
            String nombre = p.getAseguradora().getNombre();
            conteo.put(nombre, conteo.getOrDefault(nombre, 0L) + 1);
        }
        resultado.put("polizasPorAseguradora", conteo);
        return resultado;
    }
}