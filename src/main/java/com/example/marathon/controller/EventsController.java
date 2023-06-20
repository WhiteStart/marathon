package com.example.marathon.controller;

import com.example.marathon.dataobject.Events;
import com.example.marathon.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventsController {

    private EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/listEvents")
    public List<Events> listEvents(@RequestParam(defaultValue = "-1") int type,
                                   @RequestParam(defaultValue = "1") int pageNum,
                                   @RequestParam(defaultValue = "5") int pageSize) {
        return eventService.listEvents(type, pageNum, pageSize);
    }
}
