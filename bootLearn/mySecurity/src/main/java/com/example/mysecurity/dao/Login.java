package com.example.mysecurity.dao;

public record Login(String username, String password, boolean remember, RoleEnum roleType) {
}
