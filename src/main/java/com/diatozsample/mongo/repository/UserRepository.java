package com.diatozsample.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.diatozsample.mongo.bean.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {


}