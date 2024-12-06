package com.example.hello.repository;

import com.example.hello.dto.LogDto;
import com.example.hello.entity.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {
    List<LogDto> findByAttackName(String attackName);
}
