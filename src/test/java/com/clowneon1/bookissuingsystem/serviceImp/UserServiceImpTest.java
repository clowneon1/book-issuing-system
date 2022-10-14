package com.clowneon1.bookissuingsystem.serviceImp;

import com.clowneon1.bookissuingsystem.controller.CategoryController;
import com.clowneon1.bookissuingsystem.model.Book;
import com.clowneon1.bookissuingsystem.model.Category;
import com.clowneon1.bookissuingsystem.model.User;
import com.clowneon1.bookissuingsystem.repository.CategoryRepository;
import com.clowneon1.bookissuingsystem.repository.UserRepository;
import com.clowneon1.bookissuingsystem.serviceImp.CategoryServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImpTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userServiceImp).build();
    }

    User u1 = User.builder().id(1L).fullName("yash").email("yash@gmail.com").phoneNo("+91 2323232323").build();
    User u2 = User.builder().id(2L).fullName("aadi").email("nama@gmail.com").phoneNo("+91 2323232323").build();
    User u3 = User.builder().id(3L).fullName("namna").email("nama@gmail.com").phoneNo("+91 2323223323").build();

    @Test
    public void getAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(u1,u2,u3));
        assertThat(userServiceImp.getAllUsers()).isNotNull();
        assertThat(userServiceImp.getAllUsers().size()).isEqualTo(3);
    }

    @Test
    public void getUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(u1));
        assertThat(userServiceImp.getUserById(anyLong())).isNotNull().isEqualTo(u1);
    }

    @Test
    public void createUser() {
        User record = User.builder().id(4L).fullName("naman").email("naman@gmail.com").phoneNo("+91 223323").build();
        when(userRepository.save(any(User.class))).thenReturn(record);
        assertThat(userServiceImp.createUser(record)).isNotNull();
    }

    @Test
    public void updateUser() {
        User updatedUser = User.builder()
                .fullName("aadi")
                .phoneNo("+91 89898989")
                .email("aadi@gmail.com").build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(u1));
        User _user = userServiceImp.getUserById(anyLong());

        when(userRepository.save(_user)).thenReturn(_user);
        _user.setFullName(updatedUser.getFullName());
        _user.setEmail(updatedUser.getEmail());
        _user.setPhoneNo(updatedUser.getPhoneNo());
        assertThat(userServiceImp.updateUser(anyLong(), updatedUser)).isNotNull();
    }

    @Test
    public void patchUser(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(u1));
        User user = userServiceImp.getUserById(anyLong());
        when(userRepository.save(any(User.class))).thenReturn(user);

        Map<Object, Object> fields = new HashMap<>();
        fields.put("fullName", "ash");

        fields.forEach((k,v) ->{
            Field field = ReflectionUtils.findField(User.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, v);
        });

        assertThat(userServiceImp.patchUser(anyLong(),fields)).isNotNull().isEqualTo(user);
    }

    @Test
    public void deleteUser() {
        userServiceImp.deleteUser(anyLong());
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void deleteAllUsers() {
        userServiceImp.deleteAllUsers();
        verify(userRepository , times(1)).deleteAll();
    }

}