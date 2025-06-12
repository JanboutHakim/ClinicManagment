package com.example.DocLib.controllers;

import com.example.DocLib.dto.MessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NotificationController {


    @MessageMapping("/sendMassage")
    @SendTo("/queue/appointments")
    public MessageDto getMessage(MessageDto message) throws InterruptedException {
        System.out.println("Received: " + message.getName());

        return new MessageDto("Message received: " + message.getName());
    }


}
