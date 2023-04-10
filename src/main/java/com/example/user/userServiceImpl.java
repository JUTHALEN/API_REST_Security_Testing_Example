package com.example.user;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class userServiceImpl implements userService {
 

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // @Autowired
    // public userServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    //     this.passwordEncoder = passwordEncoder;
    //     this.userRepository = userRepository;
    // } Esta parte me lo ha dado la IA que es necesario para usar el metodo .code

    @Override
    public User add(User user) {
        //No usamos el metodo crud porq queremos cifrar la contraseña, por eso aqui de hace asi
        Optional<User> theUser = userRepository.findByEmail(user.getEmail());

        if(theUser.isPresent()) {
            // Deberiamos devolver una exception personalizada
            //throw new UsernameNotFoundException(); Pero eso no sirve, el profe no sabe como
            return null;
        }

        // Encriptamos la password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    @Override
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository
            .findByEmail(email)
            .orElseThrow(() -> 
               new UsernameNotFoundException("No existe el usuario con el email: " + email));
    }

    @Override
    public User update(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail())
            .orElseThrow(
                () -> new UsernameNotFoundException("No existe el usuario con el email: " + user.getEmail()));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }    
    
}