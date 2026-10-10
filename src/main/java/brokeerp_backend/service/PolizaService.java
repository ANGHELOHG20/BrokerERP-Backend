package brokeerp_backend.service;

import brokeerp_backend.entity.*;
import brokeerp_backend.exception.BadRequestException;
import brokeerp_backend.exception.ResourceNotFoundException;
import brokeerp_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PolizaService {

    @Autowired
    private PolizaRepository polizaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private AseguradoraRepository aseguradoraRepository;
    @Autowired
    private TipoSeguroRepository tipoSeguroRepository;

    public List<Poliza> listarTodas() {
        return polizaRepository.findByActivoTrue();
    }

    public Poliza buscarPorId(Integer id) {
        return polizaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Póliza no encontrada con id: " + id));
    }

    public List<Poliza> listarPorCliente(Integer idCliente) {
        return polizaRepository.findByClienteIdCliente(idCliente);
    }

    public List<Poliza> buscar(String filtro) {
        if (filtro == null || filtro.isBlank())
            return listarTodas();
        return polizaRepository.buscarPorNumero(filtro);
    }

    @Transactional
    public Poliza guardar(Poliza poliza) {
        if (poliza.getNumeroPoliza() == null || poliza.getNumeroPoliza().isBlank()) {
            throw new BadRequestException("El número de póliza es obligatorio");
        }
        if (poliza.getCliente() == null || poliza.getCliente().getIdCliente() == null) {
            throw new BadRequestException("Debe seleccionar un cliente");
        }
        if (poliza.getAseguradora() == null || poliza.getAseguradora().getIdAseguradora() == null) {
            throw new BadRequestException("Debe seleccionar una aseguradora");
        }
        if (poliza.getTipoSeguro() == null || poliza.getTipoSeguro().getIdTipoSeguro() == null) {
            throw new BadRequestException("Debe seleccionar un tipo de seguro");
        }
        return polizaRepository.save(poliza);
    }

    public void eliminar(Integer id) {
        Poliza p = buscarPorId(id);
        p.setActivo(false);
        polizaRepository.save(p);
    }
}