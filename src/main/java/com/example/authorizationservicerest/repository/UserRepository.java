package com.example.authorizationservicerest.repository;

import com.example.authorizationservicerest.domain.User;
import com.example.authorizationservicerest.permission.Authorities;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class UserRepository {
    public List<Authorities> getUserAuthorities(User user) {
        if ("admin".equals(user.getUser()) && "123456789".equals(user.getPassword())) {
            return List.of(Authorities.READ, Authorities.WRITE);
        }
        return Collections.emptyList();
    }
}
