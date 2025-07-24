package com.example.client_service.controller;

import com.example.client_service.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.client_service.service.ClientService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    //Controller for getting all clients
    @GetMapping("/")
    public List<ClientEntity> getAllClients() {
        return clientService.getClients();
    }

    //Controller for getting a client by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Controller for getting a client by Rut
    @GetMapping("/rut/{rut}")
    public ResponseEntity<ClientEntity> getClientByRut(@PathVariable String rut) {
        ClientEntity client = clientService.getClientByRut(rut);
        return client != null ? ResponseEntity.ok(client) : ResponseEntity.notFound().build();
    }

    //Controller for getting a client by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<ClientEntity> getClientByEmail(@PathVariable String email) {
        return clientService.getClientByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Controller for getting a client by Name
    @GetMapping("/name/{name}")
    public List<ClientEntity> getClientsByName(@PathVariable String name) {
        return clientService.getClientsByName(name);
    }

    //Controller for getting a client by Mountly Visits
    @GetMapping("/monthlyVisits/{visits}")
    public List<ClientEntity> getClientsByMonthlyVisits(@PathVariable int visits) {
        return clientService.getClientsByMonthlyVisits(visits);
    }

    //Controller for getting a client by Birthdate
    @GetMapping("/birthDate/{birthDate}")
    public List<ClientEntity> getClientsByBirthDate(@PathVariable String birthDate) {
        LocalDate date = LocalDate.parse(birthDate);
        return clientService.getClientsByBirthDate(date);
    }

    //Controller for updating a client by ID
    @PutMapping("/{id}")
    public ResponseEntity<ClientEntity> updateClient(@PathVariable Long id, @RequestBody ClientEntity client) {
        if (!clientService.getClientById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        client.setId(id);
        return ResponseEntity.ok(clientService.updateClient(client));
    }

    //Controller for deleting a client by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    //Controller for creating a client by ID
    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody ClientEntity client) {
        try {
            ClientEntity newClient = clientService.registerClient(client);
            return ResponseEntity.ok(newClient);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}