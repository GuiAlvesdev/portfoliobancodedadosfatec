package com.api.painelvendas.controllers;


import com.api.painelvendas.converters.ClienteConverter;
import com.api.painelvendas.dtos.ClienteDto;
import com.api.painelvendas.dtos.ClienteGetDto;
import com.api.painelvendas.models.Cliente;
import com.api.painelvendas.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500", maxAge = 3600)
@RequestMapping("/cliente")
public class ClienteController {

    final ClienteService clienteService;
    final ClienteConverter clienteConverter;

    public ClienteController(ClienteService clienteService, ClienteConverter clienteConverter) {
        this.clienteService = clienteService;
        this.clienteConverter = clienteConverter;
    }
    @PostMapping
    public ResponseEntity<Object> saveCliente(@RequestBody @Valid ClienteDto clienteDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(clienteDto));
    }

    @GetMapping
    public ResponseEntity<List<ClienteGetDto>> getAllClientes() {
        return ResponseEntity.status(HttpStatus.OK).body(
                clienteConverter.convert(clienteService.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCliente(@PathVariable(value = "id") Integer id) {
        Optional<Cliente> clienteModelOptional = clienteService.findById(id);
        if (!clienteModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(clienteModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCliente(@PathVariable(value = "id") Integer id) {
        Optional<Cliente> clienteModelOptional = clienteService.findById(id);
        if (!clienteModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        }
        clienteService.delete(clienteModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCliente(@PathVariable(value = "id") Integer id,
                                                @RequestBody @Valid ClienteDto clienteDto) {
        Optional<Cliente> clienteModelOptional = clienteService.findById(id);
        if (!clienteModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        clienteDto.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.save(clienteDto));
        }
    }
