package com.example.hello.service;

import com.example.hello.dto.AirDto;
import com.example.hello.entity.Air;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class ApiService {
    private final ObjectMapper objectMapper;
    private final Producer producer;

    public ApiService(ObjectMapper objectMapper, Producer producer) {
        this.objectMapper = objectMapper;
        this.producer = producer;
    }

    public void processApiData() throws Exception {
        String response = ApiData();
        JsonNode items = parseJson(response).path("response").path("body").path("items");
        for (JsonNode item : items) {
            AirDto airDto = objectMapper.treeToValue(item, AirDto.class);
            Air air = airDto.convertToEntity(this);
            producer.send(air); // Kafka로 전송
        }
    }

    public int[] parsingDate(String date) {
        String[] parsing = date.split("-");
        int year = Integer.parseInt(parsing[0]);
        int month = Integer.parseInt(parsing[1]);
        int day = Integer.parseInt(parsing[2]);

        return new int[]{year, month, day};
    }

    public String ApiData() throws IOException {
        HttpURLConnection urlConnection = null;
        try {
            StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B552584/ArpltnStatsSvc/getMsrstnAcctoRDyrg");
            urlBuilder.append("?").append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)).append("=").append("mRHO2ila5LAfNdF2kIcOxF01o5L5rwt1LssSl2oUH%2FRXh05VlsqBxIcjG7amEnENQOCZjNXm7N9PF4rW3G79Cg%3D%3D");
            urlBuilder.append("&").append(URLEncoder.encode("returnType", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("json", StandardCharsets.UTF_8));
            urlBuilder.append("&").append(URLEncoder.encode("numOfRows", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("150", StandardCharsets.UTF_8));
            urlBuilder.append("&").append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("1", StandardCharsets.UTF_8));
            urlBuilder.append("&").append(URLEncoder.encode("inqBginDt", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("20240701", StandardCharsets.UTF_8));
            urlBuilder.append("&").append(URLEncoder.encode("inqEndDt", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("20241101", StandardCharsets.UTF_8));
            URL url = new URL(urlBuilder.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            try (InputStream stream = getNetworkConnection(urlConnection)) {
                return readStreamToString(stream);
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private InputStream getNetworkConnection(HttpURLConnection urlConnection) throws IOException {
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);
        if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
        }
        return urlConnection.getInputStream();
    }

    private String readStreamToString(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        String readLine;
        while ((readLine = br.readLine()) != null) {
            result.append(readLine).append("\n\r");
        }
        br.close();
        return result.toString();
    }

    public JsonNode parseJson(String response) throws IOException {
        return objectMapper.readTree(response);
    }
}
