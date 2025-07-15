package com.PrintLab.service.impl;

import com.PrintLab.dto.UserDto;
import com.PrintLab.exception.RecordNotFoundException;
import com.PrintLab.model.Role;
import com.PrintLab.model.User;
import com.PrintLab.repository.RoleRepository;
import com.PrintLab.repository.UserRepository;
import com.PrintLab.service.UserService;
import com.PrintLab.utils.EmailUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final EmailUtils emailUtils;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, EmailUtils emailUtils) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.emailUtils = emailUtils;
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setStatus(true);
        Set<Role> roleList = new HashSet<>();
        for(Role role: user.getRoles()){
            roleRepository.findById(role.getId())
                    .orElseThrow(()-> new RecordNotFoundException("Role not found"));
            roleList.add(role);
        }
        user.setRoles(roleList);
        userRepository.save(user);
        emailUtils.sendRegistrationEmail(user.getEmail(),password);
        return user;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> userList = userRepository.findAllByStatusIsTrue();
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            UserDto userDto = toDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public List<UserDto> getUsersByRole(String role) {
        List<User> userList = userRepository.findByRole(role);
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            UserDto userDto = toDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return toDto(user);
        } else {
            throw new RecordNotFoundException(String.format("User not found for id => %d", id));
        }
    }
    @Transactional
    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = toEntity(userDto);
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(user.getName());
            if(!existingUser.getPassword().equals(user.getPassword())){
                existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            existingUser.setPhone(user.getPhone());
            existingUser.setCnic(user.getCnic());
            existingUser.setEmail(user.getEmail());

            Set<Role> existingRoleValues = existingUser.getRoles();
            Set<Role> newRoleValues = userDto.getRoles();

            // Remove roles that are present in existingRoleValues but not in newRoleValues
            existingRoleValues.removeIf(existingRole -> !newRoleValues.contains(existingRole));

            // Add roles that are present in newRoleValues but not in existingRoleValues
            newRoleValues.removeAll(existingRoleValues);
            existingRoleValues.addAll(newRoleValues);

            User updatedUser = userRepository.save(existingUser);
            return toDto(updatedUser);
        } else {
            throw new RecordNotFoundException(String.format("User not found for id => %d", id));
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.setStatusInactive(id);
        }
        else{
            throw new RecordNotFoundException(String.format("User not found for id => %d", id));
        }
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .createdAt(user.getCreatedAt())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .email(user.getEmail())
                .phone(user.getPhone())
                .cnic(user.getCnic())
                .status(user.getStatus())
                .roles(user.getRoles())
                .build();
    }

    public User toEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .createdAt(userDto.getCreatedAt())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .cnic(userDto.getCnic())
                .status(userDto.getStatus())
                .roles(userDto.getRoles())
                .build();
    }
}
