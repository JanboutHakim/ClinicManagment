package com.example.DocLib.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class MessageDto {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be at most 255 characters")
    private String name;

    public MessageDto() {}

    public MessageDto(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
