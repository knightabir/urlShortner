package com.url.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class URLModel {
    @Id
    private String id;
    private String url;
    private String shortUrl;
}
