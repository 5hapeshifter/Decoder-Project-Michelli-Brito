package com.ead.authuser.repositories;

import com.ead.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// O Spring já entendi que essa classe é um Bean que ele gerenciará, portanto não precisamos anotar com @Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {



}