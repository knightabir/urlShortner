package com.url.controller.urlController;

import com.url.model.URLModel;
import com.url.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private URLService urlService;


    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveUrl(@RequestBody URLModel urlModel) {
        return ResponseEntity.ok(urlService.saveURL(urlModel.getUrl()));
    }

    @GetMapping("/{url}")
    public ResponseEntity<Void> getUrl(@PathVariable String url) {
        // Find the url by the short url and redirect to the actual url
        URLModel actualUrl = urlService.findById(url);
        if (actualUrl != null) {
            // Redirect to the actual url
            // update the click count
            if (urlService.updateVisitCount(url)) {
                return ResponseEntity.status(302).header("Location", actualUrl.getUrl()).build();
            }
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.notFound().build();
    }
}
