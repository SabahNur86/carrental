package com.carrentalproject.service;

import com.carrentalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor //autowired kullanmaya gerek kalmasın diye
                    // ioc konteynırında olusturulan bean ı burda kullanmak icin
@Service
public class UserService {

    private final UserRepository userRepository;

}
