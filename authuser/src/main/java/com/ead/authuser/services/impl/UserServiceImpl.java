package com.ead.authuser.services.impl;

import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Estamos garantindo um maior encapsulamento separando os métodos da implementação
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

}
