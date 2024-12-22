package com.uees.mgra.producto.controllers;

import com.uees.mgra.producto.handler.BadRequestException;
import com.uees.mgra.producto.handler.NotFoundException;
import com.uees.mgra.producto.model.dto.ProductoDTO;
import com.uees.mgra.producto.model.dto.RequerimientoDTO;
import com.uees.mgra.producto.services.ProductoService;
import com.uees.mgra.producto.services.RequerimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GestorProductoController {
    private final ProductoService productoService;
    private final RequerimientoService requerimientoService;

    public GestorProductoController(ProductoService productoService, RequerimientoService requerimientoService) {
        this.productoService = productoService;
        this.requerimientoService = requerimientoService;
    }

    @GetMapping
    @RequestMapping(path = "/traerProducto/{id}")
    public ResponseEntity<ProductoDTO> traerProducto(@PathVariable("id") Long id) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(this.productoService.traerProducto(id), HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping(path ="/listarProductos")
    public ResponseEntity<List<ProductoDTO>> listarProductos(){
        return new ResponseEntity<>(this.productoService.listarProductos(), HttpStatus.OK);
    }
    @PostMapping
    @RequestMapping(path = "/registrarProducto")
    public ResponseEntity<ProductoDTO> registrarProducto(@RequestBody ProductoDTO productoDTO ) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(this.productoService.registrarProducto(productoDTO), HttpStatus.CREATED);
    }
    @PutMapping
    @RequestMapping(path = "/actualizarProducto/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable("id")  Long id, @RequestBody ProductoDTO productoDTO ) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(this.productoService.actualizarProducto(id, productoDTO), HttpStatus.OK);
    }
    @DeleteMapping
    @RequestMapping("/eliminarProducto/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable("id")  Long id) throws BadRequestException, NotFoundException {
        this.productoService.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping(path = "/traerRequerimiento/{id}")
    public ResponseEntity<RequerimientoDTO> traerRequerimiento(@PathVariable("id") Long id) throws BadRequestException, NotFoundException {
        return new ResponseEntity<RequerimientoDTO>(this.requerimientoService.traerRequerimiento(id), HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping(path ="/listarRequerimientos")
    public ResponseEntity<List<RequerimientoDTO>> listarRequerimiento(){
        return new ResponseEntity<List<RequerimientoDTO>>(this.requerimientoService.listarRequerimiento(), HttpStatus.OK);
    }
    @PostMapping
    @RequestMapping(path = "/registrarRequerimiento")
    public ResponseEntity<RequerimientoDTO> registrarProducto(@RequestBody RequerimientoDTO requerimientoDTO ) throws BadRequestException, NotFoundException {
        return new ResponseEntity<RequerimientoDTO>(this.requerimientoService.registrarRequerimiento(requerimientoDTO), HttpStatus.CREATED);
    }
    @PutMapping
    @RequestMapping(path = "/actualizarRequerimiento/{id}")
    public ResponseEntity<RequerimientoDTO> actualizarRequerimiento(@PathVariable("id")  Long id, @RequestBody RequerimientoDTO requerimientoDTO ) throws BadRequestException, NotFoundException {
        return new ResponseEntity<RequerimientoDTO>(this.requerimientoService.actualizarRequerimiento(id, requerimientoDTO), HttpStatus.OK);
    }
    @DeleteMapping
    @RequestMapping("/eliminarRequerimiento/{id}")
    public ResponseEntity<Void> eliminarRequerimiento(@PathVariable("id")  Long id) throws BadRequestException, NotFoundException {
        this.requerimientoService.eliminarRequerimiento(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
