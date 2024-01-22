package com.login.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.login.api.controller.config.response.ResponseHandler;
import com.login.api.dto.AuthenticationDTO;
import com.login.api.dto.BodyUserDTO;
import com.login.api.dto.ResponseLoginDTO;
import com.login.api.infra.security.TokenService;
import com.login.api.model.UserModel;
import com.login.api.services.UserService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("auth")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService service;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), 
            data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new ResponseLoginDTO(token,
         "Login realizado com sucesso!"));
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();

            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> SignUp(@RequestBody @Valid BodyUserDTO req) {
        try {
            UserModel user = service.createSignUpService(req);
            return ResponseHandler.responseBuilder(
                "Cadastro realizado com sucesso!", HttpStatus.CREATED, user);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();

            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Object> ListUsers() {
        List<UserModel> Users = service.listUsers();

        return ResponseHandler.responseBuilder(
            " listados com sucesso!", HttpStatus.OK, Users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {

        try {
            service.deleteUserById(id);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();

            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

    }
    
}
