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
import com.login.api.model.UserModel;
import com.login.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/login")
    @Operation(summary = "Permite autenticar usuários")

    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data) {
        try {

            ResponseLoginDTO userToken = service.loginService(data);
            return ResponseEntity.ok(userToken);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();

            error.put("error", e.getMessage());
            return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/signup")
    @Operation(summary = "Realiza cadastro de novos usuários")
    public ResponseEntity<Object> SignUp(@RequestBody @Valid BodyUserDTO req) {
        try {
            service.createSignUpService(req);
            return ResponseEntity.ok("Cadastro realizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();

            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listUsers")
    @Operation(summary = "Lista todos os usuários usuários")

    public ResponseEntity<Object> ListUsers() {
        List<UserModel> Users = service.listUsers();

        return ResponseHandler.responseBuilder(
                " listados com sucesso!", HttpStatus.OK, Users);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Permite à administradores deletar usuários")
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
