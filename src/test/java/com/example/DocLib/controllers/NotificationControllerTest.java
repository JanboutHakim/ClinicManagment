package com.example.DocLib.controllers;

import com.example.DocLib.dto.StringDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationControllerTest {
    @Test
    void getMessageReturnsResponse() throws Exception {
        NotificationController controller = new NotificationController();

        StringDto result = controller.getMessage(new StringDto("hello"));

        assertEquals("Message received: hello", result.getName());
    }
}
