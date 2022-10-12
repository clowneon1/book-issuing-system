package com.clowneon1.bookissuingsystem.service;

import com.clowneon1.bookissuingsystem.exception.ResourceNotFoundException;
import com.clowneon1.bookissuingsystem.model.User;
import com.clowneon1.bookissuingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImp implements UserService{

    private UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
    }

    @Override
    public User createUser(User userRequest) {
        return userRepository.save(userRequest);
    }

    @Override
    public User updateUser(long userId, User userDetails) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + userId));

        user.setFullName(userDetails.getFullName());
        user.setPhoneNo(userDetails.getPhoneNo());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public User patchUser(Long userId, Map<Object, Object> fields) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + userId));

        fields.forEach((k,v) ->{
            Field field = ReflectionUtils.findField(User.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, v);
        });
        return userRepository.save(user);
    }
}
