package com.tweetapp.repository;

import com.tweetapp.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {
    @Query("{'email': ?0 , 'password': ?1}")
    Optional<Users> findByIdAndPassword(String email, String password);
}
