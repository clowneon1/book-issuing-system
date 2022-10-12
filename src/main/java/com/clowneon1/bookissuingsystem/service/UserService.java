package com.clowneon1.bookissuingsystem.service;

import com.clowneon1.bookissuingsystem.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface UserService {

    public List<User> getAllUsers();
    public User getUserById(long userId);
    public User createUser(User userRequest);
    public User updateUser(long userId,User userDetails);
    public void deleteUser(long userId);
    public void deleteAllUsers();
    public User patchUser(Long userId, Map<Object,Object> fields);
}
