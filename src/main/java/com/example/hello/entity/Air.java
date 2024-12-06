package com.example.hello.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Entity
@Getter
@Setter
@Document(collection = "air")
public class Air {
    @Id
    private String id;

    private int year;

    private int month;

    private int day;

    private double so2Value;

    private double coValue;

    private String msrstnName;

    private double pm10Value;

    private double no2Value;

    private double o3Value;

    private double pm25Value;
}
