package com.url.repository;

import com.url.model.URLModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface URLRepository extends MongoRepository<URLModel, String> {
    public URLModel findByShortUrl(String shortUrl);
    List<URLModel> getAllUrlsByUser(String userId);
}
