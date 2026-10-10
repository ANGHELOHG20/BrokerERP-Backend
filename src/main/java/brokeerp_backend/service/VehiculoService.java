package brokeerp_backend.service;

import brokeerp_backend.entity.Vehiculo;
import brokeerp_backend.exception.BadRequestException;
import brokeerp_backend.exception.ResourceNotFoundException;
import brokeerp_backend.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> listarTodos() {
        return vehiculoRepository.findByActivoTrue();
    }

    public Vehiculo buscarPorId(Integer id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con id: " + id));
    }

    public List<Vehiculo> listarPorCliente(Integer idCliente) {
        return vehiculoRepository.findByClienteIdCliente(idCliente);
    }

    public Vehiculo guardar(Vehiculo vehiculo) {
        if (vehiculo.getIdVehiculo() == null
                && vehiculoRepository.existsByPlaca(vehiculo.getPlaca())) {
            throw new BadRequestException("Ya existe un vehículo con esa placa");
        }
        return vehiculoRepository.save(vehiculo);
    }

    public void eliminar(Integer id) {
        Vehiculo v = buscarPorId(id);
        v.setActivo(false);
        vehiculoRepository.save(v);
    }
}