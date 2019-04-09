package ur.inf.lab2.pz.servicemanmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ServicemanFirstLoginDTO;

import java.io.IOException;

@Service
public class ServicemanFirstLoginService {

    @Autowired
    UserService userService;

    public void servicemanLogin(ServicemanFirstLoginDTO servicemanFirstLoginDTO) throws IOException {
        validate(servicemanFirstLoginDTO);
        userService.changePersonalData(servicemanFirstLoginDTO);
    }

    public void validate(ServicemanFirstLoginDTO data) throws IOException {
        if ("".equals(data.getFirstName()) || "".equals(data.getLastName()) || "".equals(data.getPassword()) || "".equals(data.getConfirmPassword()) ||
                !data.getPassword().equals(data.getConfirmPassword()))
            throw new IOException();
    }
}
