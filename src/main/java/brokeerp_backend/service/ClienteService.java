package brokeerp_backend.service;

import brokeerp_backend.entity.Cliente;
import brokeerp_backend.exception.BadRequestException;
import brokeerp_backend.exception.ResourceNotFoundException;
import brokeerp_backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findByActivoTrue();
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }

    public List<Cliente> buscar(String filtro) {
        if (filtro == null || filtro.isBlank()) {
            return clienteRepository.findByActivoTrue();
        }
        return clienteRepository.buscarPorFiltro(filtro);
    }

    public Cliente guardar(Cliente cliente) {
        // Validar duplicados
        if (cliente.getTipoCliente() == Cliente.TipoCliente.NATURAL) {
            if (cliente.getDni() == null || cliente.getDni().isBlank()) {
                throw new BadRequestException("El DNI es obligatorio para persona natural");
            }
            if (cliente.getIdCliente() == null && clienteRepository.existsByDni(cliente.getDni())) {
                throw new BadRequestException("Ya existe un cliente con ese DNI");
            }
        } else {
            if (cliente.getRuc() == null || cliente.getRuc().isBlank()) {
                throw new BadRequestException("El RUC es obligatorio para persona jurídica");
            }
            if (cliente.getIdCliente() == null && clienteRepository.existsByRuc(cliente.getRuc())) {
                throw new BadRequestException("Ya existe un cliente con ese RUC");
            }
        }
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Integer id, Cliente cliente) {
        Cliente existente = buscarPorId(id);
        existente.setTelefono(cliente.getTelefono());
        existente.setEmail(cliente.getEmail());
        existente.setDireccion(cliente.getDireccion());
        existente.setDireccionFiscal(cliente.getDireccionFiscal());
        existente.setGiroActividad(cliente.getGiroActividad());
        existente.setRepresentanteLegal(cliente.getRepresentanteLegal());
        return clienteRepository.save(existente);
    }

    public void eliminar(Integer id) {
        Cliente cliente = buscarPorId(id);
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }
}