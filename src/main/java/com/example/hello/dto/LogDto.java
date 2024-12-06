package com.example.hello.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import java.util.Date;

@Slf4j
@Getter
@Setter
public class LogDto {
    private String attackName;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "KST")
    private Date date;

    //id 제외한 dto
    public LogDto(String attackName, String message, Date date) {
        this.attackName = attackName;
        this.message = message;
        this.date = date;
    }
}
