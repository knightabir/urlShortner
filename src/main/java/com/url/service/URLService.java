package com.url.service;

import com.url.model.URLModel;

import java.util.Map;

public interface URLService {
    public String getShortURL(String urlId);
    public Map<String, String> saveURL(String url);
    public String getLongURL(String urlId);
    public String updateURL(String urlId, String url);
    public void deleteURL(String urlId);
    public String getActualUrl(String shortUrl);
    public URLModel findById(String shortUrl);
    public Boolean updateVisitCount(String shortUrl);
}
