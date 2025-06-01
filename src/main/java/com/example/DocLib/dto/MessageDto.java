package com.example.DocLib.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private String name;

    public MessageDto() {}

    public MessageDto(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
