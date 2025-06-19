package com.example.DocLib.controllers;

import com.example.DocLib.dto.StringDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {


    @MessageMapping("/send-message")
    @SendTo("/queue/appointments")
    public StringDto getMessage(StringDto message) throws InterruptedException {
        System.out.println("Received: " + message.getName());

        return new StringDto("Message received: " + message.getName());
    }


}
