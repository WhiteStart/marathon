package com.example.marathon.controller;

import com.example.marathon.dataobject.Events;
import com.example.marathon.service.ManagerService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/draw")
    public void draw(@RequestParam String eventId){
        managerService.draw(eventId);
    }

    @PostMapping("/createEvent")
    public void createEvent(@RequestBody Events event){
        managerService.createEvents(event);
    }
}
