package com.shamil.dtm.service;

import com.shamil.dtm.dto.LoginRequest;
import com.shamil.dtm.dto.UserRequest;
import com.shamil.dtm.dto.UserResponse;
import com.shamil.dtm.model.User;
import com.shamil.dtm.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> getUsers() {
        List<User> userList = userRepository.findAll();

        return userList.stream()
                       .map(this::mapToUserResponse)
                       .toList();
    }

    public UserResponse getUser(int _id) {
        User user = userRepository.findById(_id)
                                  .orElse(null);
        if (user != null)
            return mapToUserResponse(user);
        return null;
    }

    public UserResponse addUser(UserRequest _userRequest) {
        User user = null;
        try {
            user = userRepository.save(mapToUser(_userRequest));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        if (user != null) {
            return mapToUserResponse(user);
        }
        return null;
    }

    public Map<String, UserResponse> userLogin(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        Map<String, UserResponse> m = new HashMap<>();
        if (user != null) {
            if (Objects.equals(user.getPassword(), loginRequest.getPassword())) {
                m.put("user", mapToUserResponse(user));
                return m;
            }
            m.put("password", null);
            return m;
        }
        m.put("email", null);
        return m;
    }


    UserResponse mapToUserResponse(User u) {
        return UserResponse.builder()
                           .id(u.getId())
                           .name(u.getName())
                           .email(u.getEmail())
                           .build();
    }

    User mapToUser(UserRequest ur) {
        return User.builder()
                   .name(ur.getName())
                   .email(ur.getEmail())
                   .password(ur.getPassword())
                   .build();
    }
}
