package com.login.api.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.login.api.controller.config.exception.ApiExceptionMessage;
import com.login.api.dto.BodyUserDTO;
import com.login.api.model.UserModel;
import com.login.api.repository.SignUpRepository;

@Service
public class UserService {

    @Autowired
    private SignUpRepository repository;

    public List<UserModel> listUsers() {
        return repository.findAll();
    }

    public UserModel createSignUpService(BodyUserDTO data) throws ApiExceptionMessage {

        UserModel userExist = repository.findByEmail(data.email());
        if(userExist != null) throw new ApiExceptionMessage(HttpStatus.BAD_REQUEST, "Email já em uso!");
        
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel newUser = new UserModel(data.name(), data.email(),encryptedPassword, data.role(), data.cpf());
        
        UserModel user = repository.save(newUser);
        return user;
    }
    
    public void deleteUserById(String id) throws ApiExceptionMessage {
        if (id == null) throw new ApiExceptionMessage(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        
        UserModel user = repository.findById(id).orElse(null);
        if (user == null) throw new ApiExceptionMessage(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        
        repository.deleteById(id);
        
    }

    
}