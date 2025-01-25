package com.url.shortner.service;

public interface UrlService {
    String shortenUrl(String localUrl,String longUrl);
    String getLongUrl(String uniqueId);
}
