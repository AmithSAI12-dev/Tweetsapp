package com.tweetapp.repository;

import com.tweetapp.model.Tweets;
import com.tweetapp.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetsRepository extends MongoRepository<Tweets, String> {

    @Query("{email: ?0}")
    List<Tweets> findByEmail(String email);
    @Query(value = "{email: ?0, id: ?1}", delete = true)
    void deleteByIdAndEmail(String email, String id);
}
