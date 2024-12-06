package com.example.hello.service;

import com.example.hello.dto.LogDto;
import com.example.hello.entity.Log;
import com.example.hello.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MongoService {

    private final LogRepository testRepository;
    @Autowired
    public MongoService(LogRepository logRepository) {
        this.testRepository = logRepository;
    }

    public List<LogDto> getData() {
        return testRepository.findAll().stream().map(log -> new LogDto(log.getAttackName(), log.getMessage(), log.getDate())).collect(Collectors.toList());
    }

    public List<LogDto> findAttackName(String attackName) {
        return testRepository.findByAttackName(attackName).stream().map(log -> new LogDto(log.getAttackName(), log.getMessage(), log.getDate())).collect(Collectors.toList());
    }
}
