package com.example.client_service.service;

import com.example.client_service.entity.ClientEntity;
import com.example.client_service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Connections with other microservice
    private final String DISCOUNT_VISIT_SERVICE_URL = "http://discount-visit-service/api/v1/discounts_visit/assign";
    private final String DISCOUNT_DAY_SERVICE_URL = "http://discount-day-service/api/v1/discounts_day/";

    // Get all clients (service method)
    public List<ClientEntity> getClients() {
        return clientRepository.findAll();
    }

    // Get clients by id (service method)
    public Optional<ClientEntity> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    // Get clients by rut (service method)
    public ClientEntity getClientByRut(String rut) {
        return clientRepository.findByRut(rut);
    }

    // Get clients by email (service method)
    public Optional<ClientEntity> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    // Get clients by name (service method)
    public List<ClientEntity> getClientsByName(String name) {
        return clientRepository.findByName(name);
    }

    // Get clients by visit (service method)
    public List<ClientEntity> getClientsByMonthlyVisits(int visits) {
        return clientRepository.findByMonthlyVisits(visits);
    }

    // Get clients by birthday (service method)
    public List<ClientEntity> getClientsByBirthDate(LocalDate birthDate) {
        return clientRepository.findByBirthDate(birthDate);
    }

    // Update a customer service
    public ClientEntity updateClient(ClientEntity client) {
        ClientEntity updatedClient = clientRepository.save(client);
        notifyDiscountService(client);
        notifyDiscountDayService(client);
        /*restTemplate.postForObject(
                DISCOUNT_VISIT_SERVICE_URL + "?clientId=" + updatedClient.getId() +
                        "&monthlyVisits=" + updatedClient.getMonthlyVisits(),
                null,
                Void.class
        );*/
        return updatedClient;
    }

    // Delete a customer service
    public boolean deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Create a new customer service
    public ClientEntity registerClient(ClientEntity client) {
        Optional<ClientEntity> existingClient = Optional.ofNullable(clientRepository.findByRut(client.getRut()));
        if (existingClient.isPresent()) {
            throw new RuntimeException("El RUT ya está registrado");
        }
        ClientEntity newClient = clientRepository.save(client);
        notifyDiscountService(newClient);
        /*restTemplate.postForObject(   This is discount service
                DISCOUNT_VISIT_SERVICE_URL + "?clientId=" + newClient.getId() +
                        "&monthlyVisits=" + newClient.getMonthlyVisits(),
                null,
                Void.class
        );*/
        return newClient;
    }


    // Notify discount visit service (service method)
    private void notifyDiscountService(ClientEntity client) {
        restTemplate.postForObject(
                DISCOUNT_VISIT_SERVICE_URL + "?clientId=" + client.getId() +
                        "&monthlyVisits=" + client.getMonthlyVisits(),
                null,
                Void.class
        );
    }

    // Notify discount day (service method)
    private void notifyDiscountDayService(ClientEntity client) {
        LocalDate birthday = client.getBirthDate();
        restTemplate.put(
                DISCOUNT_DAY_SERVICE_URL + "/recalculate?clientId=" + client.getId()
                        + "&reservationDate=" + birthday,
                null
        );
    }
}

