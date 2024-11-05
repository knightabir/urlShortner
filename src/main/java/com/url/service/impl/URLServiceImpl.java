package com.url.service.impl;

import com.url.model.URLModel;
import com.url.repository.URLRepository;
import com.url.service.URLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class URLServiceImpl implements URLService {

    @Autowired
    private URLRepository urlRepository;


    @Override
    public String getShortURL(String urlId) {
        URLModel existingUrl = urlRepository.findById(urlId).orElseThrow(() -> new RuntimeException("URL not found"));
        if (existingUrl != null) {
            return existingUrl.getShortUrl();
        }else {
            throw new RuntimeException("URL not found");
        }
    }

    @Override
    public String saveURL(String url) {

        URLModel urlModel = new URLModel();
        urlModel.setUrl(url);

        byte[] array = new byte[5];
        new java.util.Random().nextBytes(array);
        String shortUrl = new String(array, java.nio.charset.StandardCharsets.UTF_8);
        urlModel.setId(shortUrl);
        urlModel.setShortUrl(shortUrl);
        urlRepository.save(urlModel);
        return shortUrl;
    }

    @Override
    public String getLongURL(String urlId) {
        URLModel existingUrl = urlRepository.findById(urlId).orElseThrow(() -> new RuntimeException("URL not found"));
        return existingUrl.getUrl();
    }

    @Override
    public String updateURL(String urlId, String url) {
        URLModel existingUrl = urlRepository.findById(urlId).orElseThrow(() -> new RuntimeException("URL not found"));
        existingUrl.setUrl(url);
        urlRepository.save(existingUrl);
        return existingUrl.getShortUrl();
    }

    @Override
    public void deleteURL(String urlId) {
        URLModel existingUrl = urlRepository.findById(urlId).orElseThrow(() -> new RuntimeException("URL not found"));
        urlRepository.delete(existingUrl);
    }

    @Override
    public String getActualUrl(String shortUrl) {
        URLModel existingUrl = urlRepository.findByShortUrl(shortUrl);
        if (existingUrl != null) {
            return existingUrl.getUrl();
        }else {
            throw new RuntimeException("URL not found");
        }
    }
}
