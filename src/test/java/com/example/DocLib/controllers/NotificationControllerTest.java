package com.example.DocLib.controllers;

import com.example.DocLib.dto.MessageDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationControllerTest {
    @Test
    void getMessageReturnsResponse() throws Exception {
        NotificationController controller = new NotificationController();

        MessageDto result = controller.getMessage(new MessageDto("hello"));

        assertEquals("Message received: hello", result.getName());
    }
}
