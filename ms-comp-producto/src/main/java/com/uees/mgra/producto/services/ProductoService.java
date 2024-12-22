package com.uees.mgra.producto.services;

import com.uees.mgra.producto.handler.BadRequestException;
import com.uees.mgra.producto.handler.NotFoundException;
import com.uees.mgra.producto.mappers.ProductoRequerimientosMapper;
import com.uees.mgra.producto.model.dto.ProductoDTO;
import com.uees.mgra.producto.model.entity.Producto;
import com.uees.mgra.producto.repositories.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final ProductoRequerimientosMapper productoRequerimientosMapper;

    private final static Logger log = LoggerFactory.getLogger(ProductoService.class);

    public ProductoService(ProductoRepository productoRepository, ProductoRequerimientosMapper productoRequerimientosMapper) {
        this.productoRepository = productoRepository;
        this.productoRequerimientosMapper = productoRequerimientosMapper;
    }


    public ProductoDTO traerProducto(Long id) throws BadRequestException, NotFoundException {
        if (id == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }

        Producto productoSet = this.productoRepository.findById(id).orElseThrow(()->  new NotFoundException("No existe producto con ese Id"));

        return productoRequerimientosMapper.apply(productoSet);
    }

    public List<ProductoDTO> listarProductos() {
        return this.productoRepository.findAll().stream().map(productoRequerimientosMapper).collect(Collectors.toList());
    }

    public ProductoDTO registrarProducto(ProductoDTO productoDTO) throws BadRequestException, NotFoundException {
        if (productoDTO == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }
        Optional<Producto> byNombreEqualsIgnoreCase = this.productoRepository.findByNombreEqualsIgnoreCase(productoDTO.nombre());
        if (byNombreEqualsIgnoreCase.isPresent()) {
            throw new BadRequestException("ERROR, YA EXISTE UN PRODUCTO CON ESE NOMBRE");
        }
        Producto producto = new Producto();
        producto.setNombre(productoDTO.nombre());
        producto.setPrecio(productoDTO.precio());
        producto.setDescripcion(productoDTO.descripcion());
        producto.setUserCreacion(productoDTO.nombreCreador());
        Producto save = this.productoRepository.save(producto);
        return this.productoRequerimientosMapper.apply(save);
    }

    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) throws BadRequestException, NotFoundException {
        if (id == null || productoDTO == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }
        Producto productoEntity = this.productoRepository.findById(id).orElseThrow(() -> new NotFoundException("NO EXISTE EL PRODUCTO"));
        productoEntity.setNombre(productoDTO.nombre());
        productoEntity.setUserCreacion(productoDTO.nombreCreador());
        productoEntity.setPrecio(productoDTO.precio());
        productoEntity.setDescripcion(productoDTO.descripcion());
        this.productoRepository.save(productoEntity);
        return this.productoRequerimientosMapper.apply(productoEntity);
    }

    public void eliminarProducto(Long id) throws BadRequestException, NotFoundException {
        if (id == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }

        Optional<Producto> producto = Optional.ofNullable(productoRepository.findById(id).orElseThrow(() -> new NotFoundException("NO EXISTE EL PRODUCTO")));

        if (producto.isPresent()) {
            this.productoRepository.deleteById(id);
        }
    }


}
