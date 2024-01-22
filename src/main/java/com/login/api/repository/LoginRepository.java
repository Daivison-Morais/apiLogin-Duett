package com.login.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import com.login.api.dto.BodyUserDTO;
import com.login.api.model.LoginModel;

@Repository
public interface LoginRepository extends JpaRepository<LoginModel, String> {
    LoginModel save(BodyUserDTO req);

    UserDetails findByEmail(String email);
}  