package org.henry.onlinebankingsystem.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private final MyUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController( MyUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path = {"/register/user"})
    public MyUser createUser(@RequestBody MyUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @GetMapping(path = {"/home"})
    public String home(){
        return "Henry";
    }
}
