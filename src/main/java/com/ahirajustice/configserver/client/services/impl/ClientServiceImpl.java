package com.ahirajustice.configserver.client.services.impl;

import com.ahirajustice.configserver.client.queries.SearchClientsQuery;
import com.ahirajustice.configserver.client.requests.CreateClientRequest;
import com.ahirajustice.configserver.client.requests.UpdateClientRequest;
import com.ahirajustice.configserver.client.services.ClientService;
import com.ahirajustice.configserver.client.viewmodels.ClientViewModel;
import com.ahirajustice.configserver.common.entities.Client;
import com.ahirajustice.configserver.common.exceptions.BadRequestException;
import com.ahirajustice.configserver.common.exceptions.NotFoundException;
import com.ahirajustice.configserver.common.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Page<ClientViewModel> searchClients(SearchClientsQuery query) {
        return clientRepository.findAll(query.getPredicate(), query.getPageable()).map(ClientViewModel::from);
    }

    @Override
    public ClientViewModel getClient(long id) {
        Optional<Client> clientExists = clientRepository.findById(id);

        if (!clientExists.isPresent()) {
            throw new NotFoundException(String.format("Client with id: '%d' does not exist", id));
        }

        return ClientViewModel.from(clientExists.get());
    }

    @Override
    public ClientViewModel createClient(CreateClientRequest request) {
        if (clientRepository.existsByIdentifier(request.getIdentifier())) {
            throw new BadRequestException(String.format("Client with identifier: '%s' already exists", request.getIdentifier()));
        }

        Client client = buildClient(request);
        client.setActive(true);

        return ClientViewModel.from(clientRepository.save(client));
    }

    private Client buildClient(CreateClientRequest request) {
        return Client.builder()
                .identifier(request.getIdentifier())
                .secret(passwordEncoder.encode(request.getSecret()))
                .name(request.getIdentifier())
                .adminEmail(request.getAdminEmail())
                .refreshCallbackUrl(request.getRestartCallbackUrl())
                .publicKey(request.getPublicKey())
                .build();
    }

    @Override
    public ClientViewModel updateClient(UpdateClientRequest request, long id) {
        Optional<Client> clientExists = clientRepository.findById(id);

        if (!clientExists.isPresent()) {
            throw new NotFoundException(String.format("Client with id: '%d' does not exist", id));
        }

        Client client = clientExists.get();

        client.setAdminEmail(request.getAdminEmail());
        client.setRefreshCallbackUrl(request.getRestartCallbackUrl());

        return ClientViewModel.from(clientRepository.save(client));
    }

}
