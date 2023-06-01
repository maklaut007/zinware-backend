package com.example.zinware.repository;

public interface UserRepository {
    boolean existByEmail(String email);
}
