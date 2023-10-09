package com.ahirajustice.configserver.modules.client.services;

import com.ahirajustice.configserver.modules.client.queries.SearchClientsQuery;
import com.ahirajustice.configserver.modules.client.requests.CreateClientRequest;
import com.ahirajustice.configserver.modules.client.requests.UpdateClientRequest;
import com.ahirajustice.configserver.modules.client.viewmodels.ClientViewModel;
import org.springframework.data.domain.Page;

public interface ClientService {

    Page<ClientViewModel> searchClients(SearchClientsQuery query);

    ClientViewModel getClient(long id);

    ClientViewModel createClient(CreateClientRequest request);

    ClientViewModel updateClient(UpdateClientRequest request, long id);

}
