package com.csys.template.factory;

import com.csys.template.domain.Client;
import com.csys.template.dto.ClientDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientFactory {
  public static ClientDTO clientToClientDTO(Client client) {
    ClientDTO clientDTO=new ClientDTO();
    clientDTO.setIdClient(client.getIdClient());
    clientDTO.setNom(client.getNom());
    clientDTO.setTelephone(client.getTelephone());
    clientDTO.setEmail(client.getEmail());
    clientDTO.setAdresse(client.getAdresse());
    return clientDTO;
  }

  public static Client clientDTOToClient(ClientDTO clientDTO) {
    Client client=new Client();
    client.setIdClient(clientDTO.getIdClient());
    client.setNom(clientDTO.getNom());
    client.setTelephone(clientDTO.getTelephone());
    client.setEmail(clientDTO.getEmail());
    client.setAdresse(clientDTO.getAdresse());
    return client;
  }

  public static Collection<ClientDTO> clientToClientDTOs(Collection<Client> clients) {
    List<ClientDTO> clientsDTO=new ArrayList<>();
    clients.forEach(x -> {
      clientsDTO.add(clientToClientDTO(x));
    } );
    return clientsDTO;
  }
}

