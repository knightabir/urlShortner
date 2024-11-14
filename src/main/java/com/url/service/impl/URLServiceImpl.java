package com.url.service.impl;

import com.url.model.URLModel;
import com.url.model.User;
import com.url.repository.URLRepository;
import com.url.service.URLService;
import com.url.service.UserService;
import com.url.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class URLServiceImpl implements URLService {

    @Autowired
    private URLRepository urlRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        log.debug("Fetching current user from security context");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return userService.getUserByEmail(email);
    }

    @Override
    public String getShortURL(String urlId) {
        log.info("Fetching short URL for URL ID: {}", urlId);
        URLModel existingUrl = urlRepository.findById(urlId).orElseThrow(() -> new RuntimeException("URL not found"));
        if (existingUrl != null) {
            log.debug("Short URL found: {}", existingUrl.getShortUrl());
            return existingUrl.getShortUrl();
        } else {
            log.error("URL not found for ID: {}", urlId);
            throw new RuntimeException("URL not found");
        }
    }

    @Override
    public Map<String, String> saveURL(String url) {
        log.info("Saving new URL: {}", url);
        URLModel urlModel = new URLModel();
        urlModel.setUrl(url);
        urlModel.setCreatedBy(getCurrentUser());
        urlModel.setCreatedAt(new java.util.Date());

        String shortUrl = getRandomString(12);
        String serverUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        urlModel.setId(shortUrl);
        urlModel.setShortUrl(serverUrl + "/u/" + shortUrl);
        urlRepository.save(urlModel);

        Map<String, String> response = new HashMap<>();
        response.put("shortUrl", serverUrl + "/u/" + shortUrl);
        log.debug("Short URL created: {}", response.get("shortUrl"));
        return response;
    }

    private String getRandomString(int length) {
        log.debug("Generating random string of length: {}", length);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * 62);
            char c;
            if (randomIndex < 26) {
                c = (char) ('A' + randomIndex);
            } else if (randomIndex < 52) {
                c = (char) ('a' + (randomIndex - 26));
            } else {
                c = (char) ('0' + (randomIndex - 52));
            }
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public String getLongURL(String urlId) {
        log.info("Fetching long URL for URL ID: {}", urlId);
        URLModel existingUrl = urlRepository.findById(urlId).orElseThrow(() -> new RuntimeException("URL not found"));
        log.debug("Long URL found: {}", existingUrl.getUrl());
        return existingUrl.getUrl();
    }

    @Override
    public String updateURL(String urlId, String url) {
        log.info("Updating URL for URL ID: {}", urlId);
        URLModel existingUrl = urlRepository.findById(urlId).orElseThrow(() -> new RuntimeException("URL not found"));
        existingUrl.setUrl(url);
        urlRepository.save(existingUrl);
        log.debug("URL updated to: {}", existingUrl.getUrl());
        return existingUrl.getShortUrl();
    }

    @Override
    public void deleteURL(String urlId) {
        log.info("Deleting URL for URL ID: {}", urlId);
        URLModel existingUrl = urlRepository.findById(urlId).orElseThrow(() -> new RuntimeException("URL not found"));
        urlRepository.delete(existingUrl);
        log.debug("URL deleted for ID: {}", urlId);
    }

    @Override
    public String getActualUrl(String shortUrl) {
        log.info("Fetching actual URL for short URL: {}", shortUrl);
        URLModel existingUrl = urlRepository.findByShortUrl(shortUrl);
        if (existingUrl != null) {
            log.debug("Actual URL found: {}", existingUrl.getUrl());
            return existingUrl.getUrl();
        } else {
            log.error("URL not found for short URL: {}", shortUrl);
            throw new RuntimeException("URL not found");
        }
    }

    @Override
    public URLModel findById(String shortUrl) {
        log.info("Finding URL by short URL: {}", shortUrl);
        return urlRepository.findById(shortUrl).orElseThrow(() -> new RuntimeException("URL not found"));
    }

    @Override
    public Boolean updateVisitCount(String shortUrl) {
        log.info("Updating visit count for short URL: {}", shortUrl);
        URLModel existingUrl = urlRepository.findById(shortUrl).orElseThrow(() -> new RuntimeException("URL not found"));
        if (existingUrl != null) {
            existingUrl.setVisitCount(existingUrl.getVisitCount() + 1);
            urlRepository.save(existingUrl);
            log.debug("Visit count updated to: {}", existingUrl.getVisitCount());
            return true;
        }
        log.warn("Failed to update visit count for short URL: {}", shortUrl);
        return false;
    }
}