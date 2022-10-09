package com.shamil.dtm.service;

import com.shamil.dtm.dto.UserRequest;
import com.shamil.dtm.dto.UserResponse;
import com.shamil.dtm.model.User;
import com.shamil.dtm.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
