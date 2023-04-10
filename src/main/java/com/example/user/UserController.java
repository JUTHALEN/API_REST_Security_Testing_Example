package com.example.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final userService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user) {
        return ResponseEntity.ok(userService.add(user));
    }

    @GetMapping("/{email}")
    @Transactional
    public User getByEmail(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }

    @DeleteMapping("/{email}")
    @Transactional
    public void delete(@PathVariable("email") String email) {
        userService.deleteByEmail(email);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<User> update(@RequestBody User user) {
        return ResponseEntity.ok(userService.update(user));
    }

}

