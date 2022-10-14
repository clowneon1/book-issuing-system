package com.clowneon1.bookissuingsystem.controller;

import com.clowneon1.bookissuingsystem.model.User;
import com.clowneon1.bookissuingsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v3")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User userRequest){
        return new ResponseEntity<>(userService.createUser(userRequest),HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long userId, @RequestBody User userDetails){
       return new ResponseEntity<>(userService.updateUser(userId,userDetails),HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> patchUser(@PathVariable("id") long userId, @RequestBody Map<Object,Object> fields){
        return new ResponseEntity<>(userService.patchUser(userId,fields),HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted",HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllUsers();
        return new ResponseEntity<>("All users deleted",HttpStatus.OK);
    }

}
