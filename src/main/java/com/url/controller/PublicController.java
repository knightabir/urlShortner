package com.url.controller;

import com.url.model.URLModel;
import com.url.service.URLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/u")
public class PublicController {

    @Autowired
    private URLService urlService;

    @GetMapping("/{url}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String url) {

        URLModel existingUrl = urlService.findById(url);

        if (existingUrl == null) {
            return ResponseEntity.notFound().build();
        }
        String actualUrl = existingUrl.getUrl();

        // update the click count
        if (urlService.updateVisitCount(url)) {
            log.info("Updated visit count for url: {}", url);
            log.info("Redirecting to: {}", actualUrl);
        // Redirect to the actual url
            return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "http://"+actualUrl)
                .build();
        }
        return ResponseEntity.notFound().build();

    }
}