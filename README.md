# API Login

Este projeto compõe um sistema de login, que permite registrar e organizar usuários.
</br>
## Deploy Java (Base url)
https://apilogin-duett.onrender.com

## Front-end
https://github.com/Daivison-Morais/login-Duett

## Funcionalidades

- Cadastrar usuários.
- login
- Trocar a senha.
- Listar e deletar usuários, somente, para Administradores.
</br>
## Rode Localmente

Clone o projeto, Navegue até o Diretório do Projeto

```bash
  java -jar ./target/api-0.0.1-SNAPSHOT.jar
```
</br>
## Swagger
Após rodar, cole no browser:
http://localhost:8080/swagger-ui/index.html#/
</br>
## Rode com docker

```bash
  docker build -t app .
```
```bash
  -docker run -p 8080:8080 app
```

## Usuários prontos para login

- daivison@gmail.com 123456 -(ADMIN)
- sandla@gmail.com 123456  -(USER)
- marco@gmail.com 123456  -(USER)