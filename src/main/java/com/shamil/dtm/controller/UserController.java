package com.shamil.dtm.controller;

import com.shamil.dtm.dto.UserRequest;
import com.shamil.dtm.dto.UserResponse;
import com.shamil.dtm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService us;

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(us.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        return new ResponseEntity<>(us.getUser(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addUser(@RequestBody UserRequest _ur) {
        UserResponse ur = us.addUser(_ur);

        if (ur != null)
            return new ResponseEntity<>(ur, HttpStatus.CREATED);

        Map<String, String> data = new HashMap<>();
        data.put("msg",
                "User Already exist with mail id "
                        + _ur.getEmail()
                        + " please login");
        return new ResponseEntity<>(data, HttpStatus.NOT_ACCEPTABLE);
    }

}
