package com.example.hello.repository;

import com.example.hello.entity.Air;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirRepository extends MongoRepository<Air,String> {
}
