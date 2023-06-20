package com.example.marathon.controller;

import com.example.marathon.dataobject.Events;
import com.example.marathon.dataobject.User;
import com.example.marathon.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public User login(@RequestParam String username,
                      @RequestParam String password){
        return userService.login(username, password);
    }

    @GetMapping("/apply")
    public boolean apply(@RequestParam String userId,
                         @RequestParam String eventId){
        return userService.apply(userId, eventId);
    }

    @GetMapping("/searchEvent")
    public Events searchEvent(@RequestParam String name){
        return userService.searchEventLogic(name);
    }
}
