package brokeerp_backend.service;

import brokeerp_backend.entity.Poliza;
import brokeerp_backend.entity.PolizaVehiculo;
import brokeerp_backend.entity.Vehiculo;
import brokeerp_backend.exception.BadRequestException;
import brokeerp_backend.exception.ResourceNotFoundException;
import brokeerp_backend.repository.PolizaRepository;
import brokeerp_backend.repository.PolizaVehiculoRepository;
import brokeerp_backend.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PolizaVehiculoService {

    @Autowired
    private PolizaVehiculoRepository polizaVehiculoRepository;
    @Autowired
    private PolizaRepository polizaRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<PolizaVehiculo> listarTodos() {
        return polizaVehiculoRepository.findAll();
    }

    public List<PolizaVehiculo> listarPorPoliza(Integer idPoliza) {
        return polizaVehiculoRepository.findByPolizaIdPoliza(idPoliza);
    }

    public List<PolizaVehiculo> listarPorVehiculo(Integer idVehiculo) {
        return polizaVehiculoRepository.findByVehiculoIdVehiculo(idVehiculo);
    }

    @Transactional
    public PolizaVehiculo asociar(Integer idPoliza, Integer idVehiculo) {
        if (polizaVehiculoRepository.existsByPolizaIdPolizaAndVehiculoIdVehiculo(idPoliza, idVehiculo)) {
            throw new BadRequestException("El vehículo ya está asociado a esta póliza");
        }
        Poliza poliza = polizaRepository.findById(idPoliza)
                .orElseThrow(() -> new ResourceNotFoundException("Póliza no encontrada"));
        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));

        PolizaVehiculo pv = new PolizaVehiculo();
        pv.setPoliza(poliza);
        pv.setVehiculo(vehiculo);
        return polizaVehiculoRepository.save(pv);
    }

    public void desasociar(Integer idPolizaVehiculo) {
        polizaVehiculoRepository.deleteById(idPolizaVehiculo);
    }
}