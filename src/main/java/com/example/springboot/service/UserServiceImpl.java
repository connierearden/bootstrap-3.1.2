package com.example.springboot.service;

import com.example.springboot.model.Role;
import com.example.springboot.model.User;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findById(Long id){
        return userRepository.getOne(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User saveUser(User user){
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getOne(1L));
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User updateUser(User user){

        return userRepository.save(user);
    }

    public User findUserByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public void deleteById(Long id) {
        User user = userRepository.getOne(id);
        user.getRoles().clear();
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
            User user = userRepository.findByFirstName(firstName);
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (Role role: user.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), grantedAuthorities);
        }
}
