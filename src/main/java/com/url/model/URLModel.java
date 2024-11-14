package com.url.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.util.Date;

@Data
@Document
public class URLModel {
    @Id
    private String id;
    private String url;
    private String shortUrl;

    @DBRef(lazy = true)
    @JsonIgnore
    private User createdBy;
    @DBRef(lazy = true)
    @JsonIgnore
    private User updatedBy;
    private Date createdAt;
    private Date updatedAt;
    private Long visitCount = 0L;
}
