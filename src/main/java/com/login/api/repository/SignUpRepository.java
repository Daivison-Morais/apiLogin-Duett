package com.login.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.login.api.dto.BodyUserDTO;
import com.login.api.model.UserModel;

@Repository
public interface SignUpRepository extends JpaRepository<UserModel, String> {

    UserModel save(BodyUserDTO req);

    UserModel findByEmail(String email);

}
