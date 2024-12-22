package com.uees.mgra.inventario.service;

import com.uees.mgra.inventario.handler.BadRequestException;
import com.uees.mgra.inventario.handler.NotFoundException;
import com.uees.mgra.inventario.mapper.InsumoMapper;
import com.uees.mgra.inventario.modals.dto.InsumoDTO;
import com.uees.mgra.inventario.modals.entity.Insumo;
import com.uees.mgra.inventario.modals.entity.InsumoDetalle;
import com.uees.mgra.inventario.repository.InsumoRepository;
import com.uees.mgra.inventario.repository.InsumoDetalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsumoService {

    private final InsumoDetalleRepository insumoDetalleRepository;
    private final InsumoRepository insumoRepository;
    private final InsumoMapper insumoMapper;

    public InsumoService(InsumoRepository insumoRepository, InsumoDetalleRepository insumoDetalleRepository, InsumoMapper insumoMapper) {
        this.insumoRepository = insumoRepository;
        this.insumoDetalleRepository = insumoDetalleRepository;
        this.insumoMapper = insumoMapper;
    }

    public InsumoDTO traerInsumo(Long id) throws BadRequestException, NotFoundException {
        if (id == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }

        Optional<Insumo> insumo = this.insumoRepository.findById(id);

        if (insumo.isEmpty()) {
            throw new NotFoundException("TipoInsumo no encontrado");
        }
        return this.insumoMapper.apply(insumo.get());
    }

    public List<InsumoDTO> listarInsumo() {
        return this.insumoRepository.findAll().stream().map(insumoMapper).toList();
    }

    public InsumoDTO registrarInsumo(InsumoDTO insumoDTO) throws BadRequestException, NotFoundException {
        if (insumoDTO == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }
        InsumoDetalle tipoProducto = this.insumoDetalleRepository.findById(insumoDTO.insumoIdpk()).orElseThrow(()->new NotFoundException("Tipo insumo no existe"));
        validateInsumo(insumoDTO);
        Insumo insumo = new Insumo();
        insumo.setTipoInsumoIdPk(tipoProducto);
        insumo.setCantidad(insumoDTO.cantidad());
        insumo.setFechaFabricacion(insumoDTO.fechaFabricacion());
        insumo.setFechaExpiracion(insumoDTO.fechaExpiracion());
        insumo.setEsCaducado(insumoDTO.esCaducado());
        Insumo save = this.insumoRepository.save(insumo);
        return this.insumoMapper.apply(save);
    }

    private void validateInsumo(InsumoDTO insumoDTO) throws  BadRequestException {
        if (insumoDTO.cantidad() < 0) {
            throw new BadRequestException("cantidad no puede ser menor a 0");
        }
        if (insumoDTO.fechaExpiracion().isBefore(insumoDTO.fechaFabricacion())) {
            throw new BadRequestException("Fecha de expiracion no puede ser antes de la de fabricacion");
        }
    }

    public InsumoDTO actualizarInsumo(Long id, InsumoDTO insumoDTO) throws NotFoundException, BadRequestException {
        Insumo insumo = this.insumoRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe el insumo"));
        validateInsumo(insumoDTO);
        insumo.setCantidad(insumoDTO.cantidad());
        insumo.setFechaExpiracion(insumoDTO.fechaExpiracion());
        insumo.setFechaFabricacion(insumoDTO.fechaFabricacion());
        insumo.setEsCaducado(insumo.isEsCaducado());
        this.insumoRepository.save(insumo);
        return this.insumoMapper.apply(insumo);
    }

    public void eliminarInsumo(Long id) throws BadRequestException, NotFoundException {
        if (id == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }
        Insumo insumo = this.insumoRepository.findById(id).orElseThrow(() -> new NotFoundException("No exite el insumo"));
        this.insumoRepository.delete(insumo);

    }
}
