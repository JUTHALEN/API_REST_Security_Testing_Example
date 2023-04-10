package com.example.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.user.Role;
import com.example.user.User;
import com.example.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User user0;

    @BeforeEach    
    void setUp(){
        user0 = User.builder()
            .firstName("Test User 0")
            .lastName("Prueba")
            .password("123456")
            .email("user0@gmail.com")
            .role(Role.USER)
            .build();
    }

    @Test
    @DisplayName("Test para agregar un user")
    public void testAddUser(){	
        /**
        * Segun el enfoque: Una prueba unitaria se divide en tres partes
        *
        * 1. Arrange: Setting up the data that is required for this test case
        * 2. Act: Calling a method or Unit that is being tested.
        * 3. Assert: Verify that the expected result is right or wrong.
        *
        * Segun el enfoque BDD
        *
        * 1. given
        * 2. when
        * 3. then
        * */    			
        
        // given - dado que:
        User user = User.builder()
            .firstName("Test User 1")
            .lastName("Alende")
            .password("123456")
            .email("test@gmail.com")
            .role(Role.USER)
            .build();
        
        //When
        User userAdded = userRepository.save(user);

        //then
        assertThat(userAdded).isNotNull();
        assertThat(userAdded.getId()).isPositive();
        assertThat(userAdded.getId()).isGreaterThan(0L);
 
    }

    @Test
    @DisplayName("Test para listar usuarios")
    public void testFindAllUsers(){
        // 1. given
        User user1 = User.builder()
            .firstName("Test User 1")
            .lastName("Alende")
            .password("123456")
            .email("test1@gmail.com")
            .role(Role.USER)
            .build();
        
        userRepository.save(user0);
        userRepository.save(user1);

        // 2. when
        List<User> usuarios = userRepository.findAll();

        // 3. then
        assertThat(usuarios).isNotNull();
        assertThat(usuarios.size()).isGreaterThan(0);
        assertThat(usuarios).isNotNull().withFailMessage("La lista de usuarios no debe ser nula");
    }

    @Test
    @DisplayName("Test para recuperar  un user por su ID")
    public void findUserById(){
        // 1. given
        userRepository.save(user0);

        // 2. when
        User user = userRepository.findById(user0.getId()).get();

        // 3. Then
        assertThat(user.getId()).isNotEqualTo(0L);

    }

    @Test
    @DisplayName("Test para actualizar un User")
    public void testUpdateUser(){
        // 1. given
        userRepository.save(user0);

        // 2. when
        User userGuardado = userRepository.findByEmail(user0.getEmail()).get();
        userGuardado.setLastName("PruebaActualizar");
        userGuardado.setFirstName("pruebaActualizar");
        userGuardado.setEmail("actualizar@gmail.com");

        User userUpdated = userRepository.save(userGuardado);

        // 3. Then
        assertThat(userUpdated.getEmail()).isEqualTo("actualizar@gmail.com");
        assertThat(userUpdated.getFirstName()).isEqualTo("pruebaActualizar");
        assertThat(userUpdated.getLastName()).isEqualTo("PruebaActualizar");

    }

    @Test
    @DisplayName("Test para eliminar un usuario")
    public void testDeleteUser(){
        // 1. given
        userRepository.save(user0);

        // 2. when
        userRepository.delete(user0);
        Optional<User> optionalUser = userRepository.findByEmail(user0.getEmail());

        // 3. Then
        assertThat(optionalUser).isEmpty();

    }
    
}
