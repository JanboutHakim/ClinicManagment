package com.example.DocLib.controllers;

import com.example.DocLib.dto.MessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @MessageMapping("/sendMassage")
    @SendTo("/patient/notification")
    public MessageDto getMessage(MessageDto message) throws InterruptedException {
        System.out.println("Received: " + message.getName());
        // ⏳ Simulate a 10-second delay
        Thread.sleep(10000);
        return new MessageDto("Message received: " + message.getName());
    }
}
