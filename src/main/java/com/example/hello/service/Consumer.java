package com.example.hello.service;

import com.example.hello.entity.Air;
import com.example.hello.entity.Log;
import com.example.hello.repository.AirRepository;
import com.example.hello.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Consumer {
    public AirRepository airRepository;

    public Consumer(AirRepository airRepository) {
        this.airRepository = airRepository;
    }

    @KafkaListener(topics = "test", groupId = "test")
    public void listen(String str) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try{
            Air data = mapper.readValue(str, Air.class);
            log.info("rcv msg : {}", data);
            airRepository.save(data);
        }catch (Exception ex) {
            throw new Exception("error", ex);
        }
    }
}
