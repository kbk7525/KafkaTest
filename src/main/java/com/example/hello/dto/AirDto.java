package com.example.hello.dto;

import com.example.hello.entity.Air;
import com.example.hello.service.ApiService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AirDto {
    @JsonProperty("msurDt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String msurDt;

    @JsonProperty("so2Value")
    private double so2Value;

    @JsonProperty("coValue")
    private double coValue;

    @JsonProperty("msrstnName")
    private String msrstnName;

    @JsonProperty("pm10Value")
    private double pm10Value;

    @JsonProperty("no2Value")
    private double no2Value;

    @JsonProperty("o3Value")
    private double o3Value;

    @JsonProperty("pm25Value")
    private double pm25Value;

    public Air convertToEntity(ApiService apiService) {
        Air air = new Air();
        int[] date = apiService.parsingDate(this.msurDt);
        air.setYear(date[0]);
        air.setMonth(date[1]);
        air.setDay(date[2]);
        air.setSo2Value(this.so2Value);
        air.setCoValue(this.coValue);
        air.setMsrstnName(this.msrstnName);
        air.setNo2Value(this.no2Value);
        air.setO3Value(this.o3Value);
        air.setPm10Value(this.pm10Value);
        air.setPm25Value(this.pm25Value);
        return air;
    }
}
