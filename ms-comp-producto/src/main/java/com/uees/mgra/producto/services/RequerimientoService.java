package com.uees.mgra.producto.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.uees.mgra.producto.handler.BadRequestException;
import com.uees.mgra.producto.handler.NotFoundException;
import com.uees.mgra.producto.mappers.RequerimientoMapper;
import com.uees.mgra.producto.model.dto.RequerimientoDTO;
import com.uees.mgra.producto.model.entity.Producto;
import com.uees.mgra.producto.model.entity.Requerimiento;
import com.uees.mgra.producto.repositories.ProductoRepository;
import com.uees.mgra.producto.repositories.RequerimientoRepository;
import com.uees.mgra.producto.util.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RequerimientoService {

    private final ProductoRepository productoRepository;
    private final RequerimientoRepository requerimientoRepository;
    private final RequerimientoMapper requerimientoMapper;
    private final RestService restService;
    private final Gson gson = new Gson();
    private final static Logger log = LoggerFactory.getLogger(RequerimientoService.class);
    @Value("${url.ms.inventario.insumo}")
    private String urlMsInventarioInsumo;

    public RequerimientoService(ProductoRepository productoRepository, RequerimientoRepository requerimientoRepository, RequerimientoMapper requerimientoMapper, RestService restService) {
        this.productoRepository = productoRepository;
        this.requerimientoRepository = requerimientoRepository;
        this.requerimientoMapper = requerimientoMapper;
        this.restService = restService;
    }

    public RequerimientoDTO traerRequerimiento(Long id) throws BadRequestException, NotFoundException {
        if (id == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }

        Optional<Requerimiento> requerimiento = this.requerimientoRepository.findById(id);

        if (!requerimiento.isPresent()) {
            throw new NotFoundException("Producto no encontrado");
        }
        return this.requerimientoMapper.apply(requerimiento.get());
    }

    public List<RequerimientoDTO> listarRequerimiento() {
        return this.requerimientoRepository.findAll().stream().map(requerimientoMapper).toList();
    }

    public RequerimientoDTO registrarRequerimiento(RequerimientoDTO requerimientoDTO) throws BadRequestException, NotFoundException {
        if (requerimientoDTO == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }

        Producto producto = this.productoRepository.findById(requerimientoDTO.productoId()).orElseThrow(()->new NotFoundException("NO SE ENCUENTRA EL PRODUCTO"));
        this.validatExisteRequerimiento(requerimientoDTO);

        Optional<Requerimiento> yaExiste = producto.getRequerimientoSet().stream().filter(requerimiento -> Objects.equals(requerimiento.getTipoInsumoIdFK(), requerimientoDTO.insumoId())).findFirst();

        Requerimiento requerimiento = new Requerimiento();
        if(yaExiste.isPresent()){
            requerimiento = yaExiste.get();
            requerimiento.setCantidad(requerimientoDTO.cantidad());
        }else{
            requerimiento.setProducto_id(producto);
            requerimiento.setTipoInsumoIdFK(requerimientoDTO.insumoId());
            requerimiento.setCantidad(requerimientoDTO.cantidad());
        }
        Requerimiento save = this.requerimientoRepository.save(requerimiento);
        return this.requerimientoMapper.apply(save);
    }

    public RequerimientoDTO actualizarRequerimiento(Long id, RequerimientoDTO requerimientoDTO) throws BadRequestException, NotFoundException {
        Requerimiento requerimiento = this.requerimientoRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe el requerimiento"));
        this.validatExisteRequerimiento(requerimientoDTO);
        requerimiento.setTipoInsumoIdFK(requerimientoDTO.insumoId());
        requerimiento.setCantidad(requerimientoDTO.cantidad());
        this.requerimientoRepository.save(requerimiento);
        return this.requerimientoMapper.apply(requerimiento);
    }

    public void eliminarRequerimiento(Long id) throws BadRequestException, NotFoundException {
        if (id == null) {
            throw new BadRequestException("ERROR EN LA PETICION");
        }

        boolean existe = this.requerimientoRepository.findById(id).isPresent();
        if (!existe) {
            throw new NotFoundException("NO EXISTE EL REQUERIMIENTO");
        }

        this.requerimientoRepository.deleteById(id);
    }

    private void validatExisteRequerimiento(RequerimientoDTO requerimientoDTO) throws BadRequestException {
        try {
            String response = restService.invokeRest(HttpMethod.GET, urlMsInventarioInsumo + "/" + requerimientoDTO.insumoId() , null, null, null);
            log.info("RESPUESTA MS-INVENTARIO {}", response);
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
            if (!Objects.isNull(jsonObject.get("details"))) {
                throw new NotFoundException(jsonObject.get("details").getAsString());
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
