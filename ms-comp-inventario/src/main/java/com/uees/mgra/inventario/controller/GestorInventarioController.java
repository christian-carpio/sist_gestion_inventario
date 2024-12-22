package com.uees.mgra.inventario.controller;

import com.uees.mgra.inventario.handler.BadRequestException;
import com.uees.mgra.inventario.handler.NotFoundException;
import com.uees.mgra.inventario.modals.dto.InsumoDetalleDTO;
import com.uees.mgra.inventario.modals.dto.InsumoDTO;
import com.uees.mgra.inventario.service.InsumoService;
import com.uees.mgra.inventario.service.InsumoDetalleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class GestorInventarioController {

    private final InsumoDetalleService insumoDetalleService;

    private final InsumoService insumoService;

    public GestorInventarioController(InsumoDetalleService insumoDetalleService, InsumoService insumoService) {
        this.insumoDetalleService = insumoDetalleService;
        this.insumoService = insumoService;
    }

    @GetMapping
    @RequestMapping(path = "/insumo-detalle/traerInsumoDetalle/{id}")
    public ResponseEntity<InsumoDetalleDTO> traerTipoInsumo(@PathVariable("id") Long id) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(this.insumoDetalleService.traerTipoInsumo(id), HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping(path = "/insumo-detalle/listarInsumoDetalles")
    public ResponseEntity<List<InsumoDetalleDTO>> listarTipoInsumos() {
        return new ResponseEntity<>(this.insumoDetalleService.listarTipoInsumos(), HttpStatus.OK);
    }

    @PostMapping
    @RequestMapping(path = "/insumo-detalle/registrarInsumoDetalle")
    public ResponseEntity<InsumoDetalleDTO> registrarTipoInsumo(@RequestBody InsumoDetalleDTO productoDTO) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(this.insumoDetalleService.registrarTipoInsumo(productoDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @RequestMapping(path = "/insumo-detalle/actualizarInsumoDetalle/{id}")
    public ResponseEntity<InsumoDetalleDTO> actualizarTipoInsumo(@PathVariable("id") Long id, @RequestBody InsumoDetalleDTO productoDTO) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(this.insumoDetalleService.actualizarTipoInsumo(id, productoDTO), HttpStatus.OK);
    }

    @DeleteMapping
    @RequestMapping(path = "/insumo-detalle/eliminarInsumoDetalle/{id}")
    public ResponseEntity<Void> eliminarTipoInsumo(@PathVariable("id") Long id) throws BadRequestException, NotFoundException {
        this.insumoDetalleService.eliminarTipoInsumo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping
    @RequestMapping(path = "/insumo/traerInsumo/{id}")
    public ResponseEntity<InsumoDTO> traerInsumo(@PathVariable("id") Long id) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(this.insumoService.traerInsumo(id), HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping(path = "/insumo/listarInsumos")
    public ResponseEntity<List<InsumoDTO>> listarInsumo() {
        return new ResponseEntity<>(this.insumoService.listarInsumo(), HttpStatus.OK);
    }

    @PostMapping
    @RequestMapping(path = "/insumo/registrarInsumo")
    public ResponseEntity<InsumoDTO> registrarInsumo(@RequestBody InsumoDTO requerimientoDTO) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(this.insumoService.registrarInsumo(requerimientoDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @RequestMapping(path = "/insumo/actualizarInsumo/{id}")
    public ResponseEntity<InsumoDTO> actualizarInsumo(@PathVariable("id") Long id, @RequestBody InsumoDTO requerimientoDTO) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(this.insumoService.actualizarInsumo(id, requerimientoDTO), HttpStatus.OK);
    }

    @DeleteMapping
    @RequestMapping(path = "/insumo/eliminarInsumo/{id}")
    public ResponseEntity<Void> eliminarInsumo(@PathVariable("id") Long id) throws BadRequestException, NotFoundException {
        this.insumoService.eliminarInsumo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

