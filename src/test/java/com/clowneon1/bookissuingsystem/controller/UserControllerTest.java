package com.clowneon1.bookissuingsystem.controller;

import com.clowneon1.bookissuingsystem.model.Section;
import com.clowneon1.bookissuingsystem.model.User;
import com.clowneon1.bookissuingsystem.model.UserSection;
import com.clowneon1.bookissuingsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {
    
    private MockMvc mockMvc;
    
    @Mock
    UserService userService;
    
    @InjectMocks
    UserController userController;

    User u1 = User.builder().id(1L).fullName("yash").email("yash@gmail.com").phoneNo("+91 2323232323").build();
    User u2 = User.builder().id(2L).fullName("aadi").email("nama@gmail.com").phoneNo("+91 2323232323").build();
    User u3 = User.builder().id(3L).fullName("namna").email("nama@gmail.com").phoneNo("+91 2323223323").build();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getAllUsers() throws Exception{
        List<User> records = new ArrayList<>(Arrays.asList(u1,u2,u3));

        when(userService.getAllUsers()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v3/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].fullName", is(u1.getFullName())));
    }

    @Test
    public void getUserById() throws Exception{
        when(userService.getUserById(anyLong())).thenReturn(u1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v3/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.fullName", is(u1.getFullName())))
                .equals(u1);
    }

    @Test
    public void createUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void patchUser() {
    }

    @Test
    public void deleteUser() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v3/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .equals("user deleted");

        verify(userService, times(1)).deleteUser(anyLong());
    }

    @Test
    public void deleteAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v3/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .equals("All users deleted");

        verify(userService, times(1)).deleteAllUsers();
    }
}