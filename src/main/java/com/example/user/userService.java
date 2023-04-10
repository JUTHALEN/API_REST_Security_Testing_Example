package com.example.user;

import java.util.List;

public interface userService {
    User add(User user);
    List<User> findAll();
    void deleteByEmail(String email);
    User findByEmail(String email);
    User update(User user);    
    
}
