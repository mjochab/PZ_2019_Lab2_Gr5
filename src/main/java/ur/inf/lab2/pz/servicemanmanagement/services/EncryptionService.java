package ur.inf.lab2.pz.servicemanmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Klasa wkorzystywana do kodowania hasła
 */
@Service
public class EncryptionService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}