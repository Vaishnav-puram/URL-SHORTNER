package com.url.shortner.service;

import java.net.SocketException;

public interface UrlService {
    String shortenUrl(String localUrl,String longUrl) throws SocketException;
    String getLongUrl(String uniqueId);
}
