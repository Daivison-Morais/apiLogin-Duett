package com.login.api.enums;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role; 

    UserRole(String role){ 
        this.role = role.toString();
    }

    public String getRole(){
        return role.toString();
    }
}
