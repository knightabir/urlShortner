package com.url.controller.urlController;

import com.url.model.URLModel;
import com.url.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private URLService urlService;


    @PostMapping("/save")
    public ResponseEntity<String> saveUrl(@RequestBody String url) {
        return ResponseEntity.ok(urlService.saveURL(url));
    }

    @GetMapping("/{url}")
    public ResponseEntity<Void> getUrl(@PathVariable String url) {
        // Find the url by the short url and redirect to the actual url
        String actualUrl = urlService.getActualUrl(url);
        if (actualUrl != null) {
            // Redirect to the actual url
            return ResponseEntity.status(302).header("Location", actualUrl).build();
        }
        return ResponseEntity.notFound().build();
    }
}
