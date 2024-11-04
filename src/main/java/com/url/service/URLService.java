package com.url.service;

import com.url.model.URLModel;

public interface URLService {
    public String getShortURL(String urlId);
    public String saveURL(String url);
    public String getLongURL(String urlId);
    public String updateURL(String urlId, String url);
    public void deleteURL(String urlId);
    public String getActualUrl(String shortUrl);
}
