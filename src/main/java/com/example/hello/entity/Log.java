package com.example.hello.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Slf4j
@Entity
@Getter
@Setter
@Document(collection = "log")
public class Log {
    @Id
    private String id;
    private String attackName;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "KST")
    private Date date;
}
