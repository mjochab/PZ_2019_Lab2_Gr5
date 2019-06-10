package ur.inf.lab2.pz.servicemanmanagement.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.Client;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.NewClientDTO;
import ur.inf.lab2.pz.servicemanmanagement.repository.ClientRepository;

/**
 * Klaca obsługująca tworzenie nowego obiektu NewClientDTO
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Metoda tworząca nowy obiekt NewClientDTO i zapisująca go
     * @param newClientDTO
     */
    public void createNewClient(NewClientDTO newClientDTO) {

        Client client = new Client();
        BeanUtils.copyProperties(newClientDTO, client);
        clientRepository.save(client);
    }

}
