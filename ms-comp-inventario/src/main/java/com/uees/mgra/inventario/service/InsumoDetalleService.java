package com.uees.mgra.inventario.service;

import com.uees.mgra.inventario.handler.BadRequestException;
import com.uees.mgra.inventario.handler.NotFoundException;
import com.uees.mgra.inventario.mapper.InsumoDetalleInsumoMapper;
import com.uees.mgra.inventario.modals.dto.InsumoDetalleDTO;
import com.uees.mgra.inventario.modals.entity.InsumoDetalle;
import com.uees.mgra.inventario.repository.InsumoDetalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InsumoDetalleService {

    private final InsumoDetalleRepository insumoDetalleRepository;
    private final InsumoDetalleInsumoMapper insumoDetalleInsumoMapper;

    public InsumoDetalleService(InsumoDetalleRepository insumoDetalleRepository, InsumoDetalleInsumoMapper insumoDetalleInsumoMapper) {
        this.insumoDetalleRepository = insumoDetalleRepository;
        this.insumoDetalleInsumoMapper = insumoDetalleInsumoMapper;
    }

    public List<InsumoDetalleDTO> listarTipoInsumos() {
        return insumoDetalleRepository.findAll().stream().map(insumoDetalleInsumoMapper).collect(Collectors.toList());
    }

    public InsumoDetalleDTO traerTipoInsumo(Long id) throws NotFoundException, BadRequestException {
        if (id == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }

        Optional<InsumoDetalle> productoSet = this.insumoDetalleRepository.findById(id);

        if (!productoSet.isPresent()) {
            throw new NotFoundException("TipoInsumo no encontrado");
        }
        Optional<InsumoDetalleDTO> first = Stream.of(productoSet.get()).map(insumoDetalleInsumoMapper).findFirst();

        return first.get();
    }

    public InsumoDetalleDTO actualizarTipoInsumo(Long id, InsumoDetalleDTO tipoinsumoDetalleDto) throws BadRequestException, NotFoundException {
        if (id == null || tipoinsumoDetalleDto == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }
        InsumoDetalle insumoDetalle = this.insumoDetalleRepository.findById(id).orElseThrow(() -> new NotFoundException("NO EXISTE EL TIPO DE INSUMO"));
        insumoDetalle.setDescripcion(tipoinsumoDetalleDto.descripcion());
        insumoDetalle.setNombre(tipoinsumoDetalleDto.nombre());
        this.insumoDetalleRepository.save(insumoDetalle);
        return this.insumoDetalleInsumoMapper.apply(insumoDetalle);
    }

    public InsumoDetalleDTO registrarTipoInsumo(InsumoDetalleDTO productoDTO) throws BadRequestException, NotFoundException {
        if (productoDTO == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }

        Optional<InsumoDetalle> byNombreEqualsIgnoreCase = this.insumoDetalleRepository.findByNombreEqualsIgnoreCase(productoDTO.nombre());
        if (byNombreEqualsIgnoreCase.isPresent()) {
            throw new BadRequestException("ERROR YA EXISTE UN TIPO INSUMO CON ESE NOMBRE");
        }
        InsumoDetalle producto = new InsumoDetalle();
        producto.setNombre(productoDTO.nombre());
        producto.setDescripcion(productoDTO.descripcion());

        InsumoDetalle saved = this.insumoDetalleRepository.save(producto);
        return this.insumoDetalleInsumoMapper.apply(saved);
    }

    public void eliminarTipoInsumo(Long id) throws BadRequestException, NotFoundException {
        if (id == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }

        Optional<InsumoDetalle> producto = Optional.ofNullable(insumoDetalleRepository.findById(id).orElseThrow(() -> new NotFoundException("NO EXISTE EL PRODUCTO")));

        if (producto.isPresent()) {
            this.insumoDetalleRepository.deleteById(id);
        }
    }


    public List<InsumoDetalleDTO> findByIds(List<Long> ids){
        List<InsumoDetalle> allById = this.insumoDetalleRepository.findAllById(ids);
        return allById.stream().map(insumoDetalleInsumoMapper).collect(Collectors.toList());
    }



}
